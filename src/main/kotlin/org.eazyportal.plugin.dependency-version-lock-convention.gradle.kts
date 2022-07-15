if (project == project.rootProject) {
    subprojects {
        plugins.apply("org.eazyportal.plugin.dependency-version-lock-convention")
    }
}

project.beforeEvaluate {
    buildscript {
        configurations.classpath {
            resolutionStrategy.activateDependencyLocking()
        }
    }
}

dependencyLocking {
    lockAllConfigurations()

    lockMode.set(LockMode.LENIENT)
}

tasks{
    register("lockDependencyVersion") {
        enabled = gradle.startParameter.isWriteDependencyLocks
        group = "eazyportal"

        doLast {
            val ignoreConfigurations = listOf(
                "archives",
                "CodeCoverage",
                "default",
                "jacoco"
            )

            configurations
                .filter { it.isCanBeResolved }
                .filter { configuration ->
                    !ignoreConfigurations.any {
                        configuration.name.contains(it)
                    }
                }
                .forEach { it.resolve() }
        }
    }

    register("unlockDependencyVersion", Delete::class) {
        group = "eazyportal"

        mustRunAfter("lockDependencyVersion")

        delete("gradle.lockfile", "buildscript-gradle.lockfile")
    }
}
