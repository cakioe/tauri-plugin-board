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
declare var getBuildModel: () => Promise<void>

/**
 * getBuildSerial
 */
declare var getBuildSerial: () => Promise<void>

/**
 * setLcdOnOff
 */
declare var setLcdOnOff: (enable: boolean) => Promise<void>

export { getBuildModel, getBuildSerial, reboot, setGestureStatusBar, setLcdOnOff, setStatusBar, shutdown };
