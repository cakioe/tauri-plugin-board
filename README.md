# Tauri Plugin board
vending machine development board of kits for tauri, use java or kotlin

### main development board model
zc-328, zc-356 etc

```
# Go into the android folder of your plugin and create the tauri-api path
$ cd android
$ mkdir -p .tauri/tauri-api
# Clone the api from the tauri repo
$ git clone https://github.com/tauri-apps/tauri
# Copy the mobile api from the repo to the created path
$ cp -r tauri/core/tauri/mobile/android/. .tauri/tauri-api
# Remove the tauri repo
$ rm -rf tauri
```

https://v2.tauri.app/zh-cn/develop/plugins/

#### mqtt client for kotlin
+ eclipse <https://github.com/eclipse/paho.mqtt.android> not support android 12 and not updates
+ hannesa2 <https://github.com/hannesa2/paho.mqtt.android> aar file has some errors
+ hivemq <https://github.com/hivemq/hivemq-mqtt-client> build error
+ waiting more
