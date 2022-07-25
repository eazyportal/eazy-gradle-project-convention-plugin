plugins {
    `kotlin-dsl`
    id("maven-publish")
}

apply(from = "src/main/kotlin/org.eazyportal.plugin.dependency-version-lock-convention.gradle.kts")

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
    implementation("org.jetbrains.kotlin", "kotlin-gradle-plugin")

    implementation("org.asciidoctor.jvm.convert", "org.asciidoctor.jvm.convert.gradle.plugin", project.properties["asciidoctorPluginVersion"] as String)
    implementation("org.asciidoctor.jvm.gems", "org.asciidoctor.jvm.gems.gradle.plugin", project.properties["asciidoctorPluginVersion"] as String)

    implementation("org.cyclonedx.bom", "org.cyclonedx.bom.gradle.plugin", project.properties["cyclonedxPluginVersion"] as String)
}
