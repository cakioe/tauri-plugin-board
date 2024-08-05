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
async function setStatusBar(options) {
    await core.invoke('plugin:board|set_status_bar', { ...options });
}
async function setGestureStatusBar(options) {
    await core.invoke('plugin:board|set_gesture_status_bar', { ...options });
}
async function getBuildModel() {
    return await core.invoke('plugin:board|get_build_model').then(r => r.value);
}
/**
 * @example
 * ```typescript
 * import { getBuildSerial } from '@cakioe/tauri-plugin-board';
 * const no = await getBuildSerial();
 * if (no) {
 *    // use the no string here
 * }
 * ```
 *
 * @since 1.2.0
 */
async function getBuildSerial() {
    return await core.invoke('plugin:board|get_build_serial').then(r => r.value);
}
async function setLcdOnOff(options) {
    await core.invoke('plugin:board|set_lcd_on_off', { ...options });
}
/**
 * @example
 * ```typescript
 * import { ping } from '@cakioe/tauri-plugin-board';
 * const pong = await ping();
 * if (pong) {
 *    // use the pong string here
 * }
 * ```
 *
 * @since 1.2.0
 */
async function ping() {
    return await core.invoke('plugin:board|ping').then(r => r.value);
}
async function setPowerOnOffTime(options) {
    await core.invoke('plugin:board|set_power_on_off_time', { ...options });
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
async function openSettingConfig() {
    await core.invoke('plugin:board|open_setting_config');
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
async function openFileManager() {
    await core.invoke('plugin:board|open_file_manager');
}

exports.getBuildModel = getBuildModel;
exports.getBuildSerial = getBuildSerial;
exports.openFileManager = openFileManager;
exports.openSettingConfig = openSettingConfig;
exports.ping = ping;
exports.reboot = reboot;
exports.setGestureStatusBar = setGestureStatusBar;
exports.setLcdOnOff = setLcdOnOff;
exports.setPowerOnOffTime = setPowerOnOffTime;
exports.setStatusBar = setStatusBar;
exports.shutdown = shutdown;
