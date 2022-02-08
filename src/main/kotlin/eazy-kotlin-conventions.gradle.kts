import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("eazy-java-conventions")

    id("org.jetbrains.kotlin.jvm")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}
