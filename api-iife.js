if("__TAURI__"in window){var __TAURI_PLUGIN_BOARD__=function(n){"use strict";async function t(n,t={},a){return window.__TAURI_INTERNALS__.invoke(n,t,a)}return"function"==typeof SuppressedError&&SuppressedError,n.getBuildBoard=async function(n){return await t("plugin:board|get_build_board",{...n}).then((n=>JSON.parse(n.value)))},n.getBuildEnv=async function(){return await t("plugin:board|get_build_env").then((n=>JSON.parse(n.value)))},n.getSerialDevicesPath=async function(){return await t("plugin:board|get_serial_devices_path").then((n=>JSON.parse(n.value)))},n.openFileManager=async function(n){await t("plugin:board|open_file_manager",{...n})},n.openMainActivity=async function(){await t("plugin:board|open_main_activity")},n.openSettingConfig=async function(n){await t("plugin:board|open_setting_config",{...n})},n.reboot=async function(){await t("plugin:board|reboot")},n.setAppBrightness=async function(n){return await t("plugin:board|set_app_brightness",{...n}).then((n=>n.value))},n.setGestureStatusBar=async function(n){await t("plugin:board|set_gesture_status_bar",{...n})},n.setLcdOnOff=async function(n){await t("plugin:board|set_lcd_on_off",{...n})},n.setPowerOnOffTime=async function(n){return await t("plugin:board|set_power_on_off_time",{...n}).then((n=>n.value))},n.setStatusBar=async function(n){await t("plugin:board|set_status_bar",{...n})},n.shutdown=async function(){await t("plugin:board|shutdown")},n.takeScreenShot=async function(){return await t("plugin:board|take_screen_shot").then((n=>n.value))},n}({});Object.defineProperty(window.__TAURI__,"board",{value:__TAURI_PLUGIN_BOARD__})}
