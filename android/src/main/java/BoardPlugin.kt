package com.plugin.board

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.webkit.WebView
import app.tauri.annotation.Command
import app.tauri.annotation.InvokeArg
import app.tauri.annotation.TauriPlugin
import app.tauri.plugin.Invoke
import app.tauri.plugin.JSObject
import app.tauri.plugin.Plugin
import com.google.gson.Gson
import com.zcapi
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

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

@TauriPlugin
class BoardPlugin(private val activity: Activity): Plugin(activity) {
    private val zc = zcapi()
    private val serialPaths = getAllSerialPaths()

    // https://v2.tauri.app/zh-cn/develop/plugins/develop-mobile/#%E6%8F%92%E4%BB%B6%E9%85%8D%E7%BD%AE
    override fun load(webView: WebView) {
        this.zc.getContext(webView.context)
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

        val enable = args.enable
        val onTime = parseTimestamp(args.onTime)
        val offTime = parseTimestamp(args.offTime)

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
        this.serialPaths.let {
            val ret = JSObject()
            ret.put("value", gson.toJson(it))
            invoke.resolve(ret)
            return
        }
    }
}

fun parseTimestamp(timestamp: Long?): IntArray? {
    return timestamp?.let {
        val date = Date(it)
        val calendar = Calendar.getInstance()
        calendar.time = date

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1 // 月份从0开始，所以需要加1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY) // 24小时制
        val minute = calendar.get(Calendar.MINUTE)

        intArrayOf(year, month, day, hour, minute)
    }
}

fun getAllSerialPaths(): MutableList<String> {
    val result = mutableListOf<String>()
    try {
        val command = arrayOf("sh", "-c", "ls -l /dev/tty*")
        val process = Runtime.getRuntime().exec(command)
        val lines = BufferedReader(InputStreamReader(process.inputStream)).readLines()

        for (line in lines) {
            val parts = line.split(Regex("\\s+"))
            if (parts.size > 2) {
                val filename = parts.last() // 文件名
                val owner = parts[2] // 文件的所有者
                if (owner == "system") {
                    result.add(filename) // 添加符合条件的文件
                }
            }
        }
        process.waitFor()

        return result
    } catch (e: IOException) {
        throw e
    }
}