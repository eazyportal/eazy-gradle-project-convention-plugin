plugins {
    `kotlin-dsl`
    id("maven-publish")
}

repositories {
    gradlePluginPortal()
}

tasks {
    jar {
        manifest {
            attributes["Implementation-Version"] = project.version
        }
    }
}

publishing {
    repositories {
        if (project.version.toString().endsWith("-SNAPSHOT")) {
            mavenLocal()
        }
        else {
            maven {
                name = "github"

                credentials(PasswordCredentials::class)
                url = uri("${project.properties["githubUrl"]!!}/${project.rootProject.name}")
            }
        }
    }
}

dependencies {
    implementation("org.cyclonedx.bom", "org.cyclonedx.bom.gradle.plugin", "+")
    implementation("org.jetbrains.kotlin", "kotlin-gradle-plugin")
}
