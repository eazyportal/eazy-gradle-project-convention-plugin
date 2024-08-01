plugins {
    id("java-library")

    id("org.eazyportal.plugin.java-project-convention")
    id("org.eazyportal.plugin.publish-convention")
}

java {
    withJavadocJar()
    withSourcesJar()
}

tasks {
    jar {
        manifest {
            attributes["Implementation-Version"] = project.version
        }
    }
}
