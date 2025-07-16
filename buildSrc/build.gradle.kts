plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
}


group = "com.plints.demo"
version = "1.0.0"

gradlePlugin {
    plugins {
        register("encryptStrings") {
            id = "com.plints.demo"
            implementationClass = "com.plints.demo.EncryptStringsPlugin"
        }
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:8.6.1")
    implementation(gradleApi())
    implementation(localGroovy())
}

repositories {
    google()
    gradlePluginPortal()
}