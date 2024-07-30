import { invoke } from '@tauri-apps/api/core';

// @ts-ignore
/**
 * @method shutdown
 * @description: shutdown the board
 */
async function shutdown() {
    await invoke('plugin:board|shutdown');
}
/**
 * @method reboot
 * @description: reboot the board
 */
async function reboot() {
    await invoke('plugin:board|reboot');
}
/**
 * @method setStatusBar
 * @description: set status bar
 * @param enable
 */
async function setStatusBar(enable) {
    await invoke('plugin:board|set_status_bar', { enable });
}
/**
 * @method setGestureStatusBar
 * @description: set gesture status bar
 * @param enable
 */
async function setGestureStatusBar(enable) {
    await invoke('plugin:board|set_gesture_status_bar', { enable });
}
/**
 * @method getBuildModel
 * @description: get build model
 */
async function getBuildModel() {
    await invoke('plugin:board|get_build_model');
}
/**
 * @method getBuildSerial
 * @description: get build serial
 */
async function getBuildSerial() {
    await invoke('plugin:board|get_build_serial');
}
/**
 * @method setLcdOnOff
 * @description: set lcd on off
 * @param enable
 */
async function setLcdOnOff(enable) {
    await invoke('plugin:board|set_lcd_on_off', { enable });
}

export { getBuildModel, getBuildSerial, reboot, setGestureStatusBar, setLcdOnOff, setStatusBar, shutdown };
