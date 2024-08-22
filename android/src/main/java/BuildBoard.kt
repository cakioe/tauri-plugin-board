package com.plugin.board

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BuildBoard (
    @SerializedName("temperature")
    var temperature: Int = 0,

    @SerializedName("humidity")
    var humidity: Int = 0,

    @SerializedName("hardware_version")
    var hardwareVersion: String = "0",

    @SerializedName("board_rows")
    var boardRows: Int = 0,

    @SerializedName("board_columns")
    var boardColumns: Int = 0,

    @SerializedName("software_version")
    var softwareVersion: String = "0",
)