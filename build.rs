// COMMAND

/**
 * shutdown => 关机
 * reboot => 重启
 * setStatusBar => 设置状态栏
 * setGestureStatusBar => 设置手势状态栏
 * getBuildModel => 获取型号
 * getBuildSerial => 获取序列号
 * setLcdOnOff => 设置屏幕
 */
const COMMANDS: &[&str] = &[
    "shutdown",
    "reboot",
    "setStatusBar",
    "setGestureStatusBar",
    "getBuildModel",
    "getBuildSerial",
    "setLcdOnOff",
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
