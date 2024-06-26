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
    await core.invoke('plugin:board|setStatusBar', { enable });
}
/**
 * @method setGestureStatusBar
 * @description: set gesture status bar
 * @param enable
 */
async function setGestureStatusBar(enable) {
    await core.invoke('plugin:board|setGestureStatusBar', { enable });
}
/**
 * @method getBuildModel
 * @description: get build model
 */
async function getBuildModel() {
    await core.invoke('plugin:board|getBuildModel', {});
}
/**
 * @method getBuildSerial
 * @description: get build serial
 */
async function getBuildSerial() {
    await core.invoke('plugin:board|getBuildSerial', {});
}
/**
 * @method setLcdOnOff
 * @description: set lcd on off
 * @param enable
 */
async function setLcdOnOff(enable) {
    await core.invoke('plugin:board|setLcdOnOff', { enable });
}

exports.getBuildModel = getBuildModel;
exports.getBuildSerial = getBuildSerial;
exports.reboot = reboot;
exports.setGestureStatusBar = setGestureStatusBar;
exports.setLcdOnOff = setLcdOnOff;
exports.setStatusBar = setStatusBar;
exports.shutdown = shutdown;
