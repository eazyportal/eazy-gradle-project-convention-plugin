plugins {
    id("maven-publish")
}

publishing {
    val isRelease = !version.toString().endsWith("-SNAPSHOT")

    repositories {
        mavenLocal()

        if (isRelease) {
            maven {
                name = "github"
                url = uri("https://maven.pkg.github.com/eazyportal/${project.rootProject.name}")
                credentials(PasswordCredentials::class)
            }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            from(project.components["kotlin"])
        }

        if (isRelease) {
            create<MavenPublication>("github") {
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()

                from(project.components["kotlin"])
            }
        }
    }
}
