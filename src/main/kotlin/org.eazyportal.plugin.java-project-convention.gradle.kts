plugins {
    id("jacoco")

    id("java")
}

if (project == project.rootProject) {
    apply {
        plugin("jacoco-report-aggregation")
    }
}

repositories {
    mavenCentral()

    gradlePluginPortal()

    maven {
        name = "github"

        credentials(PasswordCredentials::class.java)
        url = uri("${project.properties["githubUrl"]!!}/*")
    }

    mavenLocal()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
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
