// COMMAND

/**
 * shutdown => 关机
 * reboot => 重启
 * set_status_bar => 设置状态栏
 * set_gesture_status_bar => 设置手势状态栏
 * get_build_model => 获取型号
 * get_build_serial => 获取序列号
 * set_lcd_on_off => 设置屏幕
 * set_power_on_off_time => 设置开关机
 * open_setting_config => 打开设置
 * open_file_manager => 打开文件
 * set_app_brightness => 设置app亮度
 */
const COMMANDS: &[&str] = &[
    "shutdown",
    "reboot",
    "set_status_bar",
    "set_gesture_status_bar",
    "get_build_model",
    "get_build_serial",
    "set_lcd_on_off",
    "ping",
    "set_power_on_off_time",
    "open_setting_config",
    "open_file_manager",
    "set_app_brightness"
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
