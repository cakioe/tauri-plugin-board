package com.plugin.board.ui

interface Board {
    /**
     * Initializes the board with the provided context.
     *
     * This function must be invoked before any other methods on the board are called.
     * It is responsible for setting up the board and preparing it for use.
     *
     * @param context the context of the application
     */
    fun initialize(context: android.content.Context)

    /**
     * Reboots the device.
     *
     * This function triggers the `reboot` method on the board, causing the device to restart.
     */
    fun reboot()

    /**
     * Shuts down the device.
     *
     * This function triggers the `shutdown` method on the board, causing the device to turn off.
     */
    fun shutdown()

    /**
     * Checks if the power on time is set.
     *
     * This function queries the board to determine if a scheduled
     * power on time has been configured.
     *
     * @return true if the power on time is set, false otherwise
     */
    fun isSetPowerOnTime(): Boolean = false

    /**
     * Clears the power on/off time settings.
     *
     * This function invokes the `clearPowerOnOffTime` method on the
     * board to reset any scheduled power on/off times to their default state.
     */
    fun clearPowerOnOffTime()

    /**
     * Retrieves the serial number of the device.
     *
     * This function accesses the serial number of the device and returns it as a String.
     *
     * @return the serial number of the device as a String
     */
    fun getSerialNo(): String

    /**
     * Captures a screenshot of the device's display.
     *
     * This function captures the current display of the device as a PNG image
     * and saves it to the SD card as a file named with the current timestamp.
     * The name of the file is returned as a String.
     *
     * @return the name of the screenshot file as a String
     */
    fun takeScreenshot(): String

    /**
     * Enables or disables the status bar.
     *
     * This function sets the status bar state. If set to true, the status bar will be shown.
     * If set to false, the status bar will be hidden.
     *
     * @param enable whether to show the status bar or not
     */
    fun setStatusBar(enable: Boolean)

    /**
     * Enables or disables the gesture status bar.
     *
     * This function sets the gesture status bar state. If set to true, the gesture status bar will be shown.
     * If set to false, the gesture status bar will be hidden.
     *
     * @param enable whether to show the gesture status bar or not
     */
    fun setGestureStatusBar(enable: Boolean)

    /**
     * Configures the power on/off schedule.
     *
     * This function sets a scheduled power on/off time for the device using the
     * provided on and off times. The schedule is applied immediately on the device.
     *
     * @param onTime an array of integers representing the time to power on
     * @param offTime an array of integers representing the time to power off
     */
    fun setPowerOnOff(onTime: IntArray, offTime: IntArray)

    /**
     * Configures the power on/off schedule with weekly settings.
     *
     * This function sets a scheduled power on/off time for the device on specific weekdays.
     * The schedule is defined by specifying the on and off times along with the weekdays on which
     * the schedule should be active.
     *
     * @param onTime an array of integers representing the time to power on
     * @param offTime an array of integers representing the time to power off
     * @param weekdays an array indicating the active days of the week (0 for inactive, 1 for active)
     */
    fun setPowerOnOffWithWeekly(onTime: IntArray, offTime: IntArray, weekdays: IntArray)
}

/**
 * Retrieves an instance of the specified [Board].
 *
 * @param model the type of the board to instantiate, e.g. "ZC" or "YS"
 * @return an instance of the board
 * @throws IllegalArgumentException if the boardType is unknown
 */
fun initialize(model: String): Board = when {
    model.startsWith("zc", ignoreCase = true) -> ZC()
    model.startsWith("rk", ignoreCase = true)
            || model.startsWith("ys", ignoreCase = true)
            || model.startsWith("a527", ignoreCase = true) -> YS()

    else -> YS()
}