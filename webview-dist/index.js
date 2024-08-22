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
async function setLcdOnOff(options) {
    await invoke('plugin:board|set_lcd_on_off', { ...options });
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
async function getSerialDevicesPath() {
    return await invoke('plugin:board|get_serial_devices_path').then(r => JSON.parse(r.value));
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

export { getBuildEnv, getSerialDevicesPath, openFileManager, openMainActivity, openSettingConfig, reboot, setAppBrightness, setGestureStatusBar, setLcdOnOff, setPowerOnOffTime, setStatusBar, shutdown, takeScreenShot };
