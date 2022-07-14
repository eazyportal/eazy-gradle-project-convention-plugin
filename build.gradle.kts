plugins {
    `kotlin-dsl`
    id("maven-publish")
}

apply(from = "src/main/kotlin/org.eazyportal.plugin.dependency-version-lock-convention.gradle.kts")

repositories {
    gradlePluginPortal()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_11.toString()))
    }
}

kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(java.targetCompatibility.toString()))
    }
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
}

publishing {
    publications {
        withType<MavenPublication> {
            versionMapping {
                allVariants {
                    fromResolutionResult()
                }
            }
        }
    }

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
