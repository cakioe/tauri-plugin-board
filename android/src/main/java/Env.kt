package com.plugin.board

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Env (
    @SerializedName("sdk_version")
    var sdkVersion: Int,

    @SerializedName("android_version")
    val androidVersion: String,

    @SerializedName("serial_sn")
    val serialSn: String,

    @SerializedName("model_no")
    val modelNo: String
)
