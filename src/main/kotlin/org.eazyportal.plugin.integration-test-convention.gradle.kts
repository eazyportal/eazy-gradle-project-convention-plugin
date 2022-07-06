plugins {
    id("java") apply false
}

sourceSets {
    create("integration-test") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

configurations["integrationTestImplementation"].extendsFrom(configurations.implementation.get())
configurations["integrationTestRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())

val integrationTest = task<Test>("integrationTest") {
    group = "verification"

    testClassesDirs = sourceSets["integration-test"].output.classesDirs
    classpath = sourceSets["integration-test"].runtimeClasspath

    shouldRunAfter("test")

    useJUnitPlatform()
}

tasks.check {
    dependsOn(integrationTest)
}
