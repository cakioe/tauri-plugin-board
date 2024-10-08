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

# GSON<https://stackoverflow.com/a/46156695/21185153>
-keep class com.plugin.board.** { *; }
-keep class cc.uling.usdk.** { *; }
-keepattributes io.github.cakioe.Signature
-keep class com.google.gson.reflect.TypeToken { *; }

-keep class com.plugin.board.database.** { *; }
-keepattributes com.plugin.board.database.**
-keepclassmembers class com.plugin.board.database.** {
    public *;
}

-dontoptimize