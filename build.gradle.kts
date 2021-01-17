/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm").version("1.3.72")
    application
}

repositories {
    jcenter()
}

tasks.withType(KotlinCompile::class.java).all {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}


dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core","1.4.2")
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core-jvm", "1.4.2")
}


application {
    mainClassName = "com.rogue1.kotlin.walkthrough.App"
    applicationDefaultJvmArgs = listOf("-Dkotlinx.coroutines.debug", "-ea")
}
