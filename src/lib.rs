use tauri::{plugin::{Builder, TauriPlugin}, Runtime};

/// Initializes the plugin.
pub fn init<R: Runtime>() -> TauriPlugin<R> {
    Builder::new("board")
        .setup(|_, _api| {
            #[cfg(target_os = "android")]
                let handle = _api.register_android_plugin("com.plugin.board", "DevicePlugin")?;
            Ok(())
        })
        .build()
}