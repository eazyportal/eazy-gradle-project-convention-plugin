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
        includeConfigs.set(
            project.configurations
                .filter { it.isCanBeResolved }
                .map { it.name }
                .filter { it.endsWith("classpath", true) }
        )

        outputFormat.set("json")
    }

    jar {
        manifest {
            attributes["Implementation-Version"] = project.version
        }
    }
}
