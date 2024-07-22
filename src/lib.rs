// src/lib.rs

#![cfg(mobile)]

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
        println!("set_status_bar: {}", enable);
        self.0
            .run_mobile_plugin("set_status_bar", ())?;
        Ok(())
    }

    pub fn set_gesture_status_bar(&self, enable: bool) -> crate::Result<()> {
        println!("set_gesture_status_bar: {}", enable);
        self.0.run_mobile_plugin(
            "set_gesture_status_bar",
            (),
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
        println!("set_lcd_on_off: {}", enable);
        self.0
            .run_mobile_plugin("set_lcd_on_off", ())?;
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
