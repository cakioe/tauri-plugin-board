import { invoke } from '@tauri-apps/api/tauri';

// @ts-ignore
/**
 * @method shutdown
 * @description: shutdown the board
 */
async function shutdown() {
    await invoke('plugin:board|shutdown', {});
}
/**
 * @method reboot
 * @description: reboot the board
 */
async function reboot() {
    await invoke('plugin:board|reboot', {});
}
/**
 * @method setStatusBar
 * @description: set status bar
 * @param enable
 */
async function setStatusBar(enable) {
    await invoke('plugin:board|setStatusBar', { enable });
}
/**
 * @method setGestureStatusBar
 * @description: set gesture status bar
 * @param enable
 */
async function setGestureStatusBar(enable) {
    await invoke('plugin:board|setGestureStatusBar', { enable });
}
/**
 * @method getBuildModel
 * @description: get build model
 */
async function getBuildModel() {
    await invoke('plugin:board|getBuildModel', {});
}
/**
 * @method getBuildSerial
 * @description: get build serial
 */
async function getBuildSerial() {
    await invoke('plugin:board|getBuildSerial', {});
}
/**
 * @method setLcdOnOff
 * @description: set lcd on off
 * @param enable
 */
async function setLcdOnOff(enable) {
    await invoke('plugin:board|setLcdOnOff', { enable });
}

export { getBuildModel, getBuildSerial, reboot, setGestureStatusBar, setLcdOnOff, setStatusBar, shutdown };
