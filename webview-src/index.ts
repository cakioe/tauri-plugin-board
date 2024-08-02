// @ts-ignore
import { invoke } from '@tauri-apps/api/core'
import type { PowerOnOffTime, JSObject } from './index.d'

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

export async function setPowerOnOffTime(options?: PowerOnOffTime): Promise<void> {
  await invoke('plugin:board|set_power_on_off_time', { ...options })
}

// 控制系统亮度