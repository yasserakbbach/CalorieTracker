buildscript {
    dependencies {
        classpath(Build.androidBuildTools)
        classpath(Build.kotlinGradlePlugin)
        classpath(Build.hiltAndroidGradlePlugin)
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version("7.2.0") apply(false)
    id("com.android.library") version("7.2.0") apply(false)
    id("org.jetbrains.kotlin.android") version(Kotlin.version) apply(false)
    id("org.jetbrains.kotlin.jvm") version(Kotlin.version) apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}