val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.20"
    id("io.ktor.plugin") version "2.1.3"
}

group = "com.example"
version = "0.0.1"
application {
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.1")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    val jbCode="0.37.3"
    implementation("org.jetbrains.exposed:exposed-core:$jbCode")
    implementation("org.jetbrains.exposed:exposed-dao:$jbCode")
    implementation("org.jetbrains.exposed:exposed-jdbc:$jbCode")
    implementation("org.jetbrains.exposed:exposed-java-time:$jbCode")
    implementation("mysql:mysql-connector-java:8.0.31")
}