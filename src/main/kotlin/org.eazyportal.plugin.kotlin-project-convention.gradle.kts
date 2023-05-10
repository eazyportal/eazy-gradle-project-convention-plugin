import org.eazyportal.plugin.convention.extension.getJavaVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm")

    id("org.eazyportal.plugin.java-project-convention")
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
