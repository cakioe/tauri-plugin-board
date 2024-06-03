package com.plugin.board

import android.util.Log

class Device {
    fun pong(value: String): String {
        Log.i("Pong", value)
        return value
    }
}
