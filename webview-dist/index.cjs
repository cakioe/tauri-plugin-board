'use strict';

var core = require('@tauri-apps/api/core');

// @ts-ignore
/**
 * @method shutdown
 * @description: shutdown the board
 */
async function shutdown() {
    await core.invoke('plugin:board|shutdown', {});
}
/**
 * @method reboot
 * @description: reboot the board
 */
async function reboot() {
    await core.invoke('plugin:board|reboot', {});
}
/**
 * @method setStatusBar
 * @description: set status bar
 * @param enable
 */
async function setStatusBar(enable) {
    await core.invoke('plugin:board|set_status_bar', { enable });
}
/**
 * @method setGestureStatusBar
 * @description: set gesture status bar
 * @param enable
 */
async function setGestureStatusBar(enable) {
    await core.invoke('plugin:board|set_gesture_status_bar', { enable });
}
/**
 * @method getBuildModel
 * @description: get build model
 */
async function getBuildModel() {
    await core.invoke('plugin:board|get_build_model', {});
}
/**
 * @method getBuildSerial
 * @description: get build serial
 */
async function getBuildSerial() {
    await core.invoke('plugin:board|get_build_serial', {});
}
/**
 * @method setLcdOnOff
 * @description: set lcd on off
 * @param enable
 */
async function setLcdOnOff(enable) {
    await core.invoke('plugin:board|set_lcd_on_off', { enable });
}

exports.getBuildModel = getBuildModel;
exports.getBuildSerial = getBuildSerial;
exports.reboot = reboot;
exports.setGestureStatusBar = setGestureStatusBar;
exports.setLcdOnOff = setLcdOnOff;
exports.setStatusBar = setStatusBar;
exports.shutdown = shutdown;
