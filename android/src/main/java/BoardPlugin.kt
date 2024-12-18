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
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import app.tauri.annotation.Command
import app.tauri.annotation.InvokeArg
import app.tauri.annotation.TauriPlugin
import app.tauri.plugin.Invoke
import app.tauri.plugin.JSObject
import app.tauri.plugin.Plugin
import cc.uling.usdk.USDK
import cc.uling.usdk.board.UBoard
import cc.uling.usdk.board.mdb.para.ACReplyPara
import cc.uling.usdk.board.mdb.para.ARReplyPara
import cc.uling.usdk.board.mdb.para.ASReplyPara
import cc.uling.usdk.board.mdb.para.BRReplyPara
import cc.uling.usdk.board.mdb.para.CBReplyPara
import cc.uling.usdk.board.mdb.para.CSReplyPara
import cc.uling.usdk.board.mdb.para.IPReplyPara
import cc.uling.usdk.board.mdb.para.MPReplyPara
import cc.uling.usdk.board.mdb.para.PBReplyPara
import cc.uling.usdk.board.mdb.para.PCReplyPara
import cc.uling.usdk.board.mdb.para.PMReplyPara
import cc.uling.usdk.board.mdb.para.PayReplyPara
import cc.uling.usdk.board.mdb.para.ResultReplyPara
import cc.uling.usdk.board.mdb.para.STReplyPara
import cc.uling.usdk.board.mdb.para.WMReplyPara
import cc.uling.usdk.board.wz.para.BSReplyPara
import cc.uling.usdk.board.wz.para.CXReplyPara
import cc.uling.usdk.board.wz.para.CYReplyPara
import cc.uling.usdk.board.wz.para.DSReplyPara
import cc.uling.usdk.board.wz.para.HCReplyPara
import cc.uling.usdk.board.wz.para.MTReplyPara
import cc.uling.usdk.board.wz.para.PXReplyPara
import cc.uling.usdk.board.wz.para.PYReplyPara
import cc.uling.usdk.board.wz.para.RMReplyPara
import cc.uling.usdk.board.wz.para.ResetReplyPara
import cc.uling.usdk.board.wz.para.SReplyPara
import cc.uling.usdk.board.wz.para.SSReplyPara
import cc.uling.usdk.board.wz.para.SVReplyPara
import cc.uling.usdk.board.wz.para.SXPReplyPara
import cc.uling.usdk.board.wz.para.SYPReplyPara
import cc.uling.usdk.board.wz.para.TXReplyPara
import cc.uling.usdk.board.wz.para.TYReplyPara
import cc.uling.usdk.board.wz.para.XSReplyPara
import cc.uling.usdk.board.wz.para.YSReplyPara
import cc.uling.usdk.constants.CodeUtil
import cc.uling.usdk.constants.ErrorConst
import com.google.gson.Gson
import com.plugin.board.database.Configs
import com.plugin.board.database.Database
import com.plugin.board.database.Floor_types
import com.plugin.board.database.Machines
import com.plugin.board.database.Serial_devices
import com.zcapi
import io.github.cakioe.Carbon
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File

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
class ShipmentRequest {
    var addr: Int = 1 //
    var code: Int = 0
    var floorType: Int = 1
    var isDc: Boolean = false
    var isLp: Boolean = false
}

@InvokeArg
class BoxStatusRequest {
    var addr: Int = 1
    var code: Int = 0
}

@InvokeArg
class AddrRequest {
    var addr: Int = 1
}

@InvokeArg
class RunMotoRequest {
    var addr: Int = 1
    var mode: Short = 0
    var status: Short = 0
}

@InvokeArg
class XYPosRequest {
    var addr: Int = 1
    var values: Array<Int> = Array(10) { 0 }
}

@InvokeArg
class XYRequest {
    var addr: Int = 1
    var value: Short = 0
}

@InvokeArg
class PaymentRequest {
    var addr: Int? = null
    val no: Int = 0
    val multiple: Int = 0
}

@InvokeArg
class FlagRequest {
    var flag: Boolean = false
}

@InvokeArg
class BalanceRequest {
    var multiple: Int = 1
}

@InvokeArg
class AcceptMoneyRequest {
    var type: Int = 0 // 0- 硬币, 1- 纸币
    var channels: IntArray = IntArray(16)
}

@InvokeArg
class AgeRequest {
    var age: Int = 12
}

@InvokeArg
class ModeRequest {
    var mode: Int = 0
}

@InvokeArg
class PulseBalanceRequest {
    var type: Int = 0
    var value: Int = 0
}

@InvokeArg
class MotoTimeoutRequest {
    var addr: Int = 1
    var time: Short = 1
}

@InvokeArg
class PickXYRequest {
    var addr: Int = 1
    var pos: Short = 1
    var mode: Int = 0
}

@InvokeArg
class ConfigOption {
    val no: String? = null
}

@SuppressLint("SdCardPath")
const val SDCARD_DIR = "/sdcard"

const val UNAVAILABLE_VALUE = 3276.7

@InvokeArg
@Suppress("unused")
class PluginOptions {
    var protocol: String = "mqtt"
    var broker: String = "broker.emqx.io"
    var port: Int = 1883
    var username: String = ""
    var password: String = ""
    var merchant_id: String = ""
    var app_key: String = ""
}

/**
 * vending board plugin of tauri for android use kotlin
 * @author: <cleveng@gmail.com>
 *
 * @constructor creates a plugin for tauri
 * @property activity of main activity
 */
@TauriPlugin
class BoardPlugin(private val activity: Activity) : Plugin(activity) {
    /**
     * displayer: the display board of screen, from`zc`
     */
    private val displayer = zcapi()

    /**
     * the driver of the board
     */
    private var baudrate: Int = 9600
    private lateinit var driver: UBoard
    private lateinit var serialsDevice: MutableList<Serial_devices>

    // the env of the android build, config instance of configs
    private lateinit var configInstance: Configs

    // machine instance of machines
    private lateinit var machineInstance: Machines

    private var taskRunning: Boolean = false
    private lateinit var database: Database
    private lateinit var options: PluginOptions

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

        // 从 tauri.config.json 获取参数
        getConfig(PluginOptions::class.java).let {
            this.options = it
        }

        // initialization of the displayer
        this.displayer.getContext(webView.context)

        // initialization of the task service
        AndroidSqliteDriver(
            Database.Schema,
            activity.application,
            File(this.activity.getExternalFilesDir(null), "default.db").absolutePath,
        ).let {
            this.database = Database(it)
        }

        // initialization of the config
        this.initConfig()
        this.initDisplayer(false)

        // initialization of the driver
        USDK.getInstance().init(activity.application)
        GlobalScope.launch(Dispatchers.IO) {
            async {
                initSerialDriver()
            }.await()
        }

        // [check the initialization state]<https://stackoverflow.com/a/46584412>
        if (this::database.isInitialized) {
            this.startTaskService()
        }
    }

    /**
     * start task service in background
     *
     * @param
     * @return void
     */
    private fun startTaskService() {
        if (this.taskRunning || !this::options.isInitialized) return

        this.database.machineQueries.find().executeAsOneOrNull()?.let {
            this.machineInstance = it

            this.activity.application.apply {
                val intent = Intent(this, TaskService::class.java)
                intent.putExtra("no", machineInstance.no)
                options.let { self ->
                    intent.putExtra("options", Gson().toJson(self))
                }
                startService(intent)
                taskRunning = true
            }
        }
    }

    /**
     * stop task service
     *
     */
    private fun stopTaskService() {
        if (!this.taskRunning) return

        this.activity.application.apply {
            taskRunning = false
            val intent = Intent(this, TaskService::class.java)
            stopService(intent)
        }
    }

    /**
     * event of the app relaunch <https://v2.tauri.app/develop/plugins/develop-mobile/#onnewintent>
     *
     * @param intent the intent
     * @return void
     */
    override fun onNewIntent(intent: Intent) {
        // 退出界面重新进入程序后 isInitialized的数据仍然保持
        this.initConfig()
        this.initDisplayer(false)
        this.startTaskService()

        Toast.makeText(activity, "welcome back", Toast.LENGTH_SHORT).show()
    }

    /**
     * initialization of the driver
     */
    private fun initSerialDriver(addr: Int = 1) {
        val records = mutableListOf<Serial_devices>()
        val paths = SerialPortFinder().allDevicesPath.sorted()
        try {
            paths.forEachIndexed { index, path ->
                val board = USDK.getInstance().create(path)
                board.let { it ->
                    var device = Serial_devices(
                        id = index.toLong() + 1,
                        path = path,
                        index = index.toLong() + 1,
                        active = 0,
                        disabled = 1,
                    )
                    val resp = it.EF_OpenDev(path, this.baudrate)
                    if (resp != ErrorConst.MDB_ERR_NO_ERR) {
                        records.add(device)
                        return@forEachIndexed
                    }

                    val para = SVReplyPara(addr)
                    it.GetSoftwareVersion(para)
                    if (para.isOK && !this::driver.isInitialized) {
                        device = device.copy(active = 1, disabled = 0)
                        this.driver = it
                        this.configInstance = this.configInstance.copy(commid = path)
                    }
                    records.add(device)
                }
            }

            this.serialsDevice = records

            // 读取驱动版 xy轴数量
            if (this::driver.isInitialized) {
                HCReplyPara(addr).apply {
                    driver.ReadHardwareConfig(this)
                }.apply {
                    if (this.isOK) {
                        configInstance = configInstance.copy(
                            rows = this.row.toLong(),
                            columns = this.column.toLong()
                        )
                    }
                }

                // 获取mdb配置
                cc.uling.usdk.board.mdb.para.HCReplyPara().apply {
                    driver.readHardwareConfig(this)
                }.let {
                    if (it.isOK) {
                        this.configInstance = this.configInstance.copy(
                            is_with_coin = if (it.isWithCoin) 1 else 0,
                            is_with_cash = if (it.isWithCash) 1 else 0,
                            is_with_pos = if (it.isWithPOS) 1 else 0,
                            is_with_pulse = if (it.isWithPulse) 1 else 0,
                            is_with_identify = if (it.isWithIdentify) 1 else 0,
                            currency_code = it.code,
                        )
                    }
                }

                // 读取外设支持最小面额
                MPReplyPara().apply {
                    driver.getMinPayoutAmount(this)
                }.let {
                    if (it.isOK) {
                        this.configInstance = this.configInstance.copy(
                            currency_unit = it.value.toLong(),
                            currency_decimal = it.decimal.toLong()
                        )
                    }
                }
            }
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
    private fun initConfig() {
        if (this::configInstance.isInitialized) return

        var screenHeight: Int
        var screenWidth: Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // [system bars]<https://stackoverflow.com/a/63409619/21185153>
            val windowMetrics = activity.windowManager.currentWindowMetrics
            screenHeight = windowMetrics.bounds.height()
            screenWidth = windowMetrics.bounds.width()
        } else {
            DisplayMetrics().let { item ->
                @Suppress("DEPRECATION")
                activity.windowManager.defaultDisplay.getMetrics(item).let {
                    screenHeight = item.widthPixels
                    screenWidth = item.heightPixels
                }
            }
        }

        this.configInstance = Configs(
            id = 1,
            sdk_version = Build.VERSION.SDK_INT.toString(),
            android_version = Build.VERSION.RELEASE,
            serial_sn = this.displayer.buildSerial,
            model_no = this.displayer.buildModel,
            screen_width = screenWidth.toLong(),
            screen_height = screenHeight.toLong(),
            baudrate = this.baudrate.toLong(),
            commid = "/dev/ttyS0",
            status_bar_on = 0,
            gesture_status_bar_on = 0,
            brightness = 255,
            rows = 0,
            columns = 0,
            is_with_identify = 0,
            is_with_cash = 0,
            is_with_coin = 0,
            is_with_pulse = 0,
            is_with_pos = 0,
            currency_code = "unknown",
            currency_unit = 1,
            currency_decimal = 2,
        )
    }

    /**
     * initialization of displayer
     *
     * @param
     * @return void
     */
    private fun initDisplayer(enable: Boolean = false) {
        this.configInstance.model_no.apply {
            if (this.startsWith("zc") || this.startsWith("ZC")) {
                displayer.setStatusBar(enable)
                displayer.setGestureStatusBar(enable)

                configInstance = configInstance.copy(
                    status_bar_on = if (enable) 1 else 0,
                    gesture_status_bar_on = if (enable) 1 else 0
                )
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
        this.stopTaskService()
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
        this.stopTaskService()
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
        this.configInstance = this.configInstance.copy(status_bar_on = if (argv) 1 else 0)
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
        this.configInstance = this.configInstance.copy(gesture_status_bar_on = if (argv) 1 else 0)
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

        val enable = args.enable
        val onTime = Carbon(args.onTime as Long).toIntArray()
        val offTime = Carbon(args.offTime as Long).toIntArray()

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
        this.configInstance =
            this.configInstance.copy(brightness = args.value.coerceIn(5, 255).toLong())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.System.canWrite(activity)) {
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            startActivityForResult(invoke, intent, "")
            throw Exception("waiting for apply permission to change brightness")
        }

        try {
            Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS).let {
                Settings.System.putInt(
                    activity.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
                )
                Settings.System.putInt(
                    activity.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS,
                    this.configInstance.brightness!!.toInt()
                )
            }
            activity.window.attributes.let {
                it.screenBrightness = this.configInstance.brightness!! / 255f
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
     * @return json { value: Serial_devices[] }
     */
    @Command
    fun getSerialDevicesPath(invoke: Invoke) {
        val gson = Gson()
        val ret = JSObject()
        if (!this::serialsDevice.isInitialized) {
            this.initSerialDriver()
        }

        ret.put("value", gson.toJson(this.serialsDevice))
        invoke.resolve(ret)
    }

    /**
     * command of `getConfig`
     *
     * @param invoke to invoke [none] { }
     * @return void
     * @since 1.7.0-beta.3
     */
    @Command
    fun getConfig(invoke: Invoke) {
        val args = invoke.parseArgs(ConfigOption::class.java)
        args.no?.let {
            this.machineInstance = if (this::machineInstance.isInitialized) {
                this.machineInstance.copy(no = it)
            } else {
                Machines(
                    no = it,
                    id = 0,
                    name = "",
                    client_id = "",
                    imei = "",
                    contact = "",
                    tel = "",
                    temperature = 0,
                    humidity = 0
                )
            }
        }

        val gson = Gson()
        val ret = JSObject()
        ret.put("value", gson.toJson(this.configInstance))
        invoke.resolve(ret)
    }

    /**
     * command of `getConfig`
     *
     * @param invoke to invoke [none] { }
     * @return void
     * @since 1.7.2
     */
    @Command
    fun getFloorTypes(invoke: Invoke) {
        val gson = Gson()
        val ret = JSObject()

        var result: List<Floor_types> = emptyList()
        if (this::database.isInitialized) {
            this.database.floorTypeQueries.select().executeAsList().let {
                result = it
            }
        }

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
     * command of `runShipment`
     *
     * @description: 控制驱动板出货 | p23
     * @param invoke to invoke [ShipmentRequest] { ...arguments }
     * @return void
     * @since 1.5.3
     */
    @Command
    fun runShipment(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }

        val args = invoke.parseArgs(ShipmentRequest::class.java)
        SReplyPara(
            args.addr,
            args.code % 100,
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
     *
     * @description: 查询格子柜当前状态 | p12
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
            args.code % 100
        ).apply {
            driver.GetBoxStatus(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("get box status failed")
            }
        }

        val ret = JSObject()
        val result: Map<String, Any> = mapOf(
            "code" to para.no,
            "status" to para.status, // 0 => open, 1 => closed
        )
        ret.put("value", Gson().toJson(result))
        invoke.resolve(ret)
    }

    /**
     * command of `getYPos`
     *
     * @description: 查询升降电机当前位置 | p12
     * @param invoke to invoke [AddrRequest] { ...arguments }
     * @return void
     * @since 1.6.0
     */
    @Command
    fun getYPos(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }

        val args = invoke.parseArgs(AddrRequest::class.java)
        val para = CYReplyPara(
            args.addr
        ).apply {
            driver.GetYPos(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("get y pos failed")
            }
        }

        val ret = JSObject()
        ret.put("value", para.value)
        invoke.resolve(ret)
    }

    /**
     * command of `getXPos`
     *
     * @description: 查询水平电机当前位置 | p13
     * @param invoke to invoke [AddrRequest] { ...arguments }
     * @return void
     * @since 1.6.0
     */
    @Command
    fun getXPos(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }

        val args = invoke.parseArgs(AddrRequest::class.java)
        val para = CXReplyPara(
            args.addr
        ).apply {
            driver.GetXPos(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("get x pos failed")
            }
        }

        val ret = JSObject()
        ret.put("value", para.value)
        invoke.resolve(ret)
    }

    /**
     * command of `getDropStatus`
     *
     * @description: 查询掉货检测状态 | p13
     * @param invoke to invoke [AddrRequest] { ...arguments }
     * @return void
     */
    @Command
    fun getDropStatus(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }
        val args = invoke.parseArgs(AddrRequest::class.java)
        val para = DSReplyPara(
            args.addr
        ).apply {
            driver.GetDropStatus(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("get drop status failed")
            }
        }

        val ret = JSObject()
        // 0- 掉货检测未连接或者被遮挡
        // 1- 掉货检测正常对射无遮挡
        ret.put("value", para.status)
        invoke.resolve(ret)
    }

    /**
     * command of `getYStatus`
     *
     * @description: 读取 Y 轴升降电机控制板状态 | p16
     * @param invoke to invoke [AddrRequest] { ...arguments }
     * @return void
     * @since 1.6.0
     */
    @Command
    fun getYStatus(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }
        val args = invoke.parseArgs(AddrRequest::class.java)
        val para = YSReplyPara(
            args.addr
        ).apply {
            driver.GetYStatus(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("get y status failed")
            }
        }
        val ret = JSObject()
        val result: Map<String, Any> = mapOf(
            "run_status" to para.runStatus,
            "status_message" to CodeUtil.getXYStatusMsg(para.runStatus),
            "fault_code" to para.faultCode,
            "fault_message" to CodeUtil.getFaultMsg(para.faultCode),
        )
        ret.put("value", Gson().toJson(result))
        invoke.resolve(ret)
    }

    /**
     * command of `getXStatus`
     *
     * @description: 读取 X 轴移动电机控制板状态 | p17
     * @param invoke to invoke [AddrRequest] { ...arguments }
     * @return void
     * @since 1.6.0
     */
    @Command
    fun getXStatus(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }
        val args = invoke.parseArgs(AddrRequest::class.java)
        val para = XSReplyPara(
            args.addr
        ).apply {
            driver.GetXStatus(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("get x status failed")
            }
        }
        val ret = JSObject()
        val result: Map<String, Any> = mapOf(
            "run_status" to para.runStatus,
            "status_message" to CodeUtil.getXYStatusMsg(para.runStatus),
            "fault_code" to para.faultCode,
            "fault_message" to CodeUtil.getFaultMsg(para.faultCode),
        )
        ret.put("value", Gson().toJson(result))
        invoke.resolve(ret)
    }

    /**
     * command of `resetLift`
     *
     * @description: 升降机复位 | p20
     * @param invoke to invoke [AddrRequest] { ...arguments }
     * @return void
     * @since 1.6.0
     */
    @Command
    fun resetLift(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }

        val args = invoke.parseArgs(AddrRequest::class.java)
        ResetReplyPara(
            args.addr
        ).apply {
            driver.ResetLift(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("reset lift failed")
            }
        }
        val ret = JSObject()
        ret.put("value", "success")
        invoke.resolve(ret)
    }

    /**
     * command of `runMoto`
     *
     * @description: 电机手动模式 | p20
     * @param invoke to invoke [RunMotoRequest] { ...arguments }
     * @return void
     * @since 1.6.0
     */
    @Command
    fun runMoto(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }
        val args = invoke.parseArgs(RunMotoRequest::class.java)
        RMReplyPara(
            args.addr,
            args.mode,
            args.status,
        ).apply {
            driver.RunMoto(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("run moto failed")
            }
        }
        invoke.resolve()
    }

    /**
     * command of `getShipmentStatus`
     *
     * @description: 获取出货状态 | p24
     * @param invoke to invoke [AddrRequest] { ...arguments }
     * @return void
     * @since 1.6.0
     */
    @Command
    fun getShipmentStatus(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }
        val args = invoke.parseArgs(AddrRequest::class.java)
        val para = SSReplyPara(
            args.addr
        ).apply {
            driver.GetShipmentStatus(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("get shipment status failed")
            }
        }

        val ret = JSObject()
        val result: Map<String, Any> = mapOf(
            "status_code" to para.runStatus,
            "status_message" to CodeUtil.getXYStatusMsg(para.runStatus).replace("idle", ""),
            "error_code" to para.faultCode,
            "error_message" to CodeUtil.getFaultMsg(para.faultCode),
        )
        ret.put("value", Gson().toJson(result))
        invoke.resolve(ret)
    }

    /**
     * command of `setXPos`
     *
     * @description: 设置 X 轴移动电机控制板位置 | p23
     * @param invoke to invoke [XYPosRequest] { ...arguments }
     * @return void
     * @since 1.6.0
     */
    @Command
    fun setXPos(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }
        val args = invoke.parseArgs(XYPosRequest::class.java)
        SXPReplyPara(
            args.addr,
            args.values[0],
            args.values[1],
            args.values[2],
            args.values[3],
            args.values[4],
            args.values[5],
            args.values[6],
            args.values[7],
            args.values[8],
            args.values[9],
        ).apply {
            driver.SeXPos(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("set x pos failed")
            }
        }

        val ret = JSObject()
        ret.put("value", "success")
        invoke.resolve()
    }

    /**
     * command of `setYPos`
     *
     * @description: 设置升降电机 Y 轴寻址位置 | p22
     * @param invoke to invoke [XYPosRequest] { ...arguments }
     * @return void
     * @since 1.6.0
     */
    @Command
    fun setYPos(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }

        val args = invoke.parseArgs(XYPosRequest::class.java)
        SYPReplyPara(
            args.addr,
            args.values[0],
            args.values[1],
            args.values[2],
            args.values[3],
            args.values[4],
            args.values[5],
            args.values[6],
            args.values[7],
            args.values[8],
            args.values[9],
        ).apply {
            driver.SeYPos(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("set y pos failed")
            }
        }

        val ret = JSObject()
        ret.put("value", "success")
        invoke.resolve()
    }

    /**
     * command of `toX`
     *
     * @description: X 轴电机寻址 | p22
     * @param invoke to invoke [XYRequest] { ...arguments }
     * @return void
     * @since 1.6.0
     */
    @Command
    fun toX(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }
        val args = invoke.parseArgs(XYRequest::class.java)
        TXReplyPara(
            args.addr,
            args.value
        ).apply {
            driver.ToX(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("to x failed")
            }
        }

        val ret = JSObject()
        ret.put("value", "success")
        invoke.resolve()
    }

    /**
     * command of `toY`
     *
     * @description: Y 轴电机寻址 | p22
     * @param invoke to invoke [XYRequest] { ...arguments }
     * @return void
     * @since 1.6.0
     */
    @Command
    fun toY(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }
        val args = invoke.parseArgs(XYRequest::class.java)
        TYReplyPara(
            args.addr,
            args.value
        ).apply {
            driver.ToY(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("to y failed")
            }
        }

        val ret = JSObject()
        ret.put("value", "success")
        invoke.resolve()
    }

    /**
     * command of `getPayAmount`
     *
     * @description: 查询 MDB 收款金额 | p4
     * @param invoke to invoke [none] { }
     * @return void
     * @since 1.6.1
     */
    @Command
    fun getPayAmount(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }
        val para = PMReplyPara().apply {
            driver.getPayAmount(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("get pay amount failed")
            }
        }

        val ret = JSObject()
        val result: Map<String, Any> = mapOf(
            "pay_type" to para.payType,
            "status" to para.status,
            "multiple" to para.multiple,
            "cancel" to para.cancel,
            "fault" to if (para.status == 1) 0 else para.fault,
        )
        ret.put("value", Gson().toJson(result))
        invoke.resolve(ret)
    }

    /**
     * command of `initPayment`
     *
     * @description: 发起收款 | p5
     * @param invoke to invoke [none] { }
     * @return void
     * @since 1.6.1
     */
    @Command
    fun initPayment(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }
        val args = invoke.parseArgs(PaymentRequest::class.java)
        IPReplyPara(
            (args.no % 100).toShort(),
            args.multiple
        ).apply {
            driver.initPayment(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("init payment failed")
            }
        }
        val ret = JSObject()
        ret.put("value", "success")
        invoke.resolve(ret)
    }

    /**
     * command of `notifyPayment`
     *
     * @description: 收款通知 | p6
     * @param invoke to invoke [none] { }
     * @return void
     * @since 1.6.1
     */
    @Command
    fun notifyPayment(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }
        val args = invoke.parseArgs(FlagRequest::class.java)
        PayReplyPara(
            args.flag
        ).apply {
            driver.notifyPayment(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("notify payment failed")
            }
        }
        val ret = JSObject()
        ret.put("value", "success")
        invoke.resolve(ret)
    }

    /**
     * command of `notifyResult`
     *
     * @description: 通知机器售卖结果 | p6
     * @param invoke to invoke [none] { }
     * @return void
     * @since 1.6.1
     */
    @Command
    fun notifyResult(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }
        val args = invoke.parseArgs(FlagRequest::class.java)
        ResultReplyPara(
            args.flag
        ).apply {
            driver.notifyResult(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("notify result failed")
            }
        }
        val ret = JSObject()
        ret.put("value", "success")
        invoke.resolve(ret)
    }

    /**
     * command of `changeBalance`
     *
     * @description: 发起找零 | p6
     * @param invoke to invoke [none] { }
     * @return void
     * @since 1.6.1
     */
    @Command
    fun changeBalance(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }
        val args = invoke.parseArgs(BalanceRequest::class.java)
        CBReplyPara(
            args.multiple
        ).apply {
            driver.changeBalance(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("notify result failed")
            }
        }
        val ret = JSObject()
        ret.put("value", "success")
        invoke.resolve(ret)
    }

    /**
     * command of `getChangeStatus`
     *
     * @description: 查询找零状态 | p6
     * @param invoke to invoke [none] { }
     * @return void
     * @since 1.6.1
     */
    @Command
    fun getChangeStatus(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }

        val para = CSReplyPara().apply {
            driver.getChangeStatus(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("get change status failed")
            }
        }

        val ret = JSObject()
        val result: Map<String, Any> = mapOf(
            "status" to para.status,
            "multiple" to para.multiple,
        )
        ret.put("value", Gson().toJson(result))
        invoke.resolve(ret)
    }

    /**
     * command of `findChangeResult`
     *
     * @description: 查询硬币器找零结果 | p6
     * @param invoke to invoke [none] { }
     * @return void
     * @since 1.6.1
     */
    @Command
    fun findChangeResult(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }

        val para = BRReplyPara().apply {
            driver.findChangeResult(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("get change status failed")
            }
        }

        val ret = JSObject()
        val result: Array<Int> = Array(16) { 0 }
        for (i in 0 until 16) {
            result[i] = para.getCount(i + 1)
        }
        ret.put("value", Gson().toJson(result))
        invoke.resolve(ret)
    }

    /**
     * command of `setAcceptMoney`
     *
     * @description: 设置硬（纸）币器允许币种 | p7
     * @param invoke to invoke [AcceptMoneyRequest] { }
     * @return void
     * @since 1.6.1
     */
    @Command
    fun setAcceptMoney(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }

        val args = invoke.parseArgs(AcceptMoneyRequest::class.java)
        ACReplyPara(
            args.type,
            args.channels
        ).apply {
            driver.setAcceptMoney(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("get change status failed")
            }
        }

        val ret = JSObject()
        ret.put("value", "success")
        invoke.resolve(ret)
    }

    /**
     * command of `syncSystemTime`
     *
     * @description: 同步系统时间 | p9
     * @param
     * @return void
     * @since 1.6.1
     */
    @Command
    fun syncSystemTime(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }

        STReplyPara().apply {
            driver.syncSystemTime(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("sync system time failed")
            }
        }

        val ret = JSObject()
        ret.put("value", "success")
        invoke.resolve(ret)
    }

    /**
     * command of `setAgeScope`
     *
     * @description: 设置年龄限制 | p9
     * @param
     * @return void
     * @since 1.6.1
     */
    @Command
    fun setAgeScope(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }

        val args = invoke.parseArgs(AgeRequest::class.java)
        ASReplyPara(
            args.age
        ).apply {
            driver.setAgeScope(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("sync system time failed")
            }
        }

        val ret = JSObject()
        ret.put("value", "success")
        invoke.resolve(ret)
    }

    /**
     * command of `getAuthResult`
     *
     * @description: 查询身份认证是否成功 | p9
     * @param
     * @return void
     * @since 1.6.1
     */
    @Command
    fun getAuthResult(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }

        val para = ARReplyPara().apply {
            driver.getAuthResult(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("get auth result failed")
            }
        }

        val ret = JSObject()
        ret.put("value", para.status)
        invoke.resolve(ret)
    }

    /**
     * command of `setWorkMode`
     *
     * @description: 设置进入（退出）补币模式 | p9
     * @param
     * @return void
     * @since 1.6.1
     */
    @Command
    fun setWorkMode(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }

        val args = invoke.parseArgs(ModeRequest::class.java)
        WMReplyPara(
            args.mode
        ).apply {
            driver.setWorkMode(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("set work mode failed")
            }
        }

        val ret = JSObject()
        ret.put("value", "success")
        invoke.resolve(ret)
    }

    /**
     * command of `setPayChannel`
     *
     * @description: 设置收款方式 | p10
     * @param
     * @return void
     * @since 1.6.1
     */
    @Command
    fun setPayChannel(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }
        val args = invoke.parseArgs(ModeRequest::class.java)
        PCReplyPara(
            args.mode
        ).apply {
            driver.setPayChannel(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("set pay channel failed")
            }
        }
        val ret = JSObject()
        ret.put("value", "success")
        invoke.resolve(ret)
    }

    /**
     * command of `pulseBalance`
     *
     * @description: 脉冲找零 | p10
     * @param
     * @return void
     * @since 1.6.1
     */
    @Command
    fun pulseBalance(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }
        val args = invoke.parseArgs(PulseBalanceRequest::class.java)
        PBReplyPara(
            args.type,
            args.value
        ).apply {
            driver.pulseBalance(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("set pay channel failed")
            }
        }
        val ret = JSObject()
        ret.put("value", "success")
        invoke.resolve(ret)
    }

    /**
     * command of `motoTimeout`
     *
     * @description: 设置电机超时时间 | p20
     * @param
     * @return void
     * @since 1.6.1
     */
    @Command
    fun motoTimeout(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }
        val args = invoke.parseArgs(MotoTimeoutRequest::class.java)
        MTReplyPara(
            args.addr,
            args.time
        ).apply {
            driver.MotoTimeout(this)
        }.apply {
            if (!this.isOK) {
                throw Exception("set moto timeout failed")
            }
        }
        val ret = JSObject()
        ret.put("value", "success")
        invoke.resolve(ret)
    }

    /**
     * command of `setPickXY`
     *
     * @description: 设置取货口 X/Y 轴位置 | p21
     * @param
     * @return void
     * @since 1.6.1
     */
    @Command
    fun setPickXY(invoke: Invoke) {
        if (!this.driver.EF_Opened()) {
            throw Exception("driver not opened")
        }
        val args = invoke.parseArgs(PickXYRequest::class.java)
        if (args.mode == 0) {
            PXReplyPara(
                args.addr,
                args.pos
            ).apply {
                driver.SetPickX(this)
            }.apply {
                if (!this.isOK) {
                    throw Exception()
                }
            }
        } else {
            PYReplyPara(
                args.addr,
                args.pos
            ).apply {
                driver.SetPickY(this)
            }.apply {
                if (!this.isOK) {
                    throw Exception()
                }
            }
        }

        val ret = JSObject()
        ret.put("value", "success")
        invoke.resolve(ret)
    }
}