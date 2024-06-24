/**
 * @method shutdown
 * @description: shutdown the board
 */
export declare function shutdown(): Promise<void>;
/**
 * @method reboot
 * @description: reboot the board
 */
export declare function reboot(): Promise<void>;
/**
 * @method setStatusBar
 * @description: set status bar
 * @param enable
 */
export declare function setStatusBar(enable: boolean): Promise<void>;
/**
 * @method setGestureStatusBar
 * @description: set gesture status bar
 * @param enable
 */
export declare function setGestureStatusBar(enable: boolean): Promise<void>;
/**
 * @method getBuildModel
 * @description: get build model
 */
export declare function getBuildModel(): Promise<void>;
/**
 * @method getBuildSerial
 * @description: get build serial
 */
export declare function getBuildSerial(): Promise<void>;
/**
 * @method setLcdOnOff
 * @description: set lcd on off
 * @param enable
 */
export declare function setLcdOnOff(enable: boolean): Promise<void>;
