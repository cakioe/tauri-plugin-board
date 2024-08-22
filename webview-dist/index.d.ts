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
 * await getBuildEnv();
 * ```
 *
 * @since 1.4.0-beta.12
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
export declare function getBuildEnv(): Promise<BuildEnv>;
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
 */
export declare function execShipment(options?: {
    addr: number;
    motorId: number;
    floorType: number;
    isDc: boolean;
    isLp: boolean;
}): Promise<string>;
