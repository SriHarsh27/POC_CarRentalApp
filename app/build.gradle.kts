plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Dagger and KSP
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.ksp)
    // Google Services
    alias(libs.plugins.google.services.plugin)
}

android {
    // Android Car Library
    useLibrary("android.car")

    namespace = "com.example.poc_carrentalapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.poc_carrentalapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Hilt and Dagger
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    // ViewModel
    implementation(libs.lifecycle.viewmodel.ktx)
    // LiveData
    implementation(libs.lifecycle.livedata.ktx)
    // Lifecycles only (without ViewModel or LiveData)
    implementation(libs.lifecycle.runtime.ktx)
    ksp(libs.lifecycle.compiler)
    // Firebase BOM
    implementation(platform(libs.firebase.bom))
    // Firebase Firestore
    implementation(libs.firebase.firestore.ktx)
    // Firebase Messaging
    implementation(libs.firebase.messaging.ktx)
    // Firebase Auth
    implementation(libs.firebase.auth.ktx)
    // Firebase Functions
    implementation(libs.firebase.functions.ktx)
    // Firebase Analytics
    implementation(libs.firebase.analytics)

    // Default Dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}