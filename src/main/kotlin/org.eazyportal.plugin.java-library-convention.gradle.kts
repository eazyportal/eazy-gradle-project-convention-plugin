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
    getByName("publish").dependsOn("cyclonedxBom")

    cyclonedxBom {
        dependsOn("jar", "build")
        mustRunAfter("check")

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
