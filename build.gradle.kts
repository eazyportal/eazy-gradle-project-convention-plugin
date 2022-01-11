plugins {
    `kotlin-dsl`
    id("maven-publish")
}

repositories {
    gradlePluginPortal()
}

publishing {
    val isRelease = !version.toString().endsWith("-SNAPSHOT")

    repositories {
        mavenLocal()

        if (isRelease) {
            maven {
                name = "github"
                url = uri("https://maven.pkg.github.com/eazyportal/eazy-gradle-project-convention-plugin")
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

dependencies {
    implementation("org.cyclonedx.bom", "org.cyclonedx.bom.gradle.plugin", "+")
    implementation("org.jetbrains.kotlin", "kotlin-gradle-plugin")
}
