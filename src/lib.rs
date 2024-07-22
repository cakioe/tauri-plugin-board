// src/lib.rs

#![cfg(mobile)]

use serde_json::json;
use tauri::{
    plugin::{Builder, PluginHandle, TauriPlugin},
    Manager, Runtime,
};

mod error;

pub use error::{Error, Result};

// #[cfg(target_os = "android")]
const PLUGIN_IDENTIFIER: &str = "com.plugin.board";

pub struct Board<R: Runtime>(PluginHandle<R>);

impl<R: Runtime> Board<R> {
    pub fn shutdown(&self) -> crate::Result<()> {
        self.0.run_mobile_plugin("shutdown", ())?;
        Ok(())
    }

    pub fn reboot(&self) -> crate::Result<()> {
        self.0.run_mobile_plugin("reboot", ())?;
        Ok(())
    }

    pub fn set_status_bar(&self, enable: bool) -> crate::Result<()> {
        self.0
            .run_mobile_plugin::<StatusBar>("set_status_bar", json!(value: enable))?;
        Ok(())
    }

    pub fn set_gesture_status_bar(&self, enable: bool) -> crate::Result<()> {
        self.0.run_mobile_plugin::<GestureStatusBar>(
            "set_gesture_status_bar",
            json!(value: enable),
        )?;
        Ok(())
    }

    pub fn get_build_model(&self) -> crate::Result<()> {
        self.0.run_mobile_plugin("get_build_model", ())?;
        Ok(())
    }

    pub fn get_build_serial(&self) -> crate::Result<()> {
        self.0.run_mobile_plugin("get_build_serial", ())?;
        Ok(())
    }

    pub fn set_lcd_on_off(&self, enable: bool) -> crate::Result<()> {
        self.0
            .run_mobile_plugin::<LcdOnOff>("set_lcd_on_off", json!(value: enable))?;
        Ok(())
    }
}

/// Initializes the plugin.
pub fn init<R: Runtime>() -> TauriPlugin<R> {
    Builder::new("board")
        .setup(|app, api| {
            #[cfg(target_os = "android")]
            let handle = api.register_android_plugin(PLUGIN_IDENTIFIER, "BoardPlugin")?;
            app.manage(Board(handle));

            Ok(())
        })
        .build()
}
