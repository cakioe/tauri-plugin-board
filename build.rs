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
    "get_build_env",
    "open_main_activity",
    "take_screen_shot",
    "get_build_board",
    "exec_shipment",
];

fn main() {
    // https://github.com/tauri-apps/plugins-workspace/blob/v2/plugins/notification/build.rs
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
