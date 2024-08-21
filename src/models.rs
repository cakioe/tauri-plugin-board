use serde::Serialize;

#[derive(Serialize)]
pub struct StatusBar {
    enable: bool,
}

#[derive(Serialize)]
pub struct GestureStatusBar {
    enable: bool,
}
