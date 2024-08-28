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
 * import { getYPos } from '@cakioe/tauri-plugin-board';
 * await getYPos({ ...options });
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
/**
 * @example
 * ```typescript
 * import { getXStatus } from '@cakioe/tauri-plugin-board';
 * await getXStatus({ ...options });
 * ```
 *
 * @since 1.6.0
 */
async function getXStatus(options) {
    return await invoke('plugin:board|get_x_status', { ...options }).then(r => JSON.parse(r.value));
}
/**
 * @example
 * ```typescript
 * import { resetLift } from '@cakioe/tauri-plugin-board';
 * await resetLift({ ...options });
 * ```
 *
 * @since 1.6.0
 */
async function resetLift(options) {
    return await invoke('plugin:board|reset_lift', { ...options }).then(r => r.value);
}
/**
 * @example
 * ```typescript
 * import { runMoto } from '@cakioe/tauri-plugin-board';
 * await runMoto({ ...options });
 * ```
 *
 * @since 1.6.0
 */
async function runMoto(options) {
    await invoke('plugin:board|run_moto', { ...options });
}
/**
 * @example
 * ```typescript
 * import { getShipmentStatus } from '@cakioe/tauri-plugin-board';
 * await getShipmentStatus({ ...options });
 * ```
 *
 * @since 1.6.0
 */
async function getShipmentStatus(options) {
    return await invoke('plugin:board|get_shipment_status', { ...options }).then(r => JSON.parse(r.value));
}
/**
 * @example
 * ```typescript
 * import { setXPos } from '@cakioe/tauri-plugin-board';
 * await setXPos({ ...options });
 * ```
 *
 * @since 1.6.0
 */
async function setXPos(options) {
    if ((options === null || options === void 0 ? void 0 : options.values.length) !== 10) {
        throw new Error('x length must be 10');
    }
    return await invoke('plugin:board|set_x_pos', { ...options }).then(r => r.value);
}
/**
 * @example
 * ```typescript
 * import { setYPos } from '@cakioe/tauri-plugin-board';
 * await setYPos({ ...options });
 * ```
 *
 * @since 1.6.0
 */
async function setYPos(options) {
    if ((options === null || options === void 0 ? void 0 : options.values.length) !== 10) {
        throw new Error('y length must be 10');
    }
    return await invoke('plugin:board|set_y_pos', { ...options }).then(r => r.value);
}
/**
 * @example
 * ```typescript
 * import { toX } from '@cakioe/tauri-plugin-board';
 * await toX({ ...options });
 * ```
 *
 * @since 1.6.0
 */
async function toX(options) {
    return await invoke('plugin:board|to_x', { ...options }).then(r => r.value);
}
/**
 * @example
 * ```typescript
 * import { toX } from '@cakioe/tauri-plugin-board';
 * await toX({ ...options });
 * ```
 *
 * @since 1.6.0
 */
async function toY(options) {
    return await invoke('plugin:board|to_y', { ...options }).then(r => r.value);
}
/**
 * @example
 * ```typescript
 * import { readHardwareConfig } from '@cakioe/tauri-plugin-board';
 * await readHardwareConfig();
 * ```
 *
 * @since 1.6.1
 * @returns {HardwareConfig}
 */
async function readHardwareConfig() {
    return await invoke('plugin:board|read_hardware_config').then(r => JSON.parse(r.value));
}
/**
 * @example
 * ```typescript
 * import { getSoftwareVersion } from '@cakioe/tauri-plugin-board';
 * await getSoftwareVersion();
 * ```
 *
 * @since 1.6.1
 * @returns {string}
 */
async function getSoftwareVersion() {
    return await invoke('plugin:board|get_software_version').then(r => r.value);
}
/**
 * @example
 * ```typescript
 * import { getMinPayoutAmount } from '@cakioe/tauri-plugin-board';
 * await getMinPayoutAmount();
 * ```
 *
 * @since 1.6.1
 * @returns {MinPayoutAmount}
 */
async function getMinPayoutAmount() {
    return await invoke('plugin:board|get_min_payout_amount').then(r => JSON.parse(r.value));
}
/**
 * @example
 * ```typescript
 * import { getPayAmount } from '@cakioe/tauri-plugin-board';
 * await getPayAmount();
 * ```
 *
 * @since 1.6.1
 * @returns {PayAmount}
 */
async function getPayAmount() {
    return await invoke('plugin:board|get_pay_amount').then(r => JSON.parse(r.value));
}
/**
 * @example
 * ```typescript
 * import { initPayment } from '@cakioe/tauri-plugin-board';
 * await initPayment();
 * ```
 *
 * @since 1.6.1
 * @returns {string}
 */
async function initPayment(options) {
    return await invoke('plugin:board|init_payment', { ...options }).then(r => r.value);
}
/**
 * @example
 * ```typescript
 * import { notifyPayment } from '@cakioe/tauri-plugin-board';
 * await notifyPayment();
 * ```
 *
 * @since 1.6.1
 * @param options {flag: boolean}
 * @returns {string}
 */
async function notifyPayment(options) {
    return await invoke('plugin:board|notify_payment', { ...options }).then(r => r.value);
}
/**
 * @example
 * ```typescript
 * import { notifyResult } from '@cakioe/tauri-plugin-board';
 * await notifyResult();
 * ```
 *
 * @since 1.6.1
 * @param options {flag: boolean}
 * @returns {string}
 */
async function notifyResult(options) {
    return await invoke('plugin:board|notify_result', { ...options }).then(r => r.value);
}
/**
 * @example
 * ```typescript
 * import { changeBalance } from '@cakioe/tauri-plugin-board';
 * await changeBalance();
 * ```
 *
 * @since 1.6.1
 * @param options {multiple: number}
 * @returns {string}
 */
async function changeBalance(options) {
    return await invoke('plugin:board|change_balance', { ...options }).then(r => r.value);
}
/**
 * @example
 * ```typescript
 * import { getChangeStatus } from '@cakioe/tauri-plugin-board';
 * await getChangeStatus();
 * ```
 *
 * @since 1.6.1
 * @returns {ChangeStatus}
 */
async function getChangeStatus() {
    return await invoke('plugin:board|get_change_status').then(r => JSON.parse(r.value));
}
/**
 * @example
 * ```typescript
 * import { findChangeResult } from '@cakioe/tauri-plugin-board';
 * await findChangeResult();
 * ```
 *
 * @since 1.6.1
 * @returns {string}
 */
async function findChangeResult() {
    return await invoke('plugin:board|find_change_result').then(r => JSON.parse(r.value));
}
/**
 * @example
 * ```typescript
 * import { setAcceptMoney } from '@cakioe/tauri-plugin-board';
 * await setAcceptMoney();
 * ```
 *
 * @since 1.6.1
 * @param options {type: number; channels: number[]}
 * @returns {string}
 */
async function setAcceptMoney(options) {
    if ((options === null || options === void 0 ? void 0 : options.type) !== 0 && (options === null || options === void 0 ? void 0 : options.type) !== 1) {
        throw new Error('type must be 0 or 1');
    }
    if ((options === null || options === void 0 ? void 0 : options.channels.length) !== 16) {
        throw new Error('channels length must be 16');
    }
    return await invoke('plugin:board|set_accept_money', { ...options }).then(r => r.value);
}
/**
 * @example
 * ```typescript
 * import { syncSystemTime } from '@cakioe/tauri-plugin-board';
 * await syncSystemTime();
 * ```
 *
 * @since 1.6.1
 * @returns {string}
 */
async function syncSystemTime() {
    return await invoke('plugin:board|sync_system_time').then(r => r.value);
}
/**
 * @example
 * ```typescript
 * import { setAgeScope } from '@cakioe/tauri-plugin-board';
 * await setAgeScope();
 * ```
 *
 * @since 1.6.1
 * @param options {age: number}
 * @returns {string}
 */
async function setAgeScope(options) {
    return await invoke('plugin:board|set_age_scope', { ...options }).then(r => r.value);
}
/**
 * @example
 * ```typescript
 * import { getAuthResult } from '@cakioe/tauri-plugin-board';
 * await getAuthResult();
 * ```
 *
 * @since 1.6.1
 * @returns {number}
 */
async function getAuthResult() {
    return await invoke('plugin:board|get_auth_result').then(r => parseInt(r.value));
}
/**
 * @example
 * ```typescript
 * import { setWorkMode } from '@cakioe/tauri-plugin-board';
 * await setWorkMode();
 * ```
 *
 * @since 1.6.1
 * @param options {mode: number}
 * @returns {string}
 */
async function setWorkMode(options) {
    if ((options === null || options === void 0 ? void 0 : options.mode) !== 0 && (options === null || options === void 0 ? void 0 : options.mode) !== 1) {
        throw new Error('mode must be 0 or 1');
    }
    return await invoke('plugin:board|set_work_mode', { ...options }).then(r => r.value);
}
/**
 * @example
 * ```typescript
 * import { setPayChannel } from '@cakioe/tauri-plugin-board';
 * await setPayChannel();
 * ```
 *
 * @since 1.6.1
 * @param options {mode: number}
 * @returns {string}
 */
async function setPayChannel(options) {
    if ((options === null || options === void 0 ? void 0 : options.mode) !== 0 && (options === null || options === void 0 ? void 0 : options.mode) !== 1 && (options === null || options === void 0 ? void 0 : options.mode) !== 2) {
        throw new Error('mode must be 0 or 1 or 2');
    }
    return await invoke('plugin:board|set_pay_channel', { ...options }).then(r => r.value);
}
/**
 * @example
 * ```typescript
 * import { pulseBalance } from '@cakioe/tauri-plugin-board';
 * await pulseBalance();
 * ```
 *
 * @since 1.6.1
 * @param options {type: number; value: number}
 * @returns {string}
 */
async function pulseBalance(options) {
    return await invoke('plugin:board|pulse_balance', { ...options }).then(r => r.value);
}
/**
 * @example
 * ```typescript
 * import { motoTimeout } from '@cakioe/tauri-plugin-board';
 * await motoTimeout();
 * ```
 *
 * @since 1.6.1
 * @param options {addr: number; time: number}
 * @returns {string}
 */
async function motoTimeout(options) {
    return await invoke('plugin:board|moto_timeout', { ...options }).then(r => r.value);
}
/**
 * @example
 * ```typescript
 * import { setPickY } from '@cakioe/tauri-plugin-board';
 * await setPickY();
 * ```
 *
 * @since 1.6.1
 * @param options {addr: number; pos: number; mode: number}
 * @returns {string}
 */
async function setPickXY(options) {
    if ((options === null || options === void 0 ? void 0 : options.mode) !== 0 && (options === null || options === void 0 ? void 0 : options.mode) !== 1) {
        throw new Error('mode must be 0 or 1');
    }
    return await invoke('plugin:board|set_pick_xy', { ...options }).then(r => r.value);
}

export { changeBalance, execShipment, findChangeResult, getAuthResult, getBoxStatus, getBuildBoard, getBuildEnv, getChangeStatus, getDropStatus, getMinPayoutAmount, getPayAmount, getSerialDevicesPath, getShipmentStatus, getSoftwareVersion, getXPos, getXStatus, getYPos, getYStatus, initPayment, motoTimeout, notifyPayment, notifyResult, openFileManager, openMainActivity, openSettingConfig, pulseBalance, readHardwareConfig, reboot, resetLift, runMoto, setAcceptMoney, setAgeScope, setAppBrightness, setGestureStatusBar, setLcdOnOff, setPayChannel, setPickXY, setPowerOnOffTime, setStatusBar, setWorkMode, setXPos, setYPos, shutdown, syncSystemTime, takeScreenShot, toX, toY };
