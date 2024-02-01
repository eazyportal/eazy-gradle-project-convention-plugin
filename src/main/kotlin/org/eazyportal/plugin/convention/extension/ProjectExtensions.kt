package org.eazyportal.plugin.convention.extension

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion

fun Project.getJavaVersion(): JavaLanguageVersion =
    JavaLanguageVersion.of(properties["javaVersion"] as String? ?: JavaVersion.VERSION_21.toString())
