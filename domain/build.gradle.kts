plugins {
    kotlin("jvm")
    `java-library`
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    jvmToolchain(11)
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.koin)

    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.kermit)
}
