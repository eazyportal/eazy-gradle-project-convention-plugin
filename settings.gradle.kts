rootProject.name = "eazyproject-convention-gradle-plugin"

pluginManagement {
    plugins {
        id("org.eazyportal.plugin.release") version (extra["eazyReleasePluginVersion"] as String)
    }

    repositories {
        gradlePluginPortal()

        maven {
            name = "github"
            url = uri("${extra["githubUrl"] as String}/*")

            credentials {
                password = extra["githubPassword"] as String
                username = extra["githubUsername"] as String
            }
        }
    }
}
