plugins {
    id("org.eazyportal.plugin.java-project-convention")
    id("org.eazyportal.plugin.publish-convention")

    id("java-library")
    id("org.cyclonedx.bom")
}

java {
    withJavadocJar()
    withSourcesJar()
}

tasks {
    check {
        finalizedBy(cyclonedxBom)
    }

    cyclonedxBom {
        skipConfigs.set(
            project.configurations
                .filter { !it.isVisible || !it.isCanBeResolved }
                .map { it.name }
        )
    }

    jar {
        manifest {
            attributes["Implementation-Version"] = project.version
        }
    }
}
