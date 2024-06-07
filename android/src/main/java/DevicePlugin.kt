package com.plugin.board

import android.app.Activity
import app.tauri.annotation.Command
import app.tauri.annotation.InvokeArg
import app.tauri.annotation.TauriPlugin
import app.tauri.plugin.JSObject
import app.tauri.plugin.Plugin
import app.tauri.plugin.Invoke
import app.tauri.plugin.WebView

import com.zcapi

@InvokeArg
class PingArgs {
  var value: String? = null
}

@InvokeArg
class StatusBar {
  var value: Boolean? = true
}

@InvokeArg
class GestureStatusBar {
  var value: Boolean? = true
}

@TauriPlugin
class DevicePlugin(private val activity: Activity): Plugin(activity) {
    private val implementation = Device()

    // private var zcapi: zcapi
    private var zcapi zcApi

    @Command
    fun ping(invoke: Invoke) {
        val args = invoke.parseArgs(PingArgs::class.java)

        val ret = JSObject()
        ret.put("value", implementation.pong(args.value ?: "default value :("))
        invoke.resolve(ret)
    }

    override fun load(webView: WebView) {
        this.zcapi = new zcapi()
        this.zcapi.getContext(webView.getContext())
        // zcApi.getContext(getApplicationContext())
    }

    /**
     * @method shutdown
     * @param invoke
     */
    @Command
    fun shutdown(invoke: Invoke) {
        this.zcapi.shutdown()

        val ret = JSObject()
        invoke.resolve(ret)
    }

    /**
     * @method reboot
     * @param invoke
     */
    @Command
    fun reboot(invoke: Invoke) {
        this.zcapi.reboot()

        val ret = JSObject()
        invoke.resolve(ret)
    }

    /**
     * @method setStatusBar
     * @param invoke
     */
    @Command
    fun setStatusBar(invoke: Invoke) {
        val args = invoke.parseArgs(StatusBar::class.java)
        this.zcapi.setStatusBar(args.value ?: true)

        val ret = JSObject()
        invoke.resolve(ret)
    }

    /**
     * @method setGestureStatusBar
     * @param invoke
     */
    @Command
    fun setGestureStatusBar(invoke: Invoke) {
        val args = invoke.parseArgs(GestureStatusBar::class.java)
        this.zcapi.setGestureStatusBar(args.value ?: true)

        val ret = JSObject()
        invoke.resolve(ret)
    }

    /**
     * @method getBuildModel
     * @param invoke
     */
    @Command
    fun getBuildModel(invoke: Invoke) {
        val ret = JSObject()
        invoke.resolve(ret)
    }

    /**
     * @method getBuildSerial
     * @param invoke
     */
    @Command
    fun getBuildSerial(invoke: Invoke) {
        val ret = JSObject()
        invoke.resolve(ret)
    }

    /**
     * @method setLcdOnOff
     * @param invoke
     */
    @Command
    fun setLcdOnOff(invoke: Invoke) {
        val ret = JSObject()
        invoke.resolve(ret)
    }
}
