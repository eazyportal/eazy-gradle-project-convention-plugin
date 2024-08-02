plugins {
    `kotlin-dsl`

    id("org.eazyportal.plugin.release")
}

apply(from = "src/main/kotlin/org.eazyportal.plugin.dependency-version-lock-convention.gradle.kts")
apply(from = "src/main/kotlin/org.eazyportal.plugin.publish-convention.gradle.kts")

repositories {
    gradlePluginPortal()

    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_21.toString()))
    }
}

tasks {
    jar {
        manifest {
            attributes["Implementation-Version"] = project.version
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin", "kotlin-gradle-plugin")

    implementation("org.asciidoctor.jvm.convert", "org.asciidoctor.jvm.convert.gradle.plugin", project.properties["asciidoctorPluginVersion"] as String)
    implementation("org.asciidoctor.jvm.gems", "org.asciidoctor.jvm.gems.gradle.plugin", project.properties["asciidoctorPluginVersion"] as String) {
        // TODO: Workaround for:
        // - https://github.com/asciidoctor/asciidoctor-gradle-plugin/issues/679
        // - https://github.com/asciidoctor/asciidoctor-gradle-plugin/issues/731
        exclude("com.burgstaller", "okhttp-digest")
    }
    // TODO: Workaround for:
    // - https://github.com/asciidoctor/asciidoctor-gradle-plugin/issues/679
    // - https://github.com/asciidoctor/asciidoctor-gradle-plugin/issues/731
    implementation("io.github.rburgst", "okhttp-digest", "1.21")
}
