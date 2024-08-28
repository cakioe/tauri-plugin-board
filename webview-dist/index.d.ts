/**
 * @example
 * ```typescript
 * import { shutdown } from '@cakioe/tauri-plugin-board';
 * await shutdown();
 * ```
 *
 * @since 1.2.0
 */
export declare function shutdown(): Promise<void>;
/**
 * @example
 * ```typescript
 * import { reboot } from '@cakioe/tauri-plugin-board';
 * await reboot();
 * ```
 *
 * @since 1.2.0
 */
export declare function reboot(): Promise<void>;
/**
 * @example
 * ```typescript
 * import { setStatusBar } from '@cakioe/tauri-plugin-board';
 * await setStatusBar(enable);
 * ```
 *
 * @since 1.2.0
 */
export declare function setStatusBar(options?: {
    enable: boolean;
}): Promise<void>;
/**
 * @example
 * ```typescript
 * import { setGestureStatusBar } from '@cakioe/tauri-plugin-board';
 * await setGestureStatusBar(enable);
 * ```
 *
 * @since 1.2.0
 */
export declare function setGestureStatusBar(options?: {
    enable: boolean;
}): Promise<void>;
/**
 * @example
 * ```typescript
 * import { setLcdOnOff } from '@cakioe/tauri-plugin-board';
 * await setLcdOnOff(enable);
 * ```
 *
 * @since 1.2.0
 */
export declare function setLcdOnOff(options?: {
    enable?: boolean;
}): Promise<void>;
/**
 * @example
 * ```typescript
 * import { setPowerOnOffTime } from '@cakioe/tauri-plugin-board';
 * await setPowerOnOffTime();
 * ```
 *
 * @since 1.2.0
 */
export interface PowerOnOffTime {
    enable: boolean;
    onTime: number;
    offTime: number;
}
export declare function setPowerOnOffTime(options?: PowerOnOffTime): Promise<string>;
/**
 * @example
 * ```typescript
 * import { openSettingConfig } from '@cakioe/tauri-plugin-board';
 * await openSettingConfig();
 * ```
 *
 * @since 1.2.6
 */
export declare function openSettingConfig(options: {
    enable: boolean;
}): Promise<void>;
/**
 * @example
 * ```typescript
 * import { openFileManager } from '@cakioe/tauri-plugin-board';
 * await openFileManager();
 * ```
 *
 * @since 1.2.6
 */
export declare function openFileManager(options: {
    enable: boolean;
}): Promise<void>;
/**
 * @example
 * ```typescript
 * import { setAppBrightness } from '@cakioe/tauri-plugin-board';
 * await setAppBrightness({value: 50});
 * ```
 *
 * @since 1.3.2
 */
export declare function setAppBrightness(options?: {
    value: number;
}): Promise<string>;
/**
 * @example
 * ```typescript
 * import { getSerialDevicesPath } from '@cakioe/tauri-plugin-board';
 * await getSerialDevicesPath();
 * ```
 *
 * @since 1.4.0-beta.5
 */
export interface SerialDevice {
    path: string;
    active: boolean;
    index: number;
    disabled: boolean;
}
export declare function getSerialDevicesPath(): Promise<SerialDevice[]>;
/**
 * @example
 * ```typescript
 * import { getBuildEnv } from '@cakioe/tauri-plugin-board';
 * await getBuildEnv({ ...options });
 * ```
 *
 * @since 1.4.0-beta.12
 * @update 1.5.6
 */
export interface BuildEnv {
    sdk_version: number;
    android_version: string;
    serial_sn: string;
    model_no: string;
    screen_width: number;
    screen_height: number;
    commid: string;
    baudrate: number;
    status_bar_on: string;
    gesture_status_bar_on: string;
}
export declare function getBuildEnv(options?: {
    no: string;
}): Promise<BuildEnv>;
/**
 * @example
 * ```typescript
 * import { openMainActivity } from '@cakioe/tauri-plugin-board';
 * await openMainActivity();
 * ```
 *
 * @since 1.4.0-beta.14
 */
export declare function openMainActivity(): Promise<void>;
/**
 * @example
 * ```typescript
 * import { takeScreenShot } from '@cakioe/tauri-plugin-board';
 * await takeScreenShot();
 * ```
 *
 * @since 1.4.0-beta.19
 */
export declare function takeScreenShot(): Promise<string>;
/**
 * @example
 * ```typescript
 * import { BuildBoard } from '@cakioe/tauri-plugin-board';
 * await BuildBoard({input: input});
 * ```
 *
 * @since 1.5.1
 */
export interface BuildBoard {
    temperature: string;
    humidity: string;
    hardware_version: string;
    software_version: string;
    board_rows: number;
    board_columns: number;
}
export declare function getBuildBoard(options?: {
    addr: number;
}): Promise<BuildBoard>;
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
export declare function execShipment(options?: {
    addr: number;
    no: number;
    floorType: number;
    isDc: boolean;
    isLp: boolean;
}): Promise<string>;
/**
 * @example
 * ```typescript
 * import { getBoxStatus } from '@cakioe/tauri-plugin-board';
 * await getBoxStatus({...options});
 * ```
 *
 * @since 1.6.0
 */
export interface BoxStatus {
    no: number;
    status: number;
}
export declare function getBoxStatus(options?: {
    addr: number;
    no: number;
}): Promise<BoxStatus>;
/**
 * @example
 * ```typescript
 * import { getYPos } from '@cakioe/tauri-plugin-board';
 * await getYPos({ ...options });
 * ```
 *
 * @since 1.6.0
 */
export declare function getYPos(options?: {
    addr: number;
}): Promise<number>;
/**
 * @example
 * ```typescript
 * import { getXPos } from '@cakioe/tauri-plugin-board';
 * await getXPos({ ...options });
 * ```
 *
 * @since 1.6.0
 */
export declare function getXPos(options?: {
    addr: number;
}): Promise<number>;
/**
 * @example
 * ```typescript
 * import { getDropStatus } from '@cakioe/tauri-plugin-board';
 * await getDropStatus({ ...options });
 * ```
 *
 * @since 1.6.0
 */
export declare function getDropStatus(options?: {
    addr: number;
}): Promise<number>;
/**
 * @example
 * ```typescript
 * import { getXStatus } from '@cakioe/tauri-plugin-board';
 * await getXStatus({ ...options });
 * ```
 *
 * @since 1.6.0
 */
export interface XYStatus {
    run_status: number;
    status_message: string;
    fault_code: number;
    fault_message: string;
}
export declare function getYStatus(options?: {
    addr: number;
}): Promise<XYStatus>;
/**
 * @example
 * ```typescript
 * import { getXStatus } from '@cakioe/tauri-plugin-board';
 * await getXStatus({ ...options });
 * ```
 *
 * @since 1.6.0
 */
export declare function getXStatus(options?: {
    addr: number;
}): Promise<XYStatus>;
/**
 * @example
 * ```typescript
 * import { resetLift } from '@cakioe/tauri-plugin-board';
 * await resetLift({ ...options });
 * ```
 *
 * @since 1.6.0
 */
export declare function resetLift(options?: {
    addr: number;
}): Promise<string>;
/**
 * @example
 * ```typescript
 * import { runMoto } from '@cakioe/tauri-plugin-board';
 * await runMoto({ ...options });
 * ```
 *
 * @since 1.6.0
 */
export declare function runMoto(options?: {
    addr: number;
    mode: number;
    status: number;
}): Promise<void>;
/**
 * @example
 * ```typescript
 * import { getShipmentStatus } from '@cakioe/tauri-plugin-board';
 * await getShipmentStatus({ ...options });
 * ```
 *
 * @since 1.6.0
 */
export declare function getShipmentStatus(options?: {
    addr: number;
}): Promise<XYStatus>;
/**
 * @example
 * ```typescript
 * import { setXPos } from '@cakioe/tauri-plugin-board';
 * await setXPos({ ...options });
 * ```
 *
 * @since 1.6.0
 */
export declare function setXPos(options?: {
    addr: number;
    values: number[];
}): Promise<string>;
/**
 * @example
 * ```typescript
 * import { setYPos } from '@cakioe/tauri-plugin-board';
 * await setYPos({ ...options });
 * ```
 *
 * @since 1.6.0
 */
export declare function setYPos(options?: {
    addr: number;
    values: number[];
}): Promise<string>;
/**
 * @example
 * ```typescript
 * import { toX } from '@cakioe/tauri-plugin-board';
 * await toX({ ...options });
 * ```
 *
 * @since 1.6.0
 */
export declare function toX(options?: {
    addr: number;
    pos: number;
}): Promise<string>;
/**
 * @example
 * ```typescript
 * import { toX } from '@cakioe/tauri-plugin-board';
 * await toX({ ...options });
 * ```
 *
 * @since 1.6.0
 */
export declare function toY(options?: {
    addr: number;
    pos: number;
}): Promise<string>;
export interface HardwareConfig {
    version: number;
    with_coin: boolean;
    with_cash: boolean;
    with_pos: boolean;
    with_pulse: boolean;
    with_identify: boolean;
    code: string;
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
export declare function readHardwareConfig(): Promise<HardwareConfig>;
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
export declare function getSoftwareVersion(): Promise<string>;
export interface MinPayoutAmount {
    value: number;
    decimal: number;
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
export declare function getMinPayoutAmount(): Promise<MinPayoutAmount>;
export interface PayAmount {
    pay_type: number;
    status: number;
    multiple: number;
    cancel: number;
    fault?: number;
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
export declare function getPayAmount(): Promise<PayAmount>;
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
export declare function initPayment(options?: {
    no: number;
    multiple: number;
    addr?: number;
}): Promise<string>;
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
export declare function notifyPayment(options?: {
    flag: boolean;
}): Promise<string>;
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
export declare function notifyResult(options?: {
    flag: boolean;
}): Promise<string>;
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
export declare function changeBalance(options?: {
    multiple: number;
}): Promise<string>;
export interface ChangeStatus {
    status: number;
    multiple: number;
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
export declare function getChangeStatus(): Promise<ChangeStatus>;
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
export declare function findChangeResult(): Promise<number[]>;
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
export declare function setAcceptMoney(options?: {
    type: number;
    channels: number[];
}): Promise<string>;
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
export declare function syncSystemTime(): Promise<string>;
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
export declare function setAgeScope(options?: {
    age: number;
}): Promise<string>;
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
export declare function getAuthResult(): Promise<number>;
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
export declare function setWorkMode(options?: {
    mode: number;
}): Promise<string>;
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
export declare function setPayChannel(options?: {
    mode: number;
}): Promise<string>;
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
export declare function pulseBalance(options?: {
    type: number;
    value: number;
}): Promise<string>;
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
export declare function motoTimeout(options?: {
    addr: number;
    time: number;
}): Promise<string>;
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
export declare function setPickXY(options?: {
    addr: number;
    pos: number;
    mode: number;
}): Promise<string>;
