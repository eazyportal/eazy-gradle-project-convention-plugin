plugins {
    `kotlin-dsl`
    id("maven-publish")
}

repositories {
    gradlePluginPortal()
}

kotlinDslPluginOptions {
    jvmTarget.set(provider { java.targetCompatibility.toString() })
}

tasks {
    jar {
        manifest {
            attributes["Implementation-Version"] = project.version
        }
    }

    register("lockDependencyVersion") {
        enabled = false
    }

    register("unlockDependencyVersion") {
        enabled = false
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
