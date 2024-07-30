package com.plugin.board

import android.app.Activity
import android.webkit.WebView
import app.tauri.annotation.Command
import app.tauri.annotation.InvokeArg
import app.tauri.annotation.TauriPlugin
import app.tauri.plugin.Invoke
import app.tauri.plugin.JSObject
import app.tauri.plugin.Plugin
import com.zcapi

@InvokeArg
class StatusBar {
    var value: Boolean? = true
}

@InvokeArg
class GestureStatusBar {
    var value: Boolean? = true
}

@InvokeArg
class LcdOnOff {
    var value: Boolean? = true
}

@TauriPlugin
class BoardPlugin(private val activity: Activity): Plugin(activity) {
    private val zc = zcapi()

    // https://v2.tauri.app/zh-cn/develop/plugins/develop-mobile/#%E6%8F%92%E4%BB%B6%E9%85%8D%E7%BD%AE
    override fun load(webView: WebView) {
        this.zc.getContext(webView.context)
    }

    /**
     * reboot the machine by board
     */
    @Command
    fun reboot() {
        this.zc.reboot()
    }

    /**
     * shutdown the machine by board
     */
    @Command
    fun shutdown() {
        this.zc.shutDown()
    }

    /**
     * set status bar on or off by board
     */
    @Command
    fun set_status_bar(invoke: Invoke) {
        val argv = invoke.parseArgs(StatusBar::class.java)
        this.zc.setStatusBar(argv.value ?: true)

        val ret = JSObject()
        ret.put("value", true)
        invoke.resolve(ret)
    }

    /**
     * set gesture status bar on or off by board
     */
    @Command
    fun set_gesture_status_bar(invoke: Invoke) {
        val argv = invoke.parseArgs(GestureStatusBar::class.java)
        this.zc.setGestureStatusBar(argv.value ?: true)

        val ret = JSObject()
        ret.put("value", true)
        invoke.resolve(ret)
    }

    /**
     * get machine build model by board
     */
    @Command
    fun get_build_model(invoke: Invoke) {
        val argv = this.zc.buildModel

        val ret = JSObject()
        ret.put("value", argv)
        invoke.resolve(ret)
    }

    /**
     * get machine build serial by board
     */
    @Command
    fun get_build_serial(invoke: Invoke) {
        val argv = this.zc.buildSerial

        val ret = JSObject()
        ret.put("value", argv)
        invoke.resolve(ret)
    }

    /**
     * set lcd on or off by board
     */
    @Command
    fun set_lcd_on_off(invoke: Invoke) {
        val argv = invoke.parseArgs(LcdOnOff::class.java)
        this.zc.setLcdOnOff(argv.value ?: true)

        val ret = JSObject()
        ret.put("value", true)
        invoke.resolve(ret)
    }
}
