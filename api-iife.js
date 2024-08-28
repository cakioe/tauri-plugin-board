if("__TAURI__"in window){var __TAURI_PLUGIN_BOARD__=function(n){"use strict";async function t(n,t={},e){return window.__TAURI_INTERNALS__.invoke(n,t,e)}return"function"==typeof SuppressedError&&SuppressedError,n.changeBalance=async function(n){return await t("plugin:board|change_balance",{...n}).then((n=>n.value))},n.execShipment=async function(n){return await t("plugin:board|exec_shipment",{...n}).then((n=>n.value))},n.findChangeResult=async function(){return await t("plugin:board|find_change_result").then((n=>JSON.parse(n.value)))},n.getAuthResult=async function(){return await t("plugin:board|get_auth_result").then((n=>parseInt(n.value)))},n.getBoxStatus=async function(n){return await t("plugin:board|get_box_status",{...n}).then((n=>JSON.parse(n.value)))},n.getBuildBoard=async function(n){return await t("plugin:board|get_build_board",{...n}).then((n=>JSON.parse(n.value)))},n.getBuildEnv=async function(n){return await t("plugin:board|get_build_env",{...n}).then((n=>JSON.parse(n.value)))},n.getChangeStatus=async function(){return await t("plugin:board|get_change_status").then((n=>JSON.parse(n.value)))},n.getDropStatus=async function(n){return await t("plugin:board|get_drop_status",{...n}).then((n=>parseInt(n.value)))},n.getMinPayoutAmount=async function(){return await t("plugin:board|get_min_payout_amount").then((n=>JSON.parse(n.value)))},n.getPayAmount=async function(){return await t("plugin:board|get_pay_amount").then((n=>JSON.parse(n.value)))},n.getSerialDevicesPath=async function(){return await t("plugin:board|get_serial_devices_path").then((n=>JSON.parse(n.value)))},n.getShipmentStatus=async function(n){return await t("plugin:board|get_shipment_status",{...n}).then((n=>JSON.parse(n.value)))},n.getSoftwareVersion=async function(){return await t("plugin:board|get_software_version").then((n=>n.value))},n.getXPos=async function(n){return await t("plugin:board|get_x_pos",{...n}).then((n=>parseInt(n.value)))},n.getXStatus=async function(n){return await t("plugin:board|get_x_status",{...n}).then((n=>JSON.parse(n.value)))},n.getYPos=async function(n){return await t("plugin:board|get_y_pos",{...n}).then((n=>parseInt(n.value)))},n.getYStatus=async function(n){return await t("plugin:board|get_y_status",{...n}).then((n=>JSON.parse(n.value)))},n.initPayment=async function(n){return await t("plugin:board|init_payment",{...n}).then((n=>n.value))},n.motoTimeout=async function(n){return await t("plugin:board|moto_timeout",{...n}).then((n=>n.value))},n.notifyPayment=async function(n){return await t("plugin:board|notify_payment",{...n}).then((n=>n.value))},n.notifyResult=async function(n){return await t("plugin:board|notify_result",{...n}).then((n=>n.value))},n.openFileManager=async function(n){await t("plugin:board|open_file_manager",{...n})},n.openMainActivity=async function(){await t("plugin:board|open_main_activity")},n.openSettingConfig=async function(n){await t("plugin:board|open_setting_config",{...n})},n.pulseBalance=async function(n){return await t("plugin:board|pulse_balance",{...n}).then((n=>n.value))},n.readHardwareConfig=async function(){return await t("plugin:board|read_hardware_config").then((n=>JSON.parse(n.value)))},n.reboot=async function(){await t("plugin:board|reboot")},n.resetLift=async function(n){return await t("plugin:board|reset_lift",{...n}).then((n=>n.value))},n.runMoto=async function(n){await t("plugin:board|run_moto",{...n})},n.setAcceptMoney=async function(n){if(0!==(null==n?void 0:n.type)&&1!==(null==n?void 0:n.type))throw new Error("type must be 0 or 1");if(16!==(null==n?void 0:n.channels.length))throw new Error("channels length must be 16");return await t("plugin:board|set_accept_money",{...n}).then((n=>n.value))},n.setAgeScope=async function(n){return await t("plugin:board|set_age_scope",{...n}).then((n=>n.value))},n.setAppBrightness=async function(n){return await t("plugin:board|set_app_brightness",{...n}).then((n=>n.value))},n.setGestureStatusBar=async function(n){await t("plugin:board|set_gesture_status_bar",{...n})},n.setLcdOnOff=async function(n){await t("plugin:board|set_lcd_on_off",{...n})},n.setPayChannel=async function(n){if(0!==(null==n?void 0:n.mode)&&1!==(null==n?void 0:n.mode)&&2!==(null==n?void 0:n.mode))throw new Error("mode must be 0 or 1 or 2");return await t("plugin:board|set_pay_channel",{...n}).then((n=>n.value))},n.setPickXY=async function(n){if(0!==(null==n?void 0:n.mode)&&1!==(null==n?void 0:n.mode))throw new Error("mode must be 0 or 1");return await t("plugin:board|set_pick_xy",{...n}).then((n=>n.value))},n.setPowerOnOffTime=async function(n){return await t("plugin:board|set_power_on_off_time",{...n}).then((n=>n.value))},n.setStatusBar=async function(n){await t("plugin:board|set_status_bar",{...n})},n.setWorkMode=async function(n){if(0!==(null==n?void 0:n.mode)&&1!==(null==n?void 0:n.mode))throw new Error("mode must be 0 or 1");return await t("plugin:board|set_work_mode",{...n}).then((n=>n.value))},n.setXPos=async function(n){if(10!==(null==n?void 0:n.values.length))throw new Error("x length must be 10");return await t("plugin:board|set_x_pos",{...n}).then((n=>n.value))},n.setYPos=async function(n){if(10!==(null==n?void 0:n.values.length))throw new Error("y length must be 10");return await t("plugin:board|set_y_pos",{...n}).then((n=>n.value))},n.shutdown=async function(){await t("plugin:board|shutdown")},n.syncSystemTime=async function(){return await t("plugin:board|sync_system_time").then((n=>n.value))},n.takeScreenShot=async function(){return await t("plugin:board|take_screen_shot").then((n=>n.value))},n.toX=async function(n){return await t("plugin:board|to_x",{...n}).then((n=>n.value))},n.toY=async function(n){return await t("plugin:board|to_y",{...n}).then((n=>n.value))},n}({});Object.defineProperty(window.__TAURI__,"board",{value:__TAURI_PLUGIN_BOARD__})}
