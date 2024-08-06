// typescript types

/**
 * shutdown
 */
declare var shutdown: () => Promise<void>

/**
 * reboot
 */
declare var reboot: () => Promise<void>

/**
 * setStatusBar
 */

export interface StatusBar {
  enable?: boolean
}

declare var setStatusBar: (options?: StatusBar) => Promise<void>

/**
 * setGestureStatusBar
 */
export interface GestureStatusBar {
  enable?: boolean
}
declare var setGestureStatusBar: (options?: GestureStatusBar) => Promise<void>

/**
 * getBuildModel
 */
declare var getBuildModel: () => Promise<string>

/**
 * getBuildSerial
 */
declare var getBuildSerial: () => Promise<string>

/**
 * setLcdOnOff
 */
export interface LcdOnOff {
  enable?: boolean
}

declare var setLcdOnOff: (options?: LcdOnOff) => Promise<void>

/**
 * ping
 */
declare var ping: () => Promise<string>

/**
 * ping
 */
export interface PowerOnOffTime {
  enable: boolean
  onTime: number // year,month,day,hour,minute
  offTime: number // year,month,day,hour,minute
}
declare var setPowerOnOffTime: (options?: PowerOnOffTime) => Promise<string>

export interface JSObject {
  value: string
}

/**
 * openSettingConfig
 */
declare var openSettingConfig: () => Promise<void>

/**
 * openFileManager
 */
declare var openFileManager: () => Promise<void>

export {
  shutdown,
  reboot,
  setStatusBar,
  setGestureStatusBar,
  getBuildModel,
  getBuildSerial,
  setLcdOnOff,
  ping,
  setPowerOnOffTime,
  openSettingConfig,
  openFileManager
}
