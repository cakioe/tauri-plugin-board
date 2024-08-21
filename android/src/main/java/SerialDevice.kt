package com.plugin.board

import android.serialport.SerialPortFinder
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/***
 * GSON<https://stackoverflow.com/a/46156695/21185153>
 */
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

fun SerialPortFinder.getAvailableSerialDevices():MutableList<String> {
    val result = mutableListOf<String>()
    try {
        val command = arrayOf("sh", "-c", "ls -l /dev/tty*")
        val process = Runtime.getRuntime().exec(command)
        val lines = BufferedReader(InputStreamReader(process.inputStream)).readLines()

        for (line in lines) {
            val parts = line.split(Regex("\\s+"))
            if (parts.size > 2) {
                val filename = parts.last() // 文件名
                val owner = parts[2] // 文件的所有者
                if (owner == "system") {
                    result.add(filename) // 添加符合条件的文件
                }
            }
        }
        process.waitFor()

        return result
    } catch (e: IOException) {
        throw e
    }
}
