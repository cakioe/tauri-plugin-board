if("__TAURI__"in window){var __TAURI_PLUGIN_BOARD__=function(t){"use strict";async function n(t,n={},a){return window.__TAURI_INTERNALS__.invoke(t,n,a)}return"function"==typeof SuppressedError&&SuppressedError,t.execShipment=async function(t){return await n("plugin:board|exec_shipment",{...t}).then((t=>t.value))},t.getBoxStatus=async function(t){return await n("plugin:board|get_box_status",{...t}).then((t=>JSON.parse(t.value)))},t.getBuildBoard=async function(t){return await n("plugin:board|get_build_board",{...t}).then((t=>JSON.parse(t.value)))},t.getBuildEnv=async function(t){return await n("plugin:board|get_build_env",{...t}).then((t=>JSON.parse(t.value)))},t.getDropStatus=async function(t){return await n("plugin:board|get_drop_status",{...t}).then((t=>parseInt(t.value)))},t.getSerialDevicesPath=async function(){return await n("plugin:board|get_serial_devices_path").then((t=>JSON.parse(t.value)))},t.getXPos=async function(t){return await n("plugin:board|get_x_pos",{...t}).then((t=>parseInt(t.value)))},t.getXStatus=async function(t){return await n("plugin:board|get_x_status",{...t}).then((t=>JSON.parse(t.value)))},t.getYPos=async function(t){return await n("plugin:board|get_y_pos",{...t}).then((t=>parseInt(t.value)))},t.getYStatus=async function(t){return await n("plugin:board|get_y_status",{...t}).then((t=>JSON.parse(t.value)))},t.openFileManager=async function(t){await n("plugin:board|open_file_manager",{...t})},t.openMainActivity=async function(){await n("plugin:board|open_main_activity")},t.openSettingConfig=async function(t){await n("plugin:board|open_setting_config",{...t})},t.reboot=async function(){await n("plugin:board|reboot")},t.resetLift=async function(t){return await n("plugin:board|reset_lift",{...t}).then((t=>t.value))},t.runMoto=async function(t){await n("plugin:board|run_moto",{...t})},t.setAppBrightness=async function(t){return await n("plugin:board|set_app_brightness",{...t}).then((t=>t.value))},t.setGestureStatusBar=async function(t){await n("plugin:board|set_gesture_status_bar",{...t})},t.setLcdOnOff=async function(t){await n("plugin:board|set_lcd_on_off",{...t})},t.setPowerOnOffTime=async function(t){return await n("plugin:board|set_power_on_off_time",{...t}).then((t=>t.value))},t.setStatusBar=async function(t){await n("plugin:board|set_status_bar",{...t})},t.shutdown=async function(){await n("plugin:board|shutdown")},t.takeScreenShot=async function(){return await n("plugin:board|take_screen_shot").then((t=>t.value))},t}({});Object.defineProperty(window.__TAURI__,"board",{value:__TAURI_PLUGIN_BOARD__})}
