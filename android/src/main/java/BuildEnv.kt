package com.plugin.board

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BuildEnv (
    @SerializedName("sdk_version")
    var sdkVersion: Int,

    @SerializedName("android_version")
    val androidVersion: String,

    @SerializedName("serial_sn")
    val serialSn: String,

    @SerializedName("model_no")
    val modelNo: String,

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
