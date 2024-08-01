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
async function setStatusBar(enable) {
    await core.invoke('plugin:board|set_status_bar', { enable });
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
async function setGestureStatusBar(enable) {
    await core.invoke('plugin:board|set_gesture_status_bar', { enable });
}
/**
 * @example
 * ```typescript
 * import { getBuildModel } from '@cakioe/tauri-plugin-board';
 * const no = await getBuildModel();
 * if (no) {
 *    // use the no string here
 * }
 * ```
 *
 * @since 1.2.0
 */
async function getBuildModel() {
    return await core.invoke('plugin:board|get_build_model');
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
    return await core.invoke('plugin:board|get_build_serial');
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
async function setLcdOnOff(enable) {
    await core.invoke('plugin:board|set_lcd_on_off', { enable });
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
    return await core.invoke('plugin:board|ping');
}

exports.getBuildModel = getBuildModel;
exports.getBuildSerial = getBuildSerial;
exports.ping = ping;
exports.reboot = reboot;
exports.setGestureStatusBar = setGestureStatusBar;
exports.setLcdOnOff = setLcdOnOff;
exports.setStatusBar = setStatusBar;
exports.shutdown = shutdown;
