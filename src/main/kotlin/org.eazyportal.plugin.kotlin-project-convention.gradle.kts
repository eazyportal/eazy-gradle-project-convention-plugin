import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.eazyportal.plugin.java-project-convention")

    id("org.jetbrains.kotlin.jvm")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.allWarningsAsErrors = true
        kotlinOptions.jvmTarget = java.targetCompatibility.toString()
    }
}

dependencies {
    testImplementation("org.mockito.kotlin", "mockito-kotlin", "+")
}
