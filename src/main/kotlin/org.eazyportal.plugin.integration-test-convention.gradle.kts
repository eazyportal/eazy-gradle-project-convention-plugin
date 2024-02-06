plugins {
    id("java") apply false
    id("jvm-test-suite") apply false
}

sourceSets {
    create("integration-test") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

configurations["integrationTestImplementation"].extendsFrom(configurations.testImplementation.get())
configurations["integrationTestRuntimeOnly"].extendsFrom(configurations.testRuntimeOnly.get())

testing {
    suites {
        register<JvmTestSuite>("integrationTest") {
            group = "verification"

            dependencies {
                implementation(project())
            }

            targets {
                all {
                    testTask.configure {
                        testClassesDirs = sourceSets["integration-test"].output.classesDirs
                        classpath = sourceSets["integration-test"].runtimeClasspath

                        shouldRunAfter("test")
                    }
                }
            }
        }
    }
}

tasks.check {
    dependsOn(testing.suites.named("integrationTest"))
}
