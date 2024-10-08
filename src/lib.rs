// src/lib.rs

#![cfg(mobile)]

pub use models::{GestureStatusBar, StatusBar};
use serde::{Deserialize, Serialize};
use tauri::{
    plugin::{Builder, PluginHandle, TauriPlugin},
    Manager, Runtime,
};

mod error;
mod models;

pub use error::{Error, Result};

#[cfg(target_os = "android")]
const PLUGIN_IDENTIFIER: &str = "com.plugin.board";

impl<R: Runtime> Board<R> {
    // 设置状态栏
    pub fn set_status_bar(&self, options: StatusBar) -> crate::Result<()> {
        self.0
            .run_mobile_plugin("set_status_bar", options)
            .map_err(Into::into)
    }

    // 设置手势状态栏
    pub fn set_gesture_status_bar(&self, options: GestureStatusBar) -> crate::Result<()> {
        self.0
            .run_mobile_plugin("set_gesture_status_bar", options)
            .map_err(Into::into)
    }

    // 设置关机
    pub fn shutdown(&self) -> crate::Result<()> {
        self.0.run_mobile_plugin("shutdown", ()).map_err(Into::into)
    }
}

pub struct Board<R: Runtime>(PluginHandle<R>);

// 定义插件配置
#[derive(Serialize, Deserialize)]
pub struct Config {
    protocol: String,
    broker: String,
    port: u16,
    username: String,
    password: String,
    merchant_id: String,
    app_key: String,
}

/// Initializes the plugin.
pub fn init<R: Runtime>() -> TauriPlugin<R, Config> {
    Builder::<R, Config>::new("board")
        .setup(|app, api| {
            #[cfg(target_os = "android")]
            let handle = api.register_android_plugin(PLUGIN_IDENTIFIER, "BoardPlugin")?;
            app.manage(Board(handle));

            Ok(())
        })
        .on_event(|_app, _event| {
            // 应用程序退出 https://v2.tauri.app/zh-cn/develop/plugins/#on_event
        })
        .build()
}
