plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.android.junit5)
}

android {
    namespace = "${ApplicationInfo.PACKAGE}.data.android"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"http://localhost:8080\"")
        }

        release {
            buildConfigField("String", "BASE_URL", "\"http://localhost:8080\"")

            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = ApplicationInfo.SOURCE_COMPATIBILITY
        targetCompatibility = ApplicationInfo.SOURCE_COMPATIBILITY
    }

    kotlin {
        jvmToolchain(11)
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)

    implementation(platform(libs.retrofit.bom))
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.kotlinx.serialization)

    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    implementation(libs.androidx.datastore.preferences)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.kermit)

    testImplementation(libs.junit5.jupiter)
    testImplementation(libs.mockito.junit.jupiter)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
}
