plugins {
    id("maven-publish")
}

publishing {
    if (project.version.toString().endsWith("-SNAPSHOT")) {
        repositories {
            mavenLocal()
        }

        // publishing to mavenLocal needs to have a publication configured
        publications {
            create<MavenPublication>("maven") {
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()

                from(project.components["java"])
            }
        }
    }
    else { // release
        repositories {
            maven {
                name = "github"

                credentials(PasswordCredentials::class)
                url = uri("${project.properties["githubUrl"]!!}/${project.rootProject.name}")
            }
        }
    }
}
