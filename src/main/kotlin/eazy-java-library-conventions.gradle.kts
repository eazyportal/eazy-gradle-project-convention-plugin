plugins {
    id("eazy-java-conventions")
    id("eazy-publish-conventions")

    id("java-library")
    id("org.cyclonedx.bom")
}

tasks {
    check {
        finalizedBy(cyclonedxBom)
    }

    jar {
        manifest {
            attributes["Implementation-Version"] = project.version
        }
    }
}
