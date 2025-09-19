package com.plugin.board.ui

import android.content.Context
import android.os.Environment
import com.zcapi


class ZC : Board {
    private val ui = zcapi()
    private val SDCARD_DIR = Environment.getExternalStorageDirectory().path

    /**
     * Initializes the ui.
     *
     * This function initializes the ui by setting up the display manager
     * and hiding the status and navigation bars.
     *
     * @param context the context of the application
     */
    override fun initialize(context: Context) {
        this.ui.getContext(context)
        this.ui.setStatusBar(false)
        this.ui.setGestureStatusBar(false)
    }

    /**
     * Reboots the device.
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
     * This function triggers the `shutdown` method on the `ui`,
     * causing the device to turn off.
     */
    override fun shutdown() {
        this.ui.shutDown()
    }

    /**
     * Checks if the power on time is set.
     *
     * This function queries the `ui` to determine if a scheduled
     * power on time has been configured.
     *
     * @return true if the power on time is set, false otherwise
     */
    override fun isSetPowerOnTime(): Boolean {
        return false
    }

    /**
     * Clears the power on/off time settings.
     *
     * This function invokes the `clearPowerOnOffTime` method on the
     * `ui` to reset any scheduled power on/off times to their default state.
     */
    override fun clearPowerOnOffTime() {
        val onTime = intArrayOf(0, 0, 0, 0, 0, 0)
        val offTime = intArrayOf(0, 0, 0, 0, 0, 0)
        this.ui.setPowetOnOffTime(false, onTime, offTime)
    }

    /**
     * Retrieves the serial number of the device.
     *
     * This function accesses the `buildSerial` property on the `ui` to
     * obtain the serial number of the device.
     *
     * @return the serial number of the device as a String
     */
    override fun getSerialNo(): String {
        return this.ui.buildSerial
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
        this.ui.screenshot(this.SDCARD_DIR, filename)

        return "${this.SDCARD_DIR}/${filename}"
    }

    /**
     * Sets the status bar state.
     *
     * This function sets the status bar to be either visible or hidden.
     * The status bar is the area at the top of the screen that displays
     * information about the device, such as its battery level, signal strength,
     * and any active notifications.
     *
     * @param enable true to show the status bar, false to hide it
     */
    override fun setStatusBar(enable: Boolean) {
        this.ui.setStatusBar(false)
    }

    /**
     * Sets the gesture status bar state.
     *
     * This function sets the gesture status bar to be either visible or hidden.
     * The gesture status bar is the area at the top of the screen that displays
     * information about the device, such as its battery level, signal strength,
     * and any active notifications.
     *
     * @param enable true to show the gesture status bar, false to hide it
     */
    override fun setGestureStatusBar(enable: Boolean) {
        this.ui.setGestureStatusBar(enable)
    }

    /**
     * Configures the power on/off schedule.
     *
     * This function sets a scheduled power on/off time for the device. It
     * utilizes the `setPowerOnOff` method on the `ui` to establish
     * when the device should power on and off.
     *
     * @param onTime an array of integers representing the time to power on
     * @param offTime an array of integers representing the time to power off
     */
    override fun setPowerOnOff(onTime: IntArray, offTime: IntArray) {
        this.ui.setPowetOnOffTime(true, onTime, offTime)
    }

    /**
     * Configures the power on/off schedule with weekly settings.
     *
     * This function attempts to set a scheduled power on/off time for the
     * device on specific weekdays. The schedule is defined by specifying
     * the on and off times along with the weekdays on which the schedule
     * should be active.
     *
     * @param onTime an array of integers representing the time to power on
     * @param offTime an array of integers representing the time to power off
     * @param weekdays an array indicating the active days of the week (0 for inactive, 1 for active)
     * @throws Exception if the functionality is not supported
     */
    override fun setPowerOnOffWithWeekly(
        onTime: IntArray, offTime: IntArray, weekdays: IntArray
    ) {
        throw Exception("not support")
    }
}
