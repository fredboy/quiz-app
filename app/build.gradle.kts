plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.junit5)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = ApplicationInfo.PACKAGE
    compileSdk = 36

    defaultConfig {
        applicationId = ApplicationInfo.PACKAGE
        minSdk = 24
        targetSdk = 36
        versionCode = ApplicationInfo.VERSION_CODE
        versionName = ApplicationInfo.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
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
        compose = true
    }
}

ktlint {
    android.set(true)
}

dependencies {
    implementation(project(":data"))
    implementation(project(":data-android"))
    implementation(project(":domain"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.androidx)
    implementation(libs.bundles.compose)
    implementation(libs.material)

    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    implementation(libs.bundles.coil)

    implementation(libs.kermit)

    testImplementation(libs.junit5.jupiter)
    testImplementation(libs.mockito.junit.jupiter)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
}
