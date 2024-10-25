---
layout: default
title: 中文文档
---

<div class="sticky top-0 z-40 flex justify-start gap-x-6 bg-white px-4 py-4 shadow-sm sm:px-10">
  <div class="flex flex-col justify-start gap-y-5 sm:flex-row sm:gap-y-0 sm:gap-x-6">
    <button
      class="group inline-flex items-center justify-center rounded-full py-2 px-4 text-sm font-semibold focus:outline-none focus-visible:outline-2 focus-visible:outline-offset-2 bg-slate-900 text-white hover:bg-slate-700 hover:text-slate-100 active:bg-slate-800 active:text-slate-300 focus-visible:outline-slate-900 animate-fade-in-left"
    >
      中文文档
    </button>
    <div class="relative flex flex-1 flex-col items-stretch sm:flex-none">
      <a
        class="group inline-flex ring-1 items-center justify-center rounded-full py-2 px-4 text-sm focus:outline-none ring-slate-200 text-slate-700 hover:text-slate-900 hover:ring-slate-300 active:bg-slate-100 active:text-slate-600 focus-visible:outline-blue-600 focus-visible:ring-slate-300 animate-fade-in-right"
        href="/docs/en_US"
      >
        English Document
      </a>
    </div>
  </div>
</div>

<section class="container mx-auto py-5">
  <h1 class="text-lg font-medium text-neutral-900">文档说明</h1>

  <p class="mt-1 text-base text-neutral-500">
    本插件可用于基于 <a href="https://v2.tauri.app/">Tauri</a>开发的移动APP项目。
  </p>

  <ul class="text-sm text-red-700 font-bold pt-5">
    <li>1、可作用于依赖 ZC-*/卓策系列安卓开发板。</li>
    <li>2、可作用 <a href="https://www.dsjsdz.com">鼎商公司</a> 提供的售卖机驱动板以及关联的MDB服务。</li>
  </ul>

  <p class="mt-1 text-base text-gray-500">
    如果你有好的安卓版/售卖机驱动板/mdb关联产品/支付设备等，可以
    <a class="text-blue-500 underline" href="https://www.dsjsdz.com/contact">联系我们</a>
  </p>

  <h2 class="mt-20 text-lg font-medium text-neutral-900">A、存储示例</h2>
  <div class="mt-2 text-sm p-2 rounded bg-gray-900 text-white">
    File(this.applicationContext.getExternalFilesDir(null), "xxx.json").writeText(Gson().toJson(payload))
  </div>

  <h2 class="mt-20 text-lg font-medium text-neutral-900">B、生成SQLDelight接口</h2>
  <div class="mt-2 text-sm p-2 rounded bg-gray-900 text-white">./android/gradlew generateSqlDelightInterface</div>

  <h2 class="mt-20 text-lg font-medium text-neutral-900">C、拷贝db文件</h2>
  <div class="mt-2 text-sm p-2 rounded bg-gray-900 text-white">adb pull /sdcard/xxx/files/xxx.db ./</div>

  <h2 class="mt-20 text-lg font-medium text-neutral-900">D、tauri.config.json 配置</h2>
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
    E、hivemq-mqtt-client 配置，在你的主项目中 `build.gradle.kts` 中添加缺失配置项
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
    F、proguard-rules配置，在你的主项目中 `proguard-rules.pro` 中添加缺失配置项
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
