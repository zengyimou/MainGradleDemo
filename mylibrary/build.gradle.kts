plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
    id("com.gradle.plugin-publish") version "1.3.1" // 发布插件用
}

group = "com.mib"
version = "1.0.2"
gradlePlugin {
    plugins {
        register("StringEn") {
            id = "com.mib.publish"
            implementationClass = "com.mib.publish.StringEnPlugins"
            displayName = "Gradle Greeting plugin"
            description = "Gradle plugin to say hello!"
            tags.set(listOf("search", "tags", "for", "your", "demo"))
        }
    }

    website.set("https://github.com/zengyimou/MainGradleDemo")
    vcsUrl.set("https://github.com/zengyimou/MainGradleDemo")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            groupId = group.toString()
            artifactId = "publish"
            version = version.toString()
        }
    }

    repositories {
        maven {
            name = "local"
            url = uri("${rootProject.buildDir}/repo") // 本地 repo 路径
        }
    }
}

dependencies {

    implementation("com.android.support:appcompat-v7:28.0.0")
    testImplementation("junit:junit:4.13.2")
    implementation("com.android.tools.build:gradle:8.6.1")
    implementation(gradleApi())
    implementation(localGroovy())
}

repositories {
    maven {
        url = uri("$rootDir/build/repo")
    }
    gradlePluginPortal()
}