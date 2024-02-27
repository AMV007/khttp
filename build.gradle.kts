plugins {
    alias(libs.plugins.dokka)
    alias(libs.plugins.gitSemVer)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.multiJvmTesting)
    alias(libs.plugins.publishOnCentral)
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

kotlin {
    //explicitApi()
}

java {
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
}

dependencies {
    api(kotlin("stdlib"))
    api(libs.json)
    testImplementation(kotlin("test"))
    testImplementation(kotlin("reflect"))
    testImplementation(libs.awaitility)
    testImplementation(libs.json)
    testImplementation(libs.spek.dsl.jvm)
    testRuntimeOnly(libs.spek.runner.junit5)
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}
