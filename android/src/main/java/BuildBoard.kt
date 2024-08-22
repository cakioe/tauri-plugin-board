package com.plugin.board

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BuildBoard(
    @SerializedName("temperature")
    var temperature: String = "unknown",

    @SerializedName("humidity")
    var humidity: String = "unknown",

    @SerializedName("hardware_version")
    var hardwareVersion: String = "0",

    @SerializedName("board_rows")
    var boardRows: Int = 0,

    @SerializedName("board_columns")
    var boardColumns: Int = 0,

    @SerializedName("software_version")
    var softwareVersion: String = "0",
)