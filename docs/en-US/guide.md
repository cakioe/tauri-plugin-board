---
outline: deep
---

## Storage Configuration

```kotlin
File(this.applicationContext.getExternalFilesDir(null), "xxx.json").writeText(Gson().toJson(payload))
```

## Generating SQLDelight Interface

```bash
./android/gradlew generateSqlDelightInterface
```

## Copy db file by adb

```bash
adb pull /sdcard/xxx/files/xxx.db ./
```

## tauri.config.json configuration
```json
"plugins": {
    "board": {
        "protocol": "", // mqtt版本
        "broker": "", // ip地址或域名
        "port": 1883,
        "username": "",
        "password": "",
        "merchant_id": "",
        "app_key": ""
    }
}
```

## hivemq-mqtt-client of configuration

add missing configuration items in your main project `build.gradle.kts`

```kotlin
import java.util.Properties

....

// 签名
var keyProperties = Properties().apply {
    var propFile = file("key.properties")
    if (propFile.exists()) {
      propFile.inputStream().use { load(it) }
    }
}

android {
    defaultConfig {
        ....
    }
    signingConfigs {
        create("release") {
            keyAlias = keyProperties["keyAlias"] as String
            keyPassword = keyProperties["keyPassword"] as String
            storeFile = file(keyProperties["storeFile"] as String)
            storePassword = keyProperties["storePassword"] as String
        }
    }
    buildTypes {
        getByName("debug") {
            ...
        }
        getByName("release") {
            ....
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                ....
            )
        }
    }

    // https://hivemq.github.io/hivemq-mqtt-client/docs/installation/android/
    resources {
        excludes += arrayOf("META-INF/INDEX.LIST", "META-INF/io.netty.versions.properties")
    }
}
```

## proguard-rules of configure

add missing configuration items in your main project `proguard-rules.pro`

```md
# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.

-keepclassmembernames class io.netty.** { *; }
-keepclassmembers class org.jctools.** { *; }

-keep class io.netty.** { *; }
-keep class org.jctools.** { *; }
-keep class org.conscrypt.** { *; }
-keep class org.bouncycastle.** { *; }
-keep class org.openjsse.** { *; }
-keep class com.aayushatharva.** { *; }
-keep class com.github.luben.** { *; }
-keep class com.google.protobuf.** { *; }
-keep class com.jcraft.** { *; }
-keep class com.ning.** { *; }
-keep class net.jpountz.** { *; }
-keep class org.apache.log4j.** { *; }
-keep class org.jboss.log4j.** { *; }
-keep class org.slf4j.** { *; }
-keep class sun.security.** { *; }

-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**
-dontwarn com.aayushatharva.**
-dontwarn com.github.luben.**
-dontwarn com.google.protobuf.**
-dontwarn com.jcraft.**
-dontwarn com.ning.**
-dontwarn io.netty.**
-dontwarn net.jpountz.**
-dontwarn org.apache.log4j.**
-dontwarn org.jboss.log4j.**
-dontwarn org.slf4j.**
-dontwarn sun.security.**
-dontwarn reactor.blockhound.integration.BlockHoundIntegration

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken

##---------------End: proguard configuration for Gson  ----------
-keep class com.plugin.board.** { *; }
-keep class cc.uling.usdk.** { *; }
-keepattributes io.github.cakioe.Signature
-keep class com.google.gson.reflect.TypeToken { *; }

-keep class com.plugin.board.database.** { *; }
-keepattributes com.plugin.board.database.**
-keepclassmembers class com.plugin.board.database.** {
    public *;
}
```