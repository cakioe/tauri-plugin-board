// @ts-ignore
import { invoke } from '@tauri-apps/api/tauri'

/**
 * @method shutdown
 * @description: shutdown the board
 */
export async function shutdown(): Promise<void> {
  await invoke('plugin:board|shutdown', {})
}

/**
 * @method reboot
 * @description: reboot the board
 */
export async function reboot(): Promise<void> {
  await invoke('plugin:board|reboot', {})
}

/**
 * @method setStatusBar
 * @description: set status bar
 * @param enable
 */
export async function setStatusBar(enable: boolean): Promise<void> {
  await invoke('plugin:board|setStatusBar', { enable })
}

/**
 * @method setGestureStatusBar
 * @description: set gesture status bar
 * @param enable
 */
export async function setGestureStatusBar(enable: boolean): Promise<void> {
  await invoke('plugin:board|setGestureStatusBar', { enable })
}

/**
 * @method getBuildModel
 * @description: get build model
 */
export async function getBuildModel(): Promise<void> {
  await invoke('plugin:board|getBuildModel', {})
}

/**
 * @method getBuildSerial
 * @description: get build serial
 */
export async function getBuildSerial(): Promise<void> {
  await invoke('plugin:board|getBuildSerial', {})
}

/**
 * @method setLcdOnOff
 * @description: set lcd on off
 * @param enable
 */
export async function setLcdOnOff(enable: boolean): Promise<void> {
  await invoke('plugin:board|setLcdOnOff', { enable })
}

