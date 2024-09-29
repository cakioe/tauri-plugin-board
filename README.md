# Tauri Plugin board
This is a vending machine development board of kits for Tauri, utilizing Java or Kotlin for development.

### support development board model
- ZC-* All with Android 11.0+

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