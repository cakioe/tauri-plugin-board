use tauri::{plugin::{Builder, TauriPlugin}, Runtime};

/// Initializes the plugin.
pub fn init<R: Runtime>() -> TauriPlugin<R> {
    Builder::new("board")
        .setup(|_, api| {
            #[cfg(target_os = "android")]
                let handle = api.register_android_plugin("com.plugin.board", "DevicePlugin")?;
            Ok(())
        })
        .build()
}