import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.eazyportal.plugin.java-project-convention")

    id("org.jetbrains.kotlin.jvm")
}

kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_17.toString()))
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.allWarningsAsErrors = true
        kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
    }
}
