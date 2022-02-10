import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.eazyportal.plugin.java-project-convention")

    id("org.jetbrains.kotlin.jvm")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}
