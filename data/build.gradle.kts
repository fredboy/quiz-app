plugins {
    kotlin("jvm")
    `java-library`
    alias(libs.plugins.kotlinx.serialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    jvmToolchain(11)
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(project(":domain"))

    implementation(platform(libs.koin.bom))
    implementation(libs.koin)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.kermit)

    testImplementation(libs.junit5.jupiter)
    testImplementation(libs.mockito.junit.jupiter)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
}
