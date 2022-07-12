plugins {
    id("jacoco")

    id("java")
}

if (project == project.rootProject) {
    apply {
        plugin("jacoco-report-aggregation")
        plugin("org.eazyportal.plugin.dependency-version-lock-convention")
    }
}

repositories {
    mavenCentral()

    gradlePluginPortal()

    maven {
        name = "github"

        credentials {
            password = "${project.properties["githubPassword"]!!}"
            username = "${project.properties["githubUsername"]!!}"
        }

        url = uri("${project.properties["githubUrl"]!!}/*")
    }

    mavenLocal()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_11.toString()))
    }
}

tasks {
    compileJava {
        options.compilerArgs.add("-Xlint:unchecked")
        options.isDeprecation = true
    }

    test {
        useJUnitPlatform()

        finalizedBy(jacocoTestReport)
    }

    jacocoTestCoverageVerification {
        dependsOn(test)

        classDirectories.setFrom(getFilteredFiles(classDirectories.files, project))
    }

    jacocoTestReport {
        dependsOn(test)

        classDirectories.setFrom(getFilteredFiles(classDirectories.files, project))
    }
}

fun getFilteredFiles(files: Set<File>, project: Project): List<File> = files
    .map {
        project.fileTree(it) {
            exclude(
                "**/config/**",
                "**/exception/**",
                "**/model/**",
                "**/*Configuration.*",
                "**/*Configurer.*",
                "**/*ConfigurerAdapter.*",
                "**/*ErrorHandler.*",
                "**/*Exception.*",
                "**/*ExceptionHandler.*"
            )
        }
    }
    .flatMap { it.files }
    .toList()
