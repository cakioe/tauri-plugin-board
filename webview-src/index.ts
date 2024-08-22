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
 * import { setPowerOnOffTime } from '@cakioe/tauri-plugin-board';
 * await setPowerOnOffTime();
 * ```
 *
 * @since 1.2.0
 */
export interface PowerOnOffTime {
  enable: boolean
  onTime: number // year,month,day,hour,minute
  offTime: number // year,month,day,hour,minute
}
export async function setPowerOnOffTime(options?: PowerOnOffTime): Promise<string> {
  return await invoke<Record<string, string>>('plugin:board|set_power_on_off_time', { ...options }).then(r => r.value)
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
export interface SettingConfig {
  enable: boolean
}
export async function openSettingConfig(options: FileManager): Promise<void> {
  await invoke('plugin:board|open_setting_config', { ...options })
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
export interface FileManager {
  enable: boolean
}
export async function openFileManager(options: FileManager): Promise<void> {
  await invoke('plugin:board|open_file_manager', { ...options })
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
export interface AppBrightness {
  value: number
  isScreen: boolean
}
export async function setAppBrightness(options?: AppBrightness): Promise<string> {
  return await invoke<Record<string, string>>('plugin:board|set_app_brightness', { ...options }).then(r => r.value)
}

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
  path: string
  active: boolean
  index: number
  disabled: boolean
}
export async function getSerialDevicesPath(): Promise<SerialDevice[]> {
  return await invoke<Record<string, string>>('plugin:board|get_serial_devices_path').then(r => JSON.parse(r.value) as unknown as SerialDevice[])
}

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
  sdk_version: number
  android_version: string
  serial_sn: string
  model_no: string
  screen_width: number
  screen_height: number
  commid: string
  baudrate: number
  status_bar_on: string
  gesture_status_bar_on: string
}
export async function getBuildEnv(): Promise<BuildEnv> {
  return await invoke<Record<string, string>>('plugin:board|get_build_env').then(r => JSON.parse(r.value) as unknown as BuildEnv)
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
export async function openMainActivity(): Promise<void> {
  await invoke('plugin:board|open_main_activity')
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
export async function takeScreenShot(): Promise<string> {
    return await invoke<Record<string, string>>('plugin:board|take_screen_shot').then(r => r.value)
}
