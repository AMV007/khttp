plugins {
    alias(libs.plugins.dokka)
    alias(libs.plugins.gitSemVer)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.multiJvmTesting) apply true
}

repositories {
    google()
    mavenCentral()
}

tasks.jar {
    archiveBaseName.value(project.name)
}

group = "khttp"
version = "1.0.0"

java {
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
}

dependencies {
    api(libs.json)

    testImplementation(kotlin("test"))
    testImplementation(kotlin("reflect"))
    testImplementation(libs.awaitility)
    testImplementation(libs.json)
    testImplementation(libs.spek.dsl.jvm)
    testRuntimeOnly(libs.spek.runner.junit5)
}

tasks {
    withType<Test> {
        useJUnitPlatform {
            includeEngines("spek2")
        }
    }
}

kotlin {

    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}
