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
    await invoke('plugin:board|set_status_bar', { ...options });
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
    await invoke('plugin:board|set_gesture_status_bar', { ...options });
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
    await invoke('plugin:board|set_lcd_on_off', { ...options });
}
async function setPowerOnOffTime(options) {
    return await invoke('plugin:board|set_power_on_off_time', { ...options }).then(r => r.value);
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
    await invoke('plugin:board|open_setting_config', { ...options });
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
    await invoke('plugin:board|open_file_manager', { ...options });
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
    return await invoke('plugin:board|set_app_brightness', { ...options }).then(r => r.value);
}
async function getSerialDevicesPath() {
    return await invoke('plugin:board|get_serial_devices_path').then(r => JSON.parse(r.value));
}
async function getBuildEnv(options) {
    return await invoke('plugin:board|get_build_env', { ...options }).then(r => JSON.parse(r.value));
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
async function getBuildBoard(options) {
    return await invoke('plugin:board|get_build_board', { ...options }).then(r => JSON.parse(r.value));
}
/**
 * @example
 * ```typescript
 * import { execShipment } from '@cakioe/tauri-plugin-board';
 * await execShipment({...options});
 * ```
 *
 * @since 1.5.4
 * @update 1.6.0 `no` replace `motorId` field of options
 */
async function execShipment(options) {
    return await invoke('plugin:board|exec_shipment', { ...options }).then(r => r.value);
}
async function getBoxStatus(options) {
    return await invoke('plugin:board|get_box_status', { ...options }).then(r => JSON.parse(r.value));
}
/**
 * @example
 * ```typescript
 * import { getXPos } from '@cakioe/tauri-plugin-board';
 * await getXPos({ ...options });
 * ```
 *
 * @since 1.6.0
 */
async function getYPos(options) {
    return await invoke('plugin:board|get_y_pos', { ...options }).then(r => parseInt(r.value));
}
/**
 * @example
 * ```typescript
 * import { getXPos } from '@cakioe/tauri-plugin-board';
 * await getXPos({ ...options });
 * ```
 *
 * @since 1.6.0
 */
async function getXPos(options) {
    return await invoke('plugin:board|get_x_pos', { ...options }).then(r => parseInt(r.value));
}
/**
 * @example
 * ```typescript
 * import { getDropStatus } from '@cakioe/tauri-plugin-board';
 * await getDropStatus({ ...options });
 * ```
 *
 * @since 1.6.0
 */
async function getDropStatus(options) {
    return await invoke('plugin:board|get_drop_status', { ...options }).then(r => parseInt(r.value));
}
async function getYStatus(options) {
    return await invoke('plugin:board|get_y_status', { ...options }).then(r => JSON.parse(r.value));
}

export { execShipment, getBoxStatus, getBuildBoard, getBuildEnv, getDropStatus, getSerialDevicesPath, getXPos, getYPos, getYStatus, openFileManager, openMainActivity, openSettingConfig, reboot, setAppBrightness, setGestureStatusBar, setLcdOnOff, setPowerOnOffTime, setStatusBar, shutdown, takeScreenShot };
