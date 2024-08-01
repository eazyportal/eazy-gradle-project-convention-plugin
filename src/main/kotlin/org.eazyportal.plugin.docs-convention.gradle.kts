import org.asciidoctor.gradle.jvm.AsciidoctorTask

plugins {
    id("org.asciidoctor.jvm.convert")
    id("org.asciidoctor.jvm.gems")
}

repositories {
    // TODO: Workaround for
    // - https://github.com/asciidoctor/asciidoctor-gradle-plugin/issues/679
    // - https://github.com/asciidoctor/asciidoctor-gradle-plugin/issues/731
    maven {
        url = uri("https://archiva-repository.apache.org/archiva/repository/public/")
    }

    mavenCentral()

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
