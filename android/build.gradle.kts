import java.util.Properties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

var localProperties = Properties().apply {
    var propFile = file("local.properties")
    if (propFile.exists()) {
        propFile.inputStream().use { load(it) }
    }
}

android {
    namespace = "com.plugin.board"
    compileSdk = 34

    buildFeatures {
        buildConfig=true
    }

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        ndk {
            abiFilters.add("armeabi-v7a")
            abiFilters.add("armeabi-v8a")
        }

        localProperties.apply {
            buildConfigField("String", "PROTOCOL", "\"${this["protocol"] as String}\"")
            buildConfigField("String", "BROKER", "\"${this["broker"] as String}\"")
            buildConfigField("int", "PORT", this["port"].toString())
            buildConfigField("String", "USERNAME", "\"${this["username"] as String}\"")
            buildConfigField("String", "PASSWORD", "\"${this["password"] as String}\"")
            buildConfigField("String", "MERCHANT_ID", "\"${this["merchant_id"] as String}\"")
        }
    }

    sourceSets {
        getByName("main") {
            jniLibs.srcDirs("libs")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    packaging {
        // Unable to strip the following libraries, packaging them as they are: libserial_port.so.
        jniLibs.keepDebugSymbols.add("**/libserial_port.so")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("androidx.work:work-runtime-ktx:2.9.1")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.0")
    implementation("com.google.android.material:material:1.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation(project(":tauri-android"))
}
