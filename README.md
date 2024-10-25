[中文文档](README_zh.md) |  [English](README.md)

## 说明

本插件可用于基于 [Tauri](https://v2.tauri.app/) 开发的APP项目。
本项目可作用于依赖 ZC-*/卓策系列安卓开发板，同时使用到 [鼎商公司](https://www.dsjsdz.com) 提供的售卖机驱动板以及关联的MDB服务。

## 可用于
- ZC-*/卓策安卓开发板，且 Android 11.0+ 以上版本
- [鼎商公司](https://www.dsjsdz.com) 提供的售卖机驱动板以及关联的MDB服务

如果你有好的安卓版/售卖机驱动板/mdb关联产品/欧美支付盒子等，可以 [联系我们](https://www.dsjsdz.com/contact)

技术支持：鼎商开发组

开发语言： kotlin/rust/typescript

# Tauri Plugin board
This is a vending machine development board of kits for Tauri, utilizing Java or Kotlin for development.

### support development board model


## Setting Up Your Tauri Plugin

Follow these steps to set up the Tauri API for your Android plugin:

```bash
# Navigate to the android folder of your plugin
cd android

# Create the tauri-api path
mkdir -p .tauri/tauri-api

# Clone the Tauri repository
git clone https://github.com/tauri-apps/tauri

# Copy the mobile API from the repository to the created path
cp -r tauri/core/tauri/mobile/android/. .tauri/tauri-api

# Remove the cloned Tauri repository
rm -rf tauri
```

## Signature Plugins

You can utilize the following signature plugins:

| Language     | Plugin Link                                    |
|--------------|------------------------------------------------|
| Kotlin       | [Signature for Kotlin](https://github.com/cakioe/signature) |
| JavaScript   | [Signature for JavaScript](https://github.com/cakioe/kit.js) |
| Go           | [Signature for Go](https://github.com/go-pansy/pansy) |
| Rust           | [Signature for Rust](https://github.com/cakioe/signatory) |

## Storage Configuration

To save data in a JSON format, you can use the following code snippet:

```kotlin
File(this.applicationContext.getExternalFilesDir(null), "xxx.json").writeText(Gson().toJson(payload))
```

## Generating SQLDelight Interface

To generate the SQLDelight interface, run the following command:

```shell
./gradlew generateSqlDelightInterface
```

To pull the database file from your Android device, use:

adb pull /sdcard/xxx/files/xxx.db ./

## [Configuration](https://github.com/tauri-apps/tauri-docs/pull/2735)

you must be add [config of board plugin](https://github.com/cakioe/tauri-plugin-board/blob/main/android/src/main/java/BoardPlugin.kt#L212) in your `tauri.config.json`, like those code.

```
  "plugins": {
    "board": {
      "protocol": "",
      "broker": "",
      "port": 1883,
      "username": "",
      "password": "",
      "merchant_id": "",
      "app_key": ""
    }
  }
```

## hivemq-mqtt-client & proguard-rules config

view `examples/build.gradle.kts.example`, `examples/proguard-rules.pro.example` codes.

copy some of those code in your main tauri app project.


