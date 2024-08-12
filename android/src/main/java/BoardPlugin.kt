package com.plugin.board

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.serialport.SerialPortFinder
import android.webkit.WebView
import app.tauri.annotation.Command
import app.tauri.annotation.InvokeArg
import app.tauri.annotation.TauriPlugin
import app.tauri.plugin.Invoke
import app.tauri.plugin.JSObject
import app.tauri.plugin.Plugin
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.zcapi

@InvokeArg
class StatusBar {
    var enable: Boolean? = true
}

@InvokeArg
class GestureStatusBar {
    var enable: Boolean? = true
}

@InvokeArg
class LcdOnOff {
    var enable: Boolean? = true
}

@InvokeArg
class PingArgs {
    var enable: String? = null
}

@InvokeArg
class PowerOnOffTime {
     var enable: Boolean = false
     var onTime: Long? = null // 以毫秒为单位
     var offTime: Long? = null // 以毫秒为单位
}

@InvokeArg
class AppBrightness {
    var value: Int = 255 // [5, 255]
    var isScreen: Boolean = true
}

@InvokeArg
class SerialsPathIndex{
    var index: Int = 0
}

@TauriPlugin
class BoardPlugin(private val activity: Activity): Plugin(activity) {
    // 卓策安卓开发板
    private val zc = zcapi()

    // wenzoom开发板 | 鼎商开发板
    private val finder = SerialPortFinder()
    private var serialsPathIndex = 0

    // https://v2.tauri.app/zh-cn/develop/plugins/develop-mobile/#%E6%8F%92%E4%BB%B6%E9%85%8D%E7%BD%AE
    override fun load(webView: WebView) {
        this.zc.getContext(webView.context)

        /**
         * @description 设置状态栏 | 设置手势状态栏
         */
        this.zc.buildModel.let { value ->
            if (value.startsWith("zc") || value.startsWith("ZC")) {
                this.zc.setStatusBar(false)
                this.zc.setGestureStatusBar(false)
            }
        }
    }

    /**
     * 应用程序重新启动
     */
    override fun onNewIntent(intent: Intent) {
        // handle new intent event
    }

    /**
     * ping
     */
    @Command
    fun ping(invoke: Invoke) {
        val args = invoke.parseArgs(PingArgs::class.java)
        val ret = JSObject()
        ret.put("value", args.enable ?: "default value :(")
        invoke.resolve(ret)
    }

    /**
     * reboot the machine by board
     */
    @Command
    fun reboot(invoke: Invoke) {
        this.zc.reboot()
        invoke.resolve()
    }

    /**
     * shutdown the machine by board
     */
    @Command
    fun shutdown(invoke: Invoke) {
        this.zc.shutDown()
        invoke.resolve()
    }

    /**
     * set status bar on or off by board
     */
    @Command
    fun setStatusBar(invoke: Invoke) {
        val argv = invoke.parseArgs(StatusBar::class.java)
        this.zc.setStatusBar(argv.enable ?: false)
        invoke.resolve()
    }

    /**
     * set gesture status bar on or off by board
     */
    @Command
    fun setGestureStatusBar(invoke: Invoke) {
        val argv = invoke.parseArgs(GestureStatusBar::class.java)
        this.zc.setGestureStatusBar(argv.enable ?: false)
        invoke.resolve()
    }

    /**
     * get machine build model by board
     */
    @Command
    fun getBuildModel(invoke: Invoke) {
        val argv = this.zc.buildModel

        val ret = JSObject()
        ret.put("value", argv)
        invoke.resolve(ret)
    }

    /**
     * get machine build serial by board
     */
    @Command
    fun getBuildSerial(invoke: Invoke) {
        val argv = this.zc.buildSerial

        val ret = JSObject()
        ret.put("value", argv)
        invoke.resolve(ret)
    }

    /**
     * set lcd on or off by board
     */
    @Command
    fun setLcdOnOff(invoke: Invoke) {
        val argv = invoke.parseArgs(LcdOnOff::class.java)
        this.zc.setLcdOnOff(argv.enable ?: true)
        invoke.resolve()
    }

    /**
     * set power on or off time
     * 设定的开机与关机时间必须超过当前时间
     */
    @Command
    fun setPowerOnOffTime(invoke: Invoke) {
        val args = invoke.parseArgs(PowerOnOffTime::class.java)
        
        val carbon = Carbon()
        val enable = args.enable
        val onTime = carbon.parseTimestamp(args.onTime)
        val offTime = carbon.parseTimestamp(args.offTime)

        this.zc.setPowetOnOffTime(enable, onTime, offTime)

        val ret = JSObject()
        ret.put("value", "success")
        invoke.resolve(ret)
    }

    /**
     * open setting config
     */
    @Command
    fun openSettingConfig(invoke: Invoke) {
        val intent = Intent(Settings.ACTION_SETTINGS)
        startActivityForResult(invoke, intent, "")
        invoke.resolve()
    }

    /**
     * open file manager
     */
    @Command
    fun openFileManager(invoke: Invoke) {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        startActivityForResult(invoke, intent, "")
        invoke.resolve()
    }

    /**
     * set app brightness
     */
    @Command
    fun setAppBrightness(invoke: Invoke) {
        val args = invoke.parseArgs(AppBrightness::class.java)
        val value: Int = args.value.let {
            if (it < 5) {
                5
            } else if (it > 255) {
                255
            } else {
                it
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(activity)) {
                val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                startActivityForResult(invoke, intent, "")

                throw Exception("waiting for apply permission to change brightness")
            }
        }

        if (!args.isScreen) {
            try {
                Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS).let {
                    Settings.System.putInt(activity.contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
                    Settings.System.putInt(activity.contentResolver, Settings.System.SCREEN_BRIGHTNESS, value)
                }
                activity.window.attributes.let {
                    it.screenBrightness = value / 255f
                    activity.window.attributes = it
                }
            } catch (e: Exception) {
                throw e
            }
        }

        throw Exception("adjust brightness failed because not support on Android 7.0 and above")
    }

    /**
     * get serial paths
     */
    @Command
    fun getSerialPaths(invoke: Invoke) {
        val gson = Gson()
        this.finder.getAvailableSerialDevices().let {
            val ret = JSObject()
            ret.put("value", gson.toJson(it))
            invoke.resolve(ret)
            return
        }
    }

    /**
     * get serial devices path
     */
    @Command
    fun getSerialDevicesPath(invoke: Invoke) {
        val gson = Gson()
        val ret = JSObject()

        val result = mutableListOf<SerialDevice>()
        val devices = this.finder.getAvailableSerialDevices()
        if (this.serialsPathIndex !in devices.indices) {
            this.serialsPathIndex = 0
        }

        devices.forEachIndexed { index, path ->
            val item = SerialDevice(
                path = path,
                active = this.serialsPathIndex == index,
                index = index
            )
            result.add(item)
        }

        ret.put("value", gson.toJson(result))
        invoke.resolve(ret)
    }

    /**
     * get all devices path
     */
    @Command
    fun getAllDevicesPath(invoke: Invoke) {
        val gson = Gson()
        val ret = JSObject()
        ret.put("value", gson.toJson(this.finder.allDevicesPath))
        invoke.resolve(ret)
    }

    /**
     * set serials path index
     */
    @Command
    fun setSerialsPathIndex(invoke: Invoke) {
        val args = invoke.parseArgs(SerialsPathIndex::class.java)
        this.serialsPathIndex = args.index
        invoke.resolve()
    }
}
