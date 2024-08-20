import { invoke } from '@tauri-apps/api/core';

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
    await invoke('plugin:board|shutdown');
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
    await invoke('plugin:board|reboot');
}
async function setStatusBar(options) {
    await invoke('plugin:board|set_status_bar', { ...options });
}
async function setGestureStatusBar(options) {
    await invoke('plugin:board|set_gesture_status_bar', { ...options });
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
 * @deprecated 1.5.0, use `getBuildEnv` instead
 */
async function getBuildModel() {
    return await invoke('plugin:board|get_build_model').then(r => r.value);
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
 * @deprecated 1.5.0, use `getBuildEnv` instead
 */
async function getBuildSerial() {
    return await invoke('plugin:board|get_build_serial').then(r => r.value);
}
async function setLcdOnOff(options) {
    await invoke('plugin:board|set_lcd_on_off', { ...options });
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
    return await invoke('plugin:board|ping').then(r => r.value);
}
async function setPowerOnOffTime(options) {
    return await invoke('plugin:board|set_power_on_off_time', { ...options }).then(r => r.value);
}
async function openSettingConfig(options) {
    await invoke('plugin:board|open_setting_config', { ...options });
}
async function openFileManager(options) {
    await invoke('plugin:board|open_file_manager', { ...options });
}
async function setAppBrightness(options) {
    return await invoke('plugin:board|set_app_brightness', { ...options }).then(r => r.value);
}
/**
 * @example
 * ```typescript
 * import { getSerialPaths } from '@cakioe/tauri-plugin-board';
 * await getSerialPaths();
 * ```
 *
 * @since 1.4.0-beta.1
 * @deprecated 1.5.0
 */
async function getSerialPaths() {
    return await invoke('plugin:board|get_serial_devices_path').then(r => JSON.parse(r.value));
}
async function getSerialDevicesPath() {
    return await invoke('plugin:board|get_serial_devices_path').then(r => JSON.parse(r.value));
}
/**
 * @example
 * ```typescript
 * import { getAllDevicesPath } from '@cakioe/tauri-plugin-board';
 * await getAllDevicesPath();
 * ```
 *
 * @since 1.4.0-beta.2
 */
async function getAllDevicesPath() {
    return await invoke('plugin:board|get_all_devices_path').then(r => JSON.parse(r.value));
}
async function setSerialsPathIndex(options) {
    return await invoke('plugin:board|set_serials_path_index', { ...options }).then(r => r.value);
}
async function getBuildEnv() {
    return await invoke('plugin:board|get_build_env').then(r => JSON.parse(r.value));
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
    await invoke('plugin:board|open_main_activity');
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
    return await invoke('plugin:board|take_screen_shot').then(r => r.value);
}

export { getAllDevicesPath, getBuildEnv, getBuildModel, getBuildSerial, getSerialDevicesPath, getSerialPaths, openFileManager, openMainActivity, openSettingConfig, ping, reboot, setAppBrightness, setGestureStatusBar, setLcdOnOff, setPowerOnOffTime, setSerialsPathIndex, setStatusBar, shutdown, takeScreenShot };
