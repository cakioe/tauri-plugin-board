package com.plugin.board

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.serialport.SerialPortFinder
import android.util.DisplayMetrics
import android.webkit.WebView
import android.widget.Toast
import app.tauri.annotation.Command
import app.tauri.annotation.InvokeArg
import app.tauri.annotation.TauriPlugin
import app.tauri.plugin.Invoke
import app.tauri.plugin.JSObject
import app.tauri.plugin.Plugin
import cc.uling.usdk.USDK
import cc.uling.usdk.board.UBoard
import cc.uling.usdk.board.wz.para.BSReplyPara
import cc.uling.usdk.board.wz.para.HCReplyPara
import cc.uling.usdk.board.wz.para.SReplyPara
import cc.uling.usdk.board.wz.para.SVReplyPara
import cc.uling.usdk.board.wz.para.TempReplyPara
import cc.uling.usdk.constants.ErrorConst
import com.google.gson.Gson
import com.zcapi
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

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
class PowerOnOffTime {
     var enable: Boolean = false
     var onTime: Long? = null // 以毫秒为单位
     var offTime: Long? = null // 以毫秒为单位
}

@InvokeArg
class AppBrightness {
    var value: Int = 255 // [5, 255]
}

@InvokeArg
class SettingConfig {
    var enable: Boolean = true
}

@InvokeArg
class FileManager {
    var enable: Boolean = true
}

@InvokeArg
class BuildBoardRequest {
    var addr: Int? = 1
}

@InvokeArg
class ShipmentRequest {
    var addr: Int = 1 //
    var no: Int = 0
    var motorId: Int = 0
    var floorType: Int = 1
    var isDc: Boolean = false
    var isLp: Boolean = false
}

@InvokeArg
class BoxStatusRequest {
    var addr: Int = 1
    var no: Int = 0
}

@SuppressLint("SdCardPath")
const val SDCARD_DIR = "/sdcard"

const val UNAVAILABLE_VALUE = 3276.7

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
     * displayer: the display board of screen, from`zc`
     */
    private val displayer = zcapi()

    /**
     * the driver of the board
     */
    private var commid: String = "/dev/ttyS0"
    private var baudrate: Int = 9600
    private lateinit var driver: UBoard
    private lateinit var serialsDevice: MutableList<SerialDevice>

    /**
     * the env of the android build
     */
    private lateinit var buildEnv: BuildEnv

    private lateinit var buildBoard: BuildBoard

    /**
     * the init method of the plugin
     *
     * @document https://v2.tauri.app/zh-cn/develop/plugins/develop-mobile/#%E6%8F%92%E4%BB%B6%E9%85%8D%E7%BD%AE
     * @param webView the view of main activity
     * @return void
     */
    @OptIn(DelicateCoroutinesApi::class)
    override fun load(webView: WebView) {
        super.load(webView)

        // initialization of the env
        this.initBuildEnv()

        // initialization of the displayer
        this.displayer.getContext(webView.context)
        this.initDisplayer(false)

        // initialization of the driver
        USDK.getInstance().init(activity.application)
        GlobalScope.launch(Dispatchers.IO) {
            async {
                initSerialDriver()
            }.await()
        }

        // initialization of the task service
        this.startTaskService()
    }

    /**
     * start task service in background
     *
     * @param
     * @return void
     */
    private fun startTaskService() {
        // TODO: mqtt service of task
        this.activity.application.apply {
            val intent = Intent(this, TaskService::class.java)
            startService(intent)
        }
    }

    /**
     * event of the app relaunch <https://v2.tauri.app/develop/plugins/develop-mobile/#onnewintent>
     *
     * @param intent the intent
     * @return void
     */
    override fun onNewIntent(intent: Intent) {
        this.initBuildEnv()
        this.initDisplayer(false)
        Toast.makeText(activity, "welcome back", Toast.LENGTH_SHORT).show()
    }

    /**
     * initialization of the driver
     */
    private fun initSerialDriver() {
        val paths = SerialPortFinder().allDevicesPath.sorted()
        val result = mutableListOf<SerialDevice>()

        try {
            paths.forEachIndexed { index, path ->
                val item: SerialDevice = SerialDevice(
                    path = path,
                    index = index + 1,
                    active = false,
                    disabled = true
                )

                val board = USDK.getInstance().create(path)
                board.let { it ->
                    val resp = it.EF_OpenDev(path, this.baudrate)
                    if (resp != ErrorConst.MDB_ERR_NO_ERR) {
                        result.add(item)
                        return@forEachIndexed
                    }

                    val para = SVReplyPara(1)
                    it.GetSoftwareVersion(para)
                    if (para.isOK && this.commid == "/dev/ttyS0") {
                        item.active = para.isOK
                        item.disabled = false
                        this.commid = path
                        this.driver = it
                    }
                    result.add(item)
                }
            }

            this.serialsDevice = result
        } catch (e: Exception) {
            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
            throw e
        }
    }

    /**
     * initialization of env
     *
     * @param
     * @return void
     */
    private fun initBuildEnv() {
        this.buildEnv = BuildEnv(
            sdkVersion = Build.VERSION.SDK_INT,
            androidVersion = Build.VERSION.RELEASE,
            serialSn = this.displayer.buildSerial,
            modelNo = this.displayer.buildModel,
            screenWidth = 0,
            screenHeight =  0,
            baudrate = this.baudrate,
            commid = this.commid,
            brightness = 255,
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // [system bars]<https://stackoverflow.com/a/63409619/21185153>
            val windowMetrics = activity.windowManager.currentWindowMetrics
            this.buildEnv.screenHeight = windowMetrics.bounds.height()
            this.buildEnv.screenWidth = windowMetrics.bounds.width()
        } else {
            DisplayMetrics().let  { item ->
                @Suppress("DEPRECATION")
                activity.windowManager.defaultDisplay.getMetrics(item).let {
                    this.buildEnv.screenHeight = item.widthPixels
                    this.buildEnv.screenWidth = item.heightPixels
                }
            }
        }
    }

    /**
     * initialization of displayer
     *
     * @param
     * @return void
     */
    private fun initDisplayer(enable: Boolean = false) {
        this.buildEnv.modelNo.apply {
            if (this.startsWith("zc") || this.startsWith("ZC")) {
                displayer.setStatusBar(enable)
                displayer.setGestureStatusBar(enable)

                buildEnv.statusBarOn = if (enable) "1"  else "0"
                buildEnv.gestureStatusBarOn = if (enable) "1"  else "0"
            }
        }
    }

    /**
     * command of `reboot`
     *
     * @param invoke to invoke [none] { }
     * @return void
     */
    @Command
    fun reboot(invoke: Invoke) {
        this.displayer.reboot()
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
        this.displayer.shutDown()
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
        val argv = invoke.parseArgs(StatusBar::class.java).enable ?: false
        this.displayer.setStatusBar(argv)
        this.buildEnv.statusBarOn = if (argv) "1"  else "0"
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
        val argv = invoke.parseArgs(GestureStatusBar::class.java).enable ?: false
        this.displayer.setGestureStatusBar(argv)
        this.buildEnv.gestureStatusBarOn = if (argv) "1"  else "0"
        invoke.resolve()
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
        this.displayer.setLcdOnOff(argv.enable ?: true)
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

        this.displayer.setPowetOnOffTime(enable, onTime, offTime)

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
        this.initDisplayer(argv.enable)

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
        this.initDisplayer(argv.enable)

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
        this.buildEnv.brightness = args.value.coerceIn(5, 255)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.System.canWrite(activity)) {
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            startActivityForResult(invoke, intent, "")
            throw Exception("waiting for apply permission to change brightness")
        }

        try {
            Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS).let {
                Settings.System.putInt(activity.contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
                Settings.System.putInt(activity.contentResolver, Settings.System.SCREEN_BRIGHTNESS, this.buildEnv.brightness)
            }
            activity.window.attributes.let {
                it.screenBrightness = this.buildEnv.brightness / 255f
                activity.window.attributes = it
            }
        } catch (e: Exception) {
            throw e
        }

        throw Exception("adjust brightness failed because not support on Android 7.0 and above")
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
        ret.put("value", gson.toJson(this.serialsDevice))
        invoke.resolve(ret)
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
        ret.put("value", gson.toJson(this.buildEnv))
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
        this.initDisplayer(true)

        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivityForResult(invoke, intent, "")

        invoke.resolve()
    }

    /**
     * command of `takeScreenShot`
     *
     * @param invoke to invoke [none] { }
     * @return void
     * @since 1.4.0-beta.19
     */
    @Command
    fun takeScreenShot(invoke: Invoke) {
        val filename = "${System.currentTimeMillis()}.png"
        this.displayer.screenshot(SDCARD_DIR, filename)

        val ret = JSObject()
        ret.put("value", "${SDCARD_DIR}/${filename}")
        invoke.resolve()
    }

    /**
     * command of `getBuildBoard`
     *
     * @param invoke to invoke [BuildBoardRequest] { input: Int }
     * @return void
     * @since 1.5.1
     */
    @Command
    fun getBuildBoard(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }

        val addr = invoke.parseArgs(BuildBoardRequest::class.java).addr ?: 1
        this.buildBoard = BuildBoard(
            temperature = "unknown",
            humidity = "unknown",
            hardwareVersion = "0",
            boardRows = 10,
            boardColumns = 10,
            softwareVersion = "0",
        )

        // read temperature
        TempReplyPara(addr).apply {
            driver.ReadTemp(this)
        }.apply {
            if (this.isOK) {
                (this.temp / 10.0).apply {
                    if (this != UNAVAILABLE_VALUE) {
                        buildBoard.temperature = this.toString()
                    }
                }

                (this.humi / 10.0).apply {
                    if (this != UNAVAILABLE_VALUE) {
                        buildBoard.temperature = this.toString()
                    }
                }
            }
        }

        // read hardware version
        HCReplyPara(addr).apply {
            driver.ReadHardwareConfig(this)
        }.apply {
            if (this.isOK) {
                buildBoard.hardwareVersion = this.version
                buildBoard.boardRows = this.row
                buildBoard.boardColumns = this.column
            }
        }

        SVReplyPara(addr).apply {
            driver.GetSoftwareVersion(this)
        }.apply {
            if (this.isOK) {
                buildBoard.softwareVersion = this.version
            }
        }

        val gson = Gson()
        val ret = JSObject()
        ret.put("value", gson.toJson(this.buildBoard))
        invoke.resolve(ret)
    }

    /**
     * command of `execShipment`
     *
     * @param invoke to invoke [ShipmentRequest] { ...arguments }
     * @return void
     * @since 1.5.3
     */
    @Command
    fun execShipment(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }

        val args = invoke.parseArgs(ShipmentRequest::class.java)
        SReplyPara(
            args.addr,
            args.no % 100,
            args.floorType,
            args.isDc,
            args.isLp,
        ).apply {
            driver.Shipment(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("shipping failed")
            }
        }

        val ret = JSObject()
        ret.put("value", "success")
        invoke.resolve(ret)
    }

    /**
     * command of `getBoxStatus`
     * @description: 查询格子柜当前状态
     * @param invoke to invoke [BoxStatusRequest] { ...arguments }
     * @return void
     * @since 1.6.0
     */
    @Command
    fun getBoxStatus(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }

        val args = invoke.parseArgs(BoxStatusRequest::class.java)
        val para = BSReplyPara(
            args.addr,
            args.no % 100
        ).apply {
            driver.GetBoxStatus(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("get box status failed")
            }
        }

        val ret = JSObject()
        val result: Map<String, Any> = mapOf(
            "no" to para.no,
            "status" to para.status, // 0 => open, 1 => closed
        )
        ret.put("value", Gson().toJson(result))
        invoke.resolve(ret)
    }
}