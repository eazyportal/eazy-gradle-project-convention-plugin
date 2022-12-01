import org.eazyportal.plugin.convention.extension.getJavaVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.eazyportal.plugin.java-project-convention")

    id("org.jetbrains.kotlin.jvm")
}

kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(project.getJavaVersion())
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            allWarningsAsErrors = true
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = project.getJavaVersion().toString()
        }
    }
}
