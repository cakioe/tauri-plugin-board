package com.plugin.board.ui

import android.content.Context
import android.os.Environment
import com.ys.rkapi.MyManager

class YS : Board {
    private lateinit var ui: MyManager

    /**
     * Initializes the ui.
     *
     * This function initializes the ui by setting up the display manager
     * and hiding the status and navigation bars.
     *
     * @param context the context of the application
     */
    override fun initialize(context: Context) {
        this.ui = MyManager.getInstance(context).apply {
            setSlideShowNavBar(false)
            hideStatusBar(false)
            hideNavBar(true)
        }
    }

    /**
     * Reboots the device.
     *
     * 重新启动设备
     *
     * This function triggers the `reboot` method on the `ui`,
     * causing the device to restart.
     */
    override fun reboot() {
        this.ui.reboot()
    }


    /**
     * Shuts down the device.
     *
     * 关闭设备
     *
     * This function triggers the `shutdown` method on the `ui`,
     * causing the device to turn off.
     */
    override fun shutdown() {
        this.ui.shutdown()
    }

    /**
     * Checks if the power on time is set.
     *
     * 检查是否设置了定时开机时间
     *
     * This function queries the `ui` to determine if a scheduled
     * power on time has been configured.
     *
     * @return true if the power on time is set, false otherwise
     */
    override fun isSetPowerOnTime(): Boolean {
        return this.ui.isSetPowerOnTime
    }

    /**
     * Clears the power on/off time settings.
     *
     * 清除定时开关机数据
     *
     * This function invokes the `clearPowerOnOffTime` method on the
     * `ui` to reset any scheduled power on/off times to their default state.
     */
    override fun clearPowerOnOffTime() {
        this.ui.clearPowerOnOffTime()
    }

    /**
     * Retrieves the serial number of the device.
     *
     * @return the serial number of the device as a String
     */
    override fun getSerialNo(): String {
        return this.ui.androidModle
    }

    /**
     * Captures a screenshot of the device's display.
     *
     * This function captures the current display of the device as a PNG image
     * and saves it to the SD card as a file named with the current timestamp.
     * The name of the file is returned as a String.
     *
     * @return the name of the screenshot file as a String
     */
    override fun takeScreenshot(): String {
        val filename = "${System.currentTimeMillis()}.png"
        val filepath = "${Environment.getExternalStorageDirectory().path}/${filename}"
        this.ui.takeScreenshot(filepath)

        return filepath
    }

    /**
     * Sets the visibility of the status and navigation bars.
     *
     * This function controls the visibility of both the status bar and the navigation bar.
     * When enabled, both bars will be visible; otherwise, they will be hidden.
     *
     * @param enable true to show the status and navigation bars, false to hide them
     */
    override fun setStatusBar(enable: Boolean) {
        this.ui.hideStatusBar(enable)
    }

    /**
     * Sets the gesture status bar state.
     * Enables or disables sliding out of the status bar
     * if `true`, allow
     * or `false`, disallow
     *
     * This function sets the gesture status bar to be either visible or hidden.
     * The gesture status bar is the area at the bottom of the screen that allows
     * users to interact with gesture-based navigation.
     *
     * @param enable true to show the gesture status bar, false to hide it
     */
    override fun setGestureStatusBar(enable: Boolean) {
        this.ui.setSlideShowNavBar(enable)
        this.ui.hideNavBar(!enable)
    }

    /**
     * Configures the power on/off schedule.
     *
     * This function sets a scheduled power on/off time for the device using the
     * provided on and off times. The schedule is applied immediately on the device.
     *
     * @param onTime an array of integers representing the time to power on
     * @param offTime an array of integers representing the time to power off
     */
    override fun setPowerOnOff(onTime: IntArray, offTime: IntArray) {
        this.ui.setPowerOnOff(onTime, offTime)
    }

    /**
     * Configures the power on/off schedule with weekly settings.
     *
     * This function sets a scheduled power on/off time for the device on specific weekdays.
     * The schedule is defined by specifying the on and off times along with the weekdays
     * on which the schedule should be active. The schedule is applied immediately on the device.
     *
     * @param onTime an array of integers representing the time to power on, eg: {8,30}
     * @param offTime an array of integers representing the time to power off, eg: {8,30}
     * @param weekdays an array indicating the active days of the week, eg: {1,1,1,1,0,0,1}
     */
    override fun setPowerOnOffWithWeekly(onTime: IntArray, offTime: IntArray, weekdays: IntArray) {
        if (onTime.size != 2) {
            throw Exception("onTime size must be 2, eg: {8,30}")
        }
        if (offTime.size != 2) {
            throw Exception("offTime size must be 2, eg: {18,30}")
        }
        if (weekdays.size != 7) {
            throw Exception("weekdays size must be 7, eg: {1,1,1,1,0,0,1}")
        }

        this.ui.setPowerOnOffWithWeekly(onTime, offTime, weekdays)
    }
}
