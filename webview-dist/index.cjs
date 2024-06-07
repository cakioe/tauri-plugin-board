'use strict';

var tauri = require('@tauri-apps/api/tauri');

// @ts-ignore
/**
 * @method shutdown
 * @description: shutdown the board
 */
async function shutdown() {
    await tauri.invoke('plugin:board|shutdown', {});
}
/**
 * @method reboot
 * @description: reboot the board
 */
async function reboot() {
    await tauri.invoke('plugin:board|reboot', {});
}
/**
 * @method setStatusBar
 * @description: set status bar
 * @param enable
 */
async function setStatusBar(enable) {
    await tauri.invoke('plugin:board|setStatusBar', { enable });
}
/**
 * @method setGestureStatusBar
 * @description: set gesture status bar
 * @param enable
 */
async function setGestureStatusBar(enable) {
    await tauri.invoke('plugin:board|setGestureStatusBar', { enable });
}
/**
 * @method getBuildModel
 * @description: get build model
 */
async function getBuildModel() {
    await tauri.invoke('plugin:board|getBuildModel', {});
}
/**
 * @method getBuildSerial
 * @description: get build serial
 */
async function getBuildSerial() {
    await tauri.invoke('plugin:board|getBuildSerial', {});
}
/**
 * @method setLcdOnOff
 * @description: set lcd on off
 * @param enable
 */
async function setLcdOnOff(enable) {
    await tauri.invoke('plugin:board|setLcdOnOff', { enable });
}

exports.getBuildModel = getBuildModel;
exports.getBuildSerial = getBuildSerial;
exports.reboot = reboot;
exports.setGestureStatusBar = setGestureStatusBar;
exports.setLcdOnOff = setLcdOnOff;
exports.setStatusBar = setStatusBar;
exports.shutdown = shutdown;
