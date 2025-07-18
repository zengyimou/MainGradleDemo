// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") apply false
    id("com.android.library") apply false
    kotlin("android") version "2.1.0" apply false
}

buildscript {
    repositories {
        maven {
            url = uri("$rootDir/build/repo")
        }
        mavenLocal()  // 本地仓库
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.mib:publish:1.0.2")
    }
}


tasks.register<Delete>("clean") {
    delete(layout.buildDirectory)
}