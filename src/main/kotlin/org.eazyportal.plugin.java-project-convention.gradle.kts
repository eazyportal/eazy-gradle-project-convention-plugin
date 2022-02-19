plugins {
    id("jacoco")

    id("java")
}

repositories {
    mavenCentral()

    gradlePluginPortal()

    maven {
        name = "github"

        credentials(PasswordCredentials::class.java)
        url = uri("${project.properties["githubUrl"]!!}/*")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks {
    test {
        useJUnitPlatform()

        finalizedBy(jacocoTestReport)
    }

    jacocoTestReport {
        dependsOn(test)
    }
}

dependencies {
    // Test dependencies
    testImplementation("org.assertj", "assertj-core", "+")
    testImplementation("org.junit.jupiter", "junit-jupiter", "+")
    testImplementation("org.mockito", "mockito-inline", "+")
}
