import org.eazyportal.plugin.convention.extension.getJavaVersion

plugins {
    id("jacoco")
    id("java-test-fixtures")

    id("java")
    id("jvm-test-suite")
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
        languageVersion.set(project.getJavaVersion())
    }
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
        }
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
