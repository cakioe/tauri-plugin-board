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
export async function setStatusBar(options?: { enable: boolean }): Promise<void> {
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
export async function setGestureStatusBar(options?: { enable: boolean }): Promise<void> {
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
export async function setLcdOnOff(options?: { enable?: boolean }): Promise<void> {
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
export async function openSettingConfig(options: { enable: boolean }): Promise<void> {
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
export async function openFileManager(options: { enable: boolean }): Promise<void> {
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
export async function setAppBrightness(options?: { value: number }): Promise<string> {
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
  return await invoke<Record<string, string>>('plugin:board|get_serial_devices_path').then(
    r => JSON.parse(r.value) as unknown as SerialDevice[]
  )
}

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
export async function getBuildEnv(options?: { no: string }): Promise<BuildEnv> {
  return await invoke<Record<string, string>>('plugin:board|get_build_env', { ...options }).then(
    r => JSON.parse(r.value) as unknown as BuildEnv
  )
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
  temperature: string
  humidity: string
  hardware_version: string
  software_version: string
  board_rows: number
  board_columns: number
}
export async function getBuildBoard(options?: { addr: number }): Promise<BuildBoard> {
  return await invoke<Record<string, string>>('plugin:board|get_build_board', { ...options }).then(
    r => JSON.parse(r.value) as unknown as BuildBoard
  )
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
export async function execShipment(options?: {
  addr: number
  no: number
  motorId?: number
  floorType: number
  isDc: boolean
  isLp: boolean
}): Promise<string> {
  return await invoke<Record<string, string>>('plugin:board|exec_shipment', { ...options }).then(r => r.value)
}

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
  no: number
  status: number
}
export async function getBoxStatus(options?: { addr: number; no: number }): Promise<BoxStatus> {
  return await invoke<Record<string, string>>('plugin:board|get_box_status', { ...options }).then(
    r => JSON.parse(r.value) as unknown as BoxStatus
  )
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
export async function getYPos(options?: { addr: number }): Promise<number> {
  return await invoke<Record<string, string>>('plugin:board|get_y_pos', { ...options }).then(r => parseInt(r.value))
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
export async function getXPos(options?: { addr: number }): Promise<number> {
  return await invoke<Record<string, string>>('plugin:board|get_x_pos', { ...options }).then(r => parseInt(r.value))
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
export async function getDropStatus(options?: { addr: number }): Promise<number> {
  return await invoke<Record<string, string>>('plugin:board|get_drop_status', { ...options }).then(r =>
    parseInt(r.value)
  )
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
export interface XYStatus {
  run_status: number
  status_message: string
  fault_code: number
  fault_message: string
}
export async function getYStatus(options?: { addr: number }): Promise<XYStatus> {
  return await invoke<Record<string, string>>('plugin:board|get_y_status', { ...options }).then(
    r => JSON.parse(r.value) as unknown as XYStatus
  )
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
export async function getXStatus(options?: { addr: number }): Promise<XYStatus> {
  return await invoke<Record<string, string>>('plugin:board|get_x_status', { ...options }).then(
    r => JSON.parse(r.value) as unknown as XYStatus
  )
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
export async function resetLift(options?: { addr: number }): Promise<string> {
  return await invoke<Record<string, string>>('plugin:board|reset_lift', { ...options }).then(r => r.value)
}