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
 * @update 1.7.0-beta.1
 */
export interface SerialDevice {
  id?: number
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

export interface Config {
    id: number
    sdk_version: string
    android_version: string
    serial_sn: string
    model_no: string
    screen_width: number
    screen_height: number
    commid: string
    baudrate: number
    status_bar_on: string | number
    gesture_status_bar_on: string | number
    brightness: number
    rows?: number
    columns?: number
    is_with_coin?: boolean
    is_with_cash?: boolean
    is_with_pos?: boolean
    is_with_pulse?: boolean
    is_with_identify?: boolean
    code?: string
}

/**
 * @example
 * ```typescript
 * import { getConfig } from '@cakioe/tauri-plugin-board';
 * await getConfig();
 * ```
 *
 * @since 1.7.0-beta.3
 */
export async function getConfig(options?: { no: string }): Promise<Config> {
  return await invoke<Record<string, string>>('plugin:board|get_config', { ...options }).then(
    r => JSON.parse(r.value) as unknown as Config
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
 * import { getYPos } from '@cakioe/tauri-plugin-board';
 * await getYPos({ ...options });
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

/**
 * @example
 * ```typescript
 * import { runMoto } from '@cakioe/tauri-plugin-board';
 * await runMoto({ ...options });
 * ```
 *
 * @since 1.6.0
 */
export async function runMoto(options?: { addr: number; mode: number; status: number }): Promise<void> {
  await invoke<Record<string, string>>('plugin:board|run_moto', { ...options })
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
export async function getShipmentStatus(options?: { addr: number }): Promise<XYStatus> {
  return await invoke<Record<string, string>>('plugin:board|get_shipment_status', { ...options }).then(
    r => JSON.parse(r.value) as unknown as XYStatus
  )
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
export async function setXPos(options?: { addr: number; values: number[] }): Promise<string> {
  if (options?.values.length !== 10) {
    throw new Error('x length must be 10')
  }

  return await invoke<Record<string, string>>('plugin:board|set_x_pos', { ...options }).then(r => r.value)
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
export async function setYPos(options?: { addr: number; values: number[] }): Promise<string> {
  if (options?.values.length !== 10) {
    throw new Error('y length must be 10')
  }

  return await invoke<Record<string, string>>('plugin:board|set_y_pos', { ...options }).then(r => r.value)
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
export async function toX(options?: { addr: number; pos: number }): Promise<string> {
  return await invoke<Record<string, string>>('plugin:board|to_x', { ...options }).then(r => r.value)
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
export async function toY(options?: { addr: number; pos: number }): Promise<string> {
  return await invoke<Record<string, string>>('plugin:board|to_y', { ...options }).then(r => r.value)
}

export interface HardwareConfig {
  version: number
  with_coin: boolean
  with_cash: boolean
  with_pos: boolean
  with_pulse: boolean
  with_identify: boolean
  code: string
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
export async function readHardwareConfig(): Promise<HardwareConfig> {
  return await invoke<Record<string, string>>('plugin:board|read_hardware_config').then(
    r => JSON.parse(r.value) as unknown as HardwareConfig
  )
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
export async function getSoftwareVersion(): Promise<string> {
  return await invoke<Record<string, string>>('plugin:board|get_software_version').then(r => r.value)
}

export interface MinPayoutAmount {
  value: number
  decimal: number
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
export async function getMinPayoutAmount(): Promise<MinPayoutAmount> {
  return await invoke<Record<string, string>>('plugin:board|get_min_payout_amount').then(
    r => JSON.parse(r.value) as unknown as MinPayoutAmount
  )
}

export interface PayAmount {
  pay_type: number
  status: number
  multiple: number
  cancel: number
  fault?: number
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
export async function getPayAmount(): Promise<PayAmount> {
  return await invoke<Record<string, string>>('plugin:board|get_pay_amount').then(
    r => JSON.parse(r.value) as unknown as PayAmount
  )
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
export async function initPayment(options?: { no: number; multiple: number; addr?: number }): Promise<string> {
  return await invoke<Record<string, string>>('plugin:board|init_payment', { ...options }).then(r => r.value)
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
export async function notifyPayment(options?: { flag: boolean }): Promise<string> {
  return await invoke<Record<string, string>>('plugin:board|notify_payment', { ...options }).then(r => r.value)
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
export async function notifyResult(options?: { flag: boolean }): Promise<string> {
  return await invoke<Record<string, string>>('plugin:board|notify_result', { ...options }).then(r => r.value)
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
export async function changeBalance(options?: { multiple: number }): Promise<string> {
  return await invoke<Record<string, string>>('plugin:board|change_balance', { ...options }).then(r => r.value)
}

export interface ChangeStatus {
  status: number
  multiple: number
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
export async function getChangeStatus(): Promise<ChangeStatus> {
  return await invoke<Record<string, string>>('plugin:board|get_change_status').then(
    r => JSON.parse(r.value) as unknown as ChangeStatus
  )
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
export async function findChangeResult(): Promise<number[]> {
  return await invoke<Record<string, string>>('plugin:board|find_change_result').then(
    r => JSON.parse(r.value) as unknown as number[]
  )
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
export async function setAcceptMoney(options?: { type: number; channels: number[] }): Promise<string> {
  if (options?.type !== 0 && options?.type !== 1) {
    throw new Error('type must be 0 or 1')
  }

  if (options?.channels.length !== 16) {
    throw new Error('channels length must be 16')
  }

  return await invoke<Record<string, string>>('plugin:board|set_accept_money', { ...options }).then(r => r.value)
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
export async function syncSystemTime(): Promise<string> {
  return await invoke<Record<string, string>>('plugin:board|sync_system_time').then(r => r.value)
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
export async function setAgeScope(options?: { age: number }): Promise<string> {
  return await invoke<Record<string, string>>('plugin:board|set_age_scope', { ...options }).then(r => r.value)
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
export async function getAuthResult(): Promise<number> {
  return await invoke<Record<string, string>>('plugin:board|get_auth_result').then(r => parseInt(r.value))
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
export async function setWorkMode(options?: { mode: number }): Promise<string> {
  if (options?.mode !== 0 && options?.mode !== 1) {
    throw new Error('mode must be 0 or 1')
  }

  return await invoke<Record<string, string>>('plugin:board|set_work_mode', { ...options }).then(r => r.value)
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
export async function setPayChannel(options?: { mode: number }): Promise<string> {
  if (options?.mode !== 0 && options?.mode !== 1 && options?.mode !== 2) {
    throw new Error('mode must be 0 or 1 or 2')
  }

  return await invoke<Record<string, string>>('plugin:board|set_pay_channel', { ...options }).then(r => r.value)
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
export async function pulseBalance(options?: { type: number; value: number }): Promise<string> {
  return await invoke<Record<string, string>>('plugin:board|pulse_balance', { ...options }).then(r => r.value)
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
export async function motoTimeout(options?: { addr: number; time: number }): Promise<string> {
  return await invoke<Record<string, string>>('plugin:board|moto_timeout', { ...options }).then(r => r.value)
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
export async function setPickXY(options?: { addr: number; pos: number; mode: number }): Promise<string> {
  if (options?.mode !== 0 && options?.mode !== 1) {
    throw new Error('mode must be 0 or 1')
  }

  return await invoke<Record<string, string>>('plugin:board|set_pick_xy', { ...options }).then(r => r.value)
}

/**
 * @example
 * ```typescript
 * import { getFloorTypes } from '@cakioe/tauri-plugin-board';
 * await getFloorTypes();
 * ```
 *
 * @since 1.7.2
 * @returns {FloorType[]}
 */
export interface FloorType {
    id: number
    display_name: string
}
export async function getFloorTypes(): Promise<FloorType[]> {
  return await invoke<Record<string, string>>('plugin:board|get_floor_types').then(r => JSON.parse(r.value) as unknown as FloorType[])
}