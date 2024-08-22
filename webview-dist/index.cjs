'use strict';

var core = require('@tauri-apps/api/core');

// @ts-ignore
/**
 * @example
 * ```typescript
 * import { shutdown } from '@cakioe/tauri-plugin-board';
 * await shutdown();
 * ```
 *
 * @since 1.2.0
 */
async function shutdown() {
    await core.invoke('plugin:board|shutdown');
}
/**
 * @example
 * ```typescript
 * import { reboot } from '@cakioe/tauri-plugin-board';
 * await reboot();
 * ```
 *
 * @since 1.2.0
 */
async function reboot() {
    await core.invoke('plugin:board|reboot');
}
/**
 * @example
 * ```typescript
 * import { setStatusBar } from '@cakioe/tauri-plugin-board';
 * await setStatusBar(enable);
 * ```
 *
 * @since 1.2.0
 */
async function setStatusBar(options) {
    await core.invoke('plugin:board|set_status_bar', { ...options });
}
/**
 * @example
 * ```typescript
 * import { setGestureStatusBar } from '@cakioe/tauri-plugin-board';
 * await setGestureStatusBar(enable);
 * ```
 *
 * @since 1.2.0
 */
async function setGestureStatusBar(options) {
    await core.invoke('plugin:board|set_gesture_status_bar', { ...options });
}
/**
 * @example
 * ```typescript
 * import { setLcdOnOff } from '@cakioe/tauri-plugin-board';
 * await setLcdOnOff(enable);
 * ```
 *
 * @since 1.2.0
 */
async function setLcdOnOff(options) {
    await core.invoke('plugin:board|set_lcd_on_off', { ...options });
}
async function setPowerOnOffTime(options) {
    return await core.invoke('plugin:board|set_power_on_off_time', { ...options }).then(r => r.value);
}
/**
 * @example
 * ```typescript
 * import { openSettingConfig } from '@cakioe/tauri-plugin-board';
 * await openSettingConfig();
 * ```
 *
 * @since 1.2.6
 */
async function openSettingConfig(options) {
    await core.invoke('plugin:board|open_setting_config', { ...options });
}
/**
 * @example
 * ```typescript
 * import { openFileManager } from '@cakioe/tauri-plugin-board';
 * await openFileManager();
 * ```
 *
 * @since 1.2.6
 */
async function openFileManager(options) {
    await core.invoke('plugin:board|open_file_manager', { ...options });
}
/**
 * @example
 * ```typescript
 * import { setAppBrightness } from '@cakioe/tauri-plugin-board';
 * await setAppBrightness({value: 50});
 * ```
 *
 * @since 1.3.2
 */
async function setAppBrightness(options) {
    return await core.invoke('plugin:board|set_app_brightness', { ...options }).then(r => r.value);
}
async function getSerialDevicesPath() {
    return await core.invoke('plugin:board|get_serial_devices_path').then(r => JSON.parse(r.value));
}
async function getBuildEnv() {
    return await core.invoke('plugin:board|get_build_env').then(r => JSON.parse(r.value));
}
/**
 * @example
 * ```typescript
 * import { openMainActivity } from '@cakioe/tauri-plugin-board';
 * await openMainActivity();
 * ```
 *
 * @since 1.4.0-beta.14
 */
async function openMainActivity() {
    await core.invoke('plugin:board|open_main_activity');
}
/**
 * @example
 * ```typescript
 * import { takeScreenShot } from '@cakioe/tauri-plugin-board';
 * await takeScreenShot();
 * ```
 *
 * @since 1.4.0-beta.19
 */
async function takeScreenShot() {
    return await core.invoke('plugin:board|take_screen_shot').then(r => r.value);
}
async function getBuildBoard(options) {
    return await core.invoke('plugin:board|get_build_board', { ...options }).then(r => JSON.parse(r.value));
}
/**
 * @example
 * ```typescript
 * import { execShipment } from '@cakioe/tauri-plugin-board';
 * await execShipment({...options});
 * ```
 *
 * @since 1.5.4
 */
async function execShipment(options) {
    return await core.invoke('plugin:board|exec_shipment', { ...options }).then(r => r.value);
}

exports.execShipment = execShipment;
exports.getBuildBoard = getBuildBoard;
exports.getBuildEnv = getBuildEnv;
exports.getSerialDevicesPath = getSerialDevicesPath;
exports.openFileManager = openFileManager;
exports.openMainActivity = openMainActivity;
exports.openSettingConfig = openSettingConfig;
exports.reboot = reboot;
exports.setAppBrightness = setAppBrightness;
exports.setGestureStatusBar = setGestureStatusBar;
exports.setLcdOnOff = setLcdOnOff;
exports.setPowerOnOffTime = setPowerOnOffTime;
exports.setStatusBar = setStatusBar;
exports.shutdown = shutdown;
exports.takeScreenShot = takeScreenShot;
