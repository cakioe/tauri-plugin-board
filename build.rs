// COMMAND

const COMMANDS: &[&str] = &[
    "shutdown",
    "reboot",
    "set_status_bar",
    "set_gesture_status_bar",
    "set_lcd_on_off",
    "set_power_on_off_time",
    "open_setting_config",
    "open_file_manager",
    "set_app_brightness",
    "get_serial_devices_path",
    "open_main_activity",
    "take_screen_shot",
    "run_shipment",
    "get_box_status",
    "get_y_pos",
    "get_x_pos",
    "get_drop_status",
    "get_y_status",
    "get_x_status",
    "reset_lift",
    "run_moto",
    "get_shipment_status",
    "set_x_pos",
    "set_y_pos",
    "to_x",
    "to_y",
    "get_pay_amount",
    "init_payment",
    "notify_payment",
    "notify_result",
    "change_balance",
    "get_change_status",
    "find_change_result",
    "set_accept_money",
    "sync_system_time",
    "set_age_scope",
    "get_auth_result",
    "set_work_mode",
    "set_pay_channel",
    "pulse_balance",
    "moto_timeout",
    "set_pick_xy",
    "get_config",
    "get_floor_types",
];

fn main() {
    if let Err(error) = tauri_plugin::Builder::new(COMMANDS)
        .global_api_script_path("./api-iife.js")
        .android_path("android")
        .try_build()
    {
        println!("{error:#}");
        if !(cfg!(docsrs) && std::env::var("TARGET").unwrap().contains("android")) {
            std::process::exit(1);
        }
    }
}
