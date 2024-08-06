package com.plugin.board

import android.app.Activity
import android.content.Intent
import android.webkit.WebView
import app.tauri.annotation.Command
import app.tauri.annotation.InvokeArg
import app.tauri.annotation.TauriPlugin
import app.tauri.plugin.Invoke
import app.tauri.plugin.JSObject
import app.tauri.plugin.Plugin
import com.zcapi
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
     var enable: Boolean? = null
     var onTime: Long? = null // 以毫秒为单位
     var offTime: Long? = null // 以毫秒为单位
}

@TauriPlugin
class BoardPlugin(private val activity: Activity): Plugin(activity) {
    private val zc = zcapi()

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
        val argv = invoke.parseArgs(PowerOnOffTime::class.java)
        val enable = argv.enable ?: false
        val onTime = parseTimestamp(argv.onTime)
        val offTime = parseTimestamp(argv.offTime)

        this.zc.setPowetOnOffTime(enable, onTime, offTime)

        val ret = JSObject()
        ret.put("value", println("enable: ${argv.enable}, onTime: ${argv.onTime}, offTime: ${argv.offTime}"))
        invoke.resolve()
    }

    /**
     * open setting config
     */
    @Command
    fun openSettingConfig(invoke: Invoke) {
        val intent = Intent(android.provider.Settings.ACTION_SETTINGS)
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