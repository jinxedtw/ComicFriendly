import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties
import kotlin.io.path.bufferedReader

plugins {
    id("kotlin-android")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.firebase.crashlytics")
    id("com.google.gms.google-services")
}

val signName = "sign_build"

// 读取配置文件
val configDir = project.file("src/config").toPath()
val prop = Properties().apply {
    configDir.resolve("config.properties").bufferedReader().use {
        load(it)
    }
}

fun buildTime(): String {
    val dateFormat = SimpleDateFormat("yyyyMMddHHmm")
    return dateFormat.format(Date())
}

android {
    namespace = "com.comic.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.comic.friendly"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        ndk {
            abiFilters.add("armeabi-v7a")
            abiFilters.add("arm64-v8a")
        }
    }

    signingConfigs {
        create(signName) {
            storeFile = configDir.resolve(prop.getProperty("storeFile")).toFile()
            storePassword = prop.getProperty("storePassword")
            keyAlias = prop.getProperty("keyAlias")
            keyPassword = prop.getProperty("keyPassword")
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName(signName)
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }

        release {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.findByName(signName)
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        allWarningsAsErrors = false
        freeCompilerArgs += "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
    }
    buildFeatures {
        compose = true

    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    applicationVariants.all {
        outputs.all {
            // 判断是否是输出 apk 类型
            if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
                this.outputFileName = "v${defaultConfig.versionName}-${buildTime()}-${this.baseName}.apk"
            }
        }
    }

    flavorDimensions.add("config")
    productFlavors {
        create("config") {
            dimension = "config"
        }
    }

    bundle {
        density {
            enableSplit = true
        }

        language {
            enableSplit = false
        }

        abi {
            enableSplit = true
        }
    }
}

dependencies {
    implementation(project(":fetch"))
    implementation(project(":util"))

    // kotlin
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    // compose
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    // firebase
    implementation(platform("com.google.firebase:firebase-bom:32.2.2"))
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-config-ktx")
    // moshi
    implementation("com.squareup.moshi:moshi:1.14.0")
    implementation("androidx.lifecycle:lifecycle-process:2.6.2")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.14.0")
    // 图片加载
    implementation("io.coil-kt:coil-compose:2.4.0")
    // test
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}