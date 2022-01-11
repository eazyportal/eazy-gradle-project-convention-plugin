plugins {
    id("eazy-kotlin-conventions")

    id("java-library")
    id("maven-publish")
    id("org.cyclonedx.bom")
}

publishing {
    repositories {
        mavenLocal()
    }

    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            from(project.components["kotlin"])
        }
    }
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
