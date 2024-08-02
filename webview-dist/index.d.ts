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
 * import { getBuildModel } from '@cakioe/tauri-plugin-board';
 * const no = await getBuildModel();
 * if (no) {
 *    // use the no string here
 * }
 * ```
 *
 * @since 1.2.0
 */
export declare function getBuildModel(): Promise<string>;
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
 * import { setPowetOnOffTime } from '@cakioe/tauri-plugin-board';
 * await setPowetOnOffTime();
 * ```
 *
 * @since 1.2.0
 */
export interface PowetOnOffTime {
    enable: boolean;
    on_time: number[];
    off_time: number[];
}
export declare function setPowetOnOffTime(options?: PowetOnOffTime): Promise<void>;
