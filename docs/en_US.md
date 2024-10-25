---
layout: default
title: English Document
---

<div class="sticky top-0 z-40 flex justify-start gap-x-6 bg-white px-4 py-4 shadow-sm sm:px-10">
  <div class="flex flex-col justify-start gap-y-5 sm:flex-row sm:gap-y-0 sm:gap-x-6">
    <a class="relative flex flex-1 flex-col items-stretch sm:flex-none" href="/docs/zh_CN">
      <button
        class="group inline-flex ring-1 items-center justify-center rounded-full py-2 px-4 text-sm focus:outline-none ring-slate-200 text-slate-700 hover:text-slate-900 hover:ring-slate-300 active:bg-slate-100 active:text-slate-600 focus-visible:outline-blue-600 focus-visible:ring-slate-300 animate-fade-in-right"
      >
        中文文档
      </button>
    </a>
    <button
      class="group inline-flex items-center justify-center rounded-full py-2 px-4 text-sm font-semibold focus:outline-none focus-visible:outline-2 focus-visible:outline-offset-2 bg-slate-900 text-white hover:bg-slate-700 hover:text-slate-100 active:bg-slate-800 active:text-slate-300 focus-visible:outline-slate-900 animate-fade-in-left"
    >
      English Document
    </button>
  </div>
</div>

<section class="container mx-auto py-5">
  <h1 class="text-lg font-medium text-neutral-900">Document description</h1>
  <p class="mt-1 text-base text-neutral-500">
    This plugin can be used for mobile APP projects developed based on <a href="https://v2.tauri.app/">Tauri</a>.
  </p>

  <ul class="text-sm text-red-700 font-bold pt-5">
    <li>1, can be used to rely on ZC-* Android development board.</li>
    <li>
      2, can use the vending machine driver board provided by <a href="https://www.dsjsdz.com">Dingshang</a> company and
      the associated MDB services.
    </li>
  </ul>

  <p class="mt-1 text-base text-gray-500">
    If you have a good Android version/vending machine driver board /mdb related products/payment devices, you can
    <a class="text-blue-500 underline" href="https://www.dsjsdz.com/contact">contact us</a>
  </p>

  <h2 class="mt-20 text-lg font-medium text-neutral-900">A、Storage Configuration</h2>
  <div class="mt-2 text-sm p-2 rounded bg-gray-900 text-white">
    File(this.applicationContext.getExternalFilesDir(null), "xxx.json").writeText(Gson().toJson(payload))
  </div>

  <h2 class="mt-20 text-lg font-medium text-neutral-900">B、Generating SQLDelight Interface</h2>
  <div class="mt-2 text-sm p-2 rounded bg-gray-900 text-white">./android/gradlew generateSqlDelightInterface</div>

  <h2 class="mt-20 text-lg font-medium text-neutral-900">C、Copy db file by adb</h2>
  <div class="mt-2 text-sm p-2 rounded bg-gray-900 text-white">adb pull /sdcard/xxx/files/xxx.db ./</div>

  <h2 class="mt-20 text-lg font-medium text-neutral-900">D、tauri.config.json configuration</h2>
  <div class="mt-2 text-sm p-2 rounded bg-gray-900 text-white">
    <pre>
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
  </pre
    >
  </div>

  <h2 class="mt-20 text-lg font-medium text-neutral-900">
    E、hivemq-mqtt-client of configuration, add missing configuration items in your main project 'build.gradle.kts'
  </h2>
  <div class="mt-2 text-sm p-2 rounded bg-gray-900 text-white">
    <pre>
import java.util.Properties

....

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

  </pre
    >
  </div>

  <h2 class="mt-20 text-lg font-medium text-neutral-900">
    F、proguard-rules of configure, add missing configuration items in your main project 'proguard-rules.pro'
  </h2>
  <div class="mt-2 text-sm p-2 rounded bg-gray-900 text-white">
    <pre>
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
  </pre
    >
  </div>
</section>
