package com.plugin.board

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment
import com.ys.rkapi.MyManager
import com.zcapi


@SuppressLint("SdCardPath")
const val SDCARD_DIR = "/sdcard"

// android device driver
interface AndroidDeviceDriver {
    fun initialize(context: Context)
    fun reboot()
    fun shutdown()
    fun isSetPowerOnTime(): Boolean = false
    fun clearPowerOnOffTime()
    fun getSerialNo(): String
    fun takeScreenshot(): String
    fun setStatusBar(enable: Boolean)
    fun setGestureStatusBar(enable: Boolean)
    fun setPowerOnOff(onTime: IntArray, offTime: IntArray)
    fun setPowerOnOffWithWeekly(onTime: IntArray, offTime: IntArray, weekdays: IntArray)
}

class ZC : AndroidDeviceDriver {
    private val driver = zcapi()

    /**
     * Initializes the driver.
     *
     * This function initializes the driver by setting up the display manager
     * and hiding the status and navigation bars.
     *
     * @param context the context of the application
     */
    override fun initialize(context: Context) {
        this.driver.getContext(context)
        this.driver.setStatusBar(false)
        this.driver.setGestureStatusBar(false)
    }

    /**
     * Reboots the device.
     *
     * This function triggers the `reboot` method on the `driver`,
     * causing the device to restart.
     */
    override fun reboot() {
        this.driver.reboot()
    }

    /**
     * Shuts down the device.
     *
     * This function triggers the `shutdown` method on the `driver`,
     * causing the device to turn off.
     */
    override fun shutdown() {
        this.driver.shutDown()
    }

    /**
     * Checks if the power on time is set.
     *
     * This function queries the `driver` to determine if a scheduled
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
     * `driver` to reset any scheduled power on/off times to their default state.
     */
    override fun clearPowerOnOffTime() {
        val onTime = intArrayOf(0, 0, 0, 0, 0, 0)
        val offTime = intArrayOf(0, 0, 0, 0, 0, 0)
        this.driver.setPowetOnOffTime(false, onTime, offTime)
    }

    /**
     * Retrieves the serial number of the device.
     *
     * This function accesses the `buildSerial` property on the `driver` to
     * obtain the serial number of the device.
     *
     * @return the serial number of the device as a String
     */
    override fun getSerialNo(): String {
        return this.driver.buildSerial
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
        this.driver.screenshot(SDCARD_DIR, filename)

        return "${SDCARD_DIR}/${filename}"
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
        this.driver.setStatusBar(false)
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
        this.driver.setGestureStatusBar(enable)
    }

    /**
     * Configures the power on/off schedule.
     *
     * This function sets a scheduled power on/off time for the device. It
     * utilizes the `setPowerOnOff` method on the `driver` to establish
     * when the device should power on and off.
     *
     * @param onTime an array of integers representing the time to power on
     * @param offTime an array of integers representing the time to power off
     */
    override fun setPowerOnOff(onTime: IntArray, offTime: IntArray) {
        this.driver.setPowetOnOffTime(true, onTime, offTime)
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

class YS : AndroidDeviceDriver {
    private lateinit var driver: MyManager

    /**
     * Initializes the driver.
     *
     * This function initializes the driver by setting up the display manager
     * and hiding the status and navigation bars.
     *
     * @param context the context of the application
     */
    override fun initialize(context: Context) {
        this.driver = MyManager.getInstance(context).apply {
            setSlideShowNavBar(false) // true => open, false => close
            hideStatusBar(true) // ture => hide, false => show
            hideNavBar(true) // ture => hide, false => show
        }
    }

    /**
     * Reboots the device.
     *
     * 重新启动设备
     *
     * This function triggers the `reboot` method on the `driver`,
     * causing the device to restart.
     */
    override fun reboot() {
        this.driver.reboot()
    }


    /**
     * Shuts down the device.
     *
     * 关闭设备
     *
     * This function triggers the `shutdown` method on the `driver`,
     * causing the device to turn off.
     */
    override fun shutdown() {
        this.driver.shutdown()
    }

    /**
     * Checks if the power on time is set.
     *
     * 检查是否设置了定时开机时间
     *
     * This function queries the `driver` to determine if a scheduled
     * power on time has been configured.
     *
     * @return true if the power on time is set, false otherwise
     */
    override fun isSetPowerOnTime(): Boolean {
        return this.driver.isSetPowerOnTime
    }

    /**
     * Clears the power on/off time settings.
     *
     * 清除定时开关机数据
     *
     * This function invokes the `clearPowerOnOffTime` method on the
     * `driver` to reset any scheduled power on/off times to their default state.
     */
    override fun clearPowerOnOffTime() {
        this.driver.clearPowerOnOffTime()
    }

    /**
     * Retrieves the serial number of the device.
     *
     * @return the serial number of the device as a String
     */
    override fun getSerialNo(): String {
        return this.driver.androidModle
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
        this.driver.takeScreenshot(filepath)

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
        this.driver.hideStatusBar(enable)
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
        this.driver.setSlideShowNavBar(enable)
        this.driver.hideNavBar(!enable)
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
        this.driver.setPowerOnOff(onTime, offTime)
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

        this.driver.setPowerOnOffWithWeekly(onTime, offTime, weekdays)
    }
}

/**
 * Gets an instance of the specified [AndroidDeviceDriver].
 *
 * @param model the type of the driver to instantiate, e.g. "ZC" or "YS"
 * @return an instance of the driver
 * @throws IllegalArgumentException if the driverType is unknown
 * @since 1.8.0
 */
fun getAndroidDeviceDriver(model: String): AndroidDeviceDriver {
    return when {
        model.startsWith("zc", ignoreCase = true) -> ZC()
        listOf("rk", "ys", "a527").any { model.startsWith(it, ignoreCase = true) } -> YS()
        else -> YS()
    }
}