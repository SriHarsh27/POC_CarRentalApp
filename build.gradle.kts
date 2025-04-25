// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    // Setup KSP and Dagger
    alias(libs.plugins.dagger.hilt) apply false
    alias(libs.plugins.ksp) apply false
    // Setup Google Services
    alias(libs.plugins.google.services.plugin) apply false
}

buildscript {
    dependencies {
        classpath(libs.google.services)
    }
}