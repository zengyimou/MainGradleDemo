plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "1.3.1" // 发布插件用
}


group = "com.plints.demo"
version = "1.0.0"

gradlePlugin {
    plugins {
        register("encryptStrings") {
            id = "com.plints.demo"
            implementationClass = "com.plints.demo.EncryptStringsPlugin"
            displayName = "Gradle Greeting plugin"
            description = "Gradle plugin to say hello!"
            tags.set(listOf("search", "tags", "for", "your", "demo"))
        }
    }

    website.set("https://github.com/zengyimou/MainGradleDemo")
    vcsUrl.set("https://github.com/zengyimou/MainGradleDemo")
}


dependencies {
    implementation("com.android.tools.build:gradle:8.6.1")
    implementation(gradleApi())
    implementation(localGroovy())
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
}