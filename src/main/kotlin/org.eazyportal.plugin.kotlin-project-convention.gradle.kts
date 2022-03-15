plugins {
    id("org.eazyportal.plugin.java-project-convention")

    id("org.jetbrains.kotlin.jvm")
}

tasks {
    compileKotlin {
        kotlinOptions.allWarningsAsErrors = true
        kotlinOptions.jvmTarget = "11"
    }
}

dependencies {
    testImplementation("org.mockito.kotlin", "mockito-kotlin", "+")
}
