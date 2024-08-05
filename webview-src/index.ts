// @ts-ignore
import { invoke } from '@tauri-apps/api/core'

/**
 * @example
 * ```typescript
 * import { shutdown } from '@cakioe/tauri-plugin-board';
 * await shutdown();
 * ```
 *
 * @since 1.2.0
 */
export async function shutdown(): Promise<void> {
  await invoke('plugin:board|shutdown')
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
export async function reboot(): Promise<void> {
  await invoke('plugin:board|reboot')
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

export interface StatusBar {
  enable?: boolean
}

export async function setStatusBar(options?: StatusBar): Promise<void> {
  await invoke('plugin:board|set_status_bar', { ...options })
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

export interface GestureStatusBar {
  enable?: boolean
}

export async function setGestureStatusBar(options?: GestureStatusBar): Promise<void> {
  await invoke('plugin:board|set_gesture_status_bar', { ...options })
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

export interface JSObject {
  value: string
}

export async function getBuildModel(): Promise<string> {
  return await invoke<JSObject>('plugin:board|get_build_model').then(r => r.value)
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
export async function getBuildSerial(): Promise<string> {
  return await invoke<JSObject>('plugin:board|get_build_serial').then(r => r.value)
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
export interface LcdOnOff {
  enable?: boolean
}

export async function setLcdOnOff(options?: LcdOnOff): Promise<void> {
  await invoke('plugin:board|set_lcd_on_off', { ...options })
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
export async function ping(): Promise<string> {
  return await invoke<JSObject>('plugin:board|ping').then(r => r.value)
}

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
  enable: boolean
  on_time: number[] // year,month,day,hour,minute
  off_time: number[] // year,month,day,hour,minute
}

export async function setPowerOnOffTime(options?: PowerOnOffTime): Promise<void> {
  await invoke('plugin:board|set_power_on_off_time', { ...options })
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
export async function openSettingConfig(): Promise<void> {
  await invoke('plugin:board|open_setting_config')
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
export async function openFileManager(): Promise<void> {
  await invoke('plugin:board|open_file_manager')
}
