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
declare var setStatusBar: (enable: boolean) => Promise<void>

/**
 * setGestureStatusBar
 */
declare var setGestureStatusBar: (enable: boolean) => Promise<void>

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
declare var setLcdOnOff: (enable: boolean) => Promise<void>

/**
 * ping
 */
declare var ping: () => Promise<string>

export { shutdown, reboot, setStatusBar, setGestureStatusBar, getBuildModel, getBuildSerial, setLcdOnOff, ping }
