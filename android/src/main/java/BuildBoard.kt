package com.plugin.board

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Env(
    @SerializedName("driver")
    var driver: Driver = Driver(),

    @SerializedName("mdb")
    var mdb: Mdb = Mdb(),

    @SerializedName("payout")
    var payout: Payout = Payout(),

    @SerializedName("os")
    var os: OS = OS()
)

@Keep
data class Mdb(
    @SerializedName("software_version")
    var softwareVersion: String = "unknown",

    @SerializedName("hardware_version")
    var hardwareVersion: Int = 0,

    // 是否支持硬币
    @SerializedName("is_with_coin")
    var isWithCoin: Boolean = false,

    // 是否支持纸币
    @SerializedName("is_with_cash")
    var isWithCash: Boolean = false,

    // 是否支持POS
    @SerializedName("is_with_pos")
    var isWithPOS: Boolean = false,

    // 是否支持脉冲
    @SerializedName("is_with_pulse")
    var isWithPulse: Boolean = false,

    // 是否支持身份识别
    @SerializedName("is_with_identify")
    var isWithIdentify: Boolean = false,

    // 货币代码，如人民币0086，美元 0001
    @SerializedName("code")
    var code: String = "unknown"
)

@Keep
data class Driver(
    @SerializedName("software_version")
    var softwareVersion: String = "unknown",

    @SerializedName("hardware_version")
    var hardwareVersion: String = "unknown",

    @SerializedName("rows")
    var rows: Int = 0,

    @SerializedName("columns")
    var columns: Int = 0,

    @SerializedName("temperature")
    var temperature: String = "unknown",

    @SerializedName("humidity")
    var humidity: String = "unknown"
)

@Keep
data class Payout(
    @SerializedName("amount")
    var amount: Int = 0,

    @SerializedName("decimal")
    var decimal: Int = 2
)

@Keep
data class OS(
    @SerializedName("sdk_version")
    var sdkVersion: Int = 0,

    @SerializedName("android_version")
    val androidVersion: String = "unknown",

    @SerializedName("id")
    val id: String = "unknown",

    @SerializedName("name")
    val name: String = "unknown",

    @SerializedName("screen_width")
    var screenWidth: Int = 0,

    @SerializedName("screen_height")
    var screenHeight: Int = 0,

    @SerializedName("commid")
    var commid: String? = "/dev/ttyS0",

    @SerializedName("baudrate")
    var baudrate: Int = 9600,

    @SerializedName("status_bar_on")
    var statusBarOn: String = "0",

    @SerializedName("gesture_status_bar_on")
    var gestureStatusBarOn: String = "0",

    @SerializedName("brightness")
    var brightness: Int = 255,
)

@Keep
data class SerialDevice(
    @SerializedName("path")
    var path: String,

    @SerializedName("active")
    var active: Boolean,

    @SerializedName("index")
    var index: Int,

    @SerializedName("disabled")
    var disabled: Boolean
)
