import org.asciidoctor.gradle.jvm.AsciidoctorTask

plugins {
    id("org.asciidoctor.jvm.convert")
    id("org.asciidoctor.jvm.gems")
}

repositories {
    ruby {
        gems()
    }
}

tasks.getByName("asciidoctor", AsciidoctorTask::class) {
    sourceDir(file("src/docs"))
    sources {
        include("index.adoc")
    }
    setOutputDir(file("build/docs"))

    baseDirFollowsSourceDir()
}

dependencies {
    asciidoctorGems("rubygems", "asciidoctor-diagram", "+")
}

asciidoctorj {
    requires("asciidoctor-diagram")

    modules {
        diagram.use()
    }
}
