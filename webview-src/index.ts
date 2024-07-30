// @ts-ignore
import { invoke } from '@tauri-apps/api/core'

/**
 * @method shutdown
 * @description: shutdown the board
 */
export async function shutdown(): Promise<void> {
  await invoke('plugin:board|shutdown')
}

/**
 * @method reboot
 * @description: reboot the board
 */
export async function reboot(): Promise<void> {
  await invoke('plugin:board|reboot')
}

/**
 * @method setStatusBar
 * @description: set status bar
 * @param enable
 */
export async function setStatusBar(enable: boolean): Promise<void> {
  await invoke('plugin:board|set_status_bar', { enable })
}

/**
 * @method setGestureStatusBar
 * @description: set gesture status bar
 * @param enable
 */
export async function setGestureStatusBar(enable: boolean): Promise<void> {
  await invoke('plugin:board|set_gesture_status_bar', { enable })
}

/**
 * @method getBuildModel
 * @description: get build model
 */
export async function getBuildModel(): Promise<void> {
  await invoke('plugin:board|get_build_model')
}

/**
 * @method getBuildSerial
 * @description: get build serial
 */
export async function getBuildSerial(): Promise<void> {
  await invoke('plugin:board|get_build_serial')
}

/**
 * @method setLcdOnOff
 * @description: set lcd on off
 * @param enable
 */
export async function setLcdOnOff(enable: boolean): Promise<void> {
  await invoke('plugin:board|set_lcd_on_off', { enable })
}

