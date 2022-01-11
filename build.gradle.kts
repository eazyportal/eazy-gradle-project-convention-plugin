plugins {
    `kotlin-dsl`
    id("maven-publish")
}

repositories {
    gradlePluginPortal()
}

publishing {
    repositories {
        mavenLocal()
    }
}

dependencies {
    implementation("org.cyclonedx.bom", "org.cyclonedx.bom.gradle.plugin", "+")
    implementation("org.jetbrains.kotlin", "kotlin-gradle-plugin")
}
