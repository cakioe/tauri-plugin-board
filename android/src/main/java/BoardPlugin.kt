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

@InvokeArg
class SettingConfig {
    var enable: Boolean = true
}

@InvokeArg
class FileManager {
    var enable: Boolean = true
}

/**
 * vending board plugin of tauri for android use kotlin
 * @author: <cleveng@gmail.com>
 *
 * @constructor creates a plugin for tauri
 * @property activity of main activity
 */
@TauriPlugin
class BoardPlugin(private val activity: Activity): Plugin(activity) {
    /**
     * ZC: the control board of screen, from`卓策`
     */
    private val zc = zcapi()

    // wenzoom开发板 | 鼎商开发板
    private val finder = SerialPortFinder()
    private var serialsPathIndex = 0

    /**
     * the init method of the plugin
     *
     * @document https://v2.tauri.app/zh-cn/develop/plugins/develop-mobile/#%E6%8F%92%E4%BB%B6%E9%85%8D%E7%BD%AE
     * @param webView the view of main activity
     * @return void
     */
    override fun load(webView: WebView) {
        this.zc.getContext(webView.context)
        this.initTouchScreen()
    }

    /**
     * event of the app relaunch
     *
     * @param intent the intent
     * @return void
     */
    override fun onNewIntent(intent: Intent) {
        this.initTouchScreen()
    }

    /**
     * initialization of touch screen
     *
     * @param
     * @return void
     */
    private fun initTouchScreen() {
        this.zc.buildModel.let { value ->
            if (value.startsWith("zc") || value.startsWith("ZC")) {
                this.zc.setStatusBar(false)
                this.zc.setGestureStatusBar(false)
            }
        }
    }

    /**
     * command of `ping`
     *
     * @param invoke to invoke [PingArgs] { enable: "" }
     * @return json
     */
    @Command
    fun ping(invoke: Invoke) {
        val args = invoke.parseArgs(PingArgs::class.java)
        val ret = JSObject()
        ret.put("value", args.enable ?: "default value :(")
        invoke.resolve(ret)
    }

    /**
     * command of `reboot`
     *
     * @param invoke to invoke [none] { }
     * @return void
     */
    @Command
    fun reboot(invoke: Invoke) {
        this.zc.reboot()
        invoke.resolve()
    }

    /**
     * command of `shutdown`
     *
     * @param invoke to invoke [none] { }
     * @return void
     */
    @Command
    fun shutdown(invoke: Invoke) {
        this.zc.shutDown()
        invoke.resolve()
    }

    /**
     * command of `setStatusBar`
     *
     * @param invoke to invoke [StatusBar] { enable: true }
     * @return void
     */
    @Command
    fun setStatusBar(invoke: Invoke) {
        val argv = invoke.parseArgs(StatusBar::class.java)
        this.zc.setStatusBar(argv.enable ?: false)
        invoke.resolve()
    }

    /**
     * command of `setGestureStatusBar`
     *
     * @param invoke to invoke [GestureStatusBar] { enable: true }
     * @return void
     */
    @Command
    fun setGestureStatusBar(invoke: Invoke) {
        val argv = invoke.parseArgs(GestureStatusBar::class.java)
        this.zc.setGestureStatusBar(argv.enable ?: false)
        invoke.resolve()
    }

    /**
     * command of `getBuildModel`
     *
     * @param invoke to invoke [none] { }
     * @return json { value: "" }
     * @deprecated 1.5.0, use `getBuildEnv` instead
     */
    @Command
    fun getBuildModel(invoke: Invoke) {
        val argv = this.zc.buildModel

        val ret = JSObject()
        ret.put("value", argv)
        invoke.resolve(ret)
    }

    /**
     * command of `getBuildSerial`
     *
     * @param invoke to invoke [none] { }
     * @return json { value: "" }
     * @deprecated 1.5.0, use `getBuildEnv` instead
     */
    @Command
    fun getBuildSerial(invoke: Invoke) {
        val argv = this.zc.buildSerial

        val ret = JSObject()
        ret.put("value", argv)
        invoke.resolve(ret)
    }

    /**
     * command of `setLcdOnOff`
     *
     * @param invoke to invoke [LcdOnOff] { enable: true }
     * @return void
     */
    @Command
    fun setLcdOnOff(invoke: Invoke) {
        val argv = invoke.parseArgs(LcdOnOff::class.java)
        this.zc.setLcdOnOff(argv.enable ?: true)
        invoke.resolve()
    }

    /**
     * command of `setPowerOnOffTime`
     *
     * @param invoke to invoke [PowerOnOffTime] { enable: true, onTime: 123, offTime: 123 }
     * @return json { value: "success" }
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
     * command of `openSettingConfig`
     *
     * @param invoke to invoke [SettingConfig] { enable: true }
     * @return void
     */
    @Command
    fun openSettingConfig(invoke: Invoke) {
        val argv = invoke.parseArgs(SettingConfig::class.java)
        this.zc.setStatusBar(argv.enable)
        this.zc.setGestureStatusBar(argv.enable)

        val intent = Intent(Settings.ACTION_SETTINGS)
        startActivityForResult(invoke, intent, "")
        invoke.resolve()
    }

    /**
     * command of `openFileManager`
     *
     * @param invoke to invoke [FileManager] { enable: true }
     * @return void
     */
    @Command
    fun openFileManager(invoke: Invoke) {
        val argv = invoke.parseArgs(FileManager::class.java)
        this.zc.setStatusBar(argv.enable)
        this.zc.setGestureStatusBar(argv.enable)

        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        startActivityForResult(invoke, intent, "")
        invoke.resolve()
    }

    /**
     * command of `setAppBrightness`
     *
     * @param invoke to invoke [AppBrightness] { value: 1, isScreen: true }
     * @return void
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
     * command of `getSerialPaths`
     *
     * @param invoke to invoke [none] { }
     * @return json { value: string[] }
     * @deprecated 1.5.0, use `getSerialDevicesPath` instead
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
     * command of `getSerialDevicesPath`
     *
     * @param invoke to invoke [none] { }
     * @return json { value: SerialDevice[] }
     */
    @Command
    fun getSerialDevicesPath(invoke: Invoke) {
        val gson = Gson()
        val ret = JSObject()

        val devices = this.finder.getAvailableSerialDevices()
        if (this.serialsPathIndex !in devices.indices) {
            this.serialsPathIndex = 0
        }

        val result = mutableListOf<SerialDevice>()
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
     * command of `getAllDevicesPath`
     *
     * @param invoke to invoke [none] { }
     * @return json { value: string[] }
     */
    @Command
    fun getAllDevicesPath(invoke: Invoke) {
        val gson = Gson()
        val ret = JSObject()
        ret.put("value", gson.toJson(this.finder.allDevicesPath))
        invoke.resolve(ret)
    }

    /**
     * command of `setSerialsPathIndex`
     *
     * @param invoke to invoke [SerialsPathIndex] { index: 1 }
     * @return void
     * @since 1.4.0-beta.11
     */
    @Command
    fun setSerialsPathIndex(invoke: Invoke) {
        val args = invoke.parseArgs(SerialsPathIndex::class.java)
        this.serialsPathIndex = args.index
        invoke.resolve()
    }

    /**
     * command of `getBuildEnv`
     *
     * @param invoke to invoke [none] { }
     * @return void
     * @since 1.4.0-beta.12
     */
    @Command
    fun getBuildEnv(invoke: Invoke) {
        val gson = Gson()
        val ret = JSObject()

        val result: Env = Env(
            sdkVersion = Build.VERSION.SDK_INT,
            androidVersion = Build.VERSION.RELEASE,
            serialSn = this.zc.buildSerial,
            modelNo = this.zc.buildModel,
        )

        ret.put("value", gson.toJson(result))
        invoke.resolve(ret)
    }

    /**
     * command of `openMainActivity`
     *
     * @param invoke to invoke [none] { }
     * @return void
     * @since 1.4.0-beta.14
     */
    @Command
    fun openMainActivity(invoke: Invoke) {
        this.zc.setStatusBar(true)
        this.zc.setGestureStatusBar(true)

        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivityForResult(invoke, intent, "")

        invoke.resolve()
    }
}
