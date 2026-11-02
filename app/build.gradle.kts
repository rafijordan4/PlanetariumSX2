plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}
android {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    namespace = "com.rafi.planetariumsx2"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.rafi.planetariumsx2"
        minSdk = 23
        targetSdk = 28
        versionCode = 1
        versionName = "0.1.0"
    }
}
