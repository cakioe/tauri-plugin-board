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
export interface StatusBar {
    enable?: boolean;
}
export declare function setStatusBar(options?: StatusBar): Promise<void>;
/**
 * @example
 * ```typescript
 * import { setGestureStatusBar } from '@cakioe/tauri-plugin-board';
 * await setGestureStatusBar(enable);
 * ```
 *
 * @since 1.2.0
 */
export interface GestureStatusBar {
    enable?: boolean;
}
export declare function setGestureStatusBar(options?: GestureStatusBar): Promise<void>;
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
export declare function getBuildSerial(): Promise<string>;
/**
 * @example
 * ```typescript
 * import { setLcdOnOff } from '@cakioe/tauri-plugin-board';
 * await setLcdOnOff(enable);
 * ```
 *
 * @since 1.2.0
 */
export interface LcdOnOff {
    enable?: boolean;
}
export declare function setLcdOnOff(options?: LcdOnOff): Promise<void>;
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
export declare function ping(): Promise<string>;
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
export interface SettingConfig {
    enable: boolean;
}
export declare function openSettingConfig(options: FileManager): Promise<void>;
/**
 * @example
 * ```typescript
 * import { openFileManager } from '@cakioe/tauri-plugin-board';
 * await openFileManager();
 * ```
 *
 * @since 1.2.6
 */
export interface FileManager {
    enable: boolean;
}
export declare function openFileManager(options: FileManager): Promise<void>;
/**
 * @example
 * ```typescript
 * import { setAppBrightness } from '@cakioe/tauri-plugin-board';
 * await setAppBrightness({value: 50});
 * ```
 *
 * @since 1.3.2
 */
export interface AppBrightness {
    value: number;
    isScreen: boolean;
}
export declare function setAppBrightness(options?: AppBrightness): Promise<string>;
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
export declare function getSerialPaths(): Promise<string[]>;
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
 * import { getAllDevicesPath } from '@cakioe/tauri-plugin-board';
 * await getAllDevicesPath();
 * ```
 *
 * @since 1.4.0-beta.2
 */
export declare function getAllDevicesPath(): Promise<string[]>;
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
