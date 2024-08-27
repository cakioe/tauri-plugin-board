package com.plugin.board

import java.util.Calendar
import java.util.Date

class Carbon {
    /**
     * @description: parse timestamp
     */
    fun parseTimestamp(timestamp: Long?): IntArray? {
        return timestamp?.let {
            val date = Date(it)
            val calendar = Calendar.getInstance()
            calendar.time = date

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1 // 月份从0开始，所以需要加1
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val hour = calendar.get(Calendar.HOUR_OF_DAY) // 24小时制
            val minute = calendar.get(Calendar.MINUTE)

            intArrayOf(year, month, day, hour, minute)
        }
    }
}