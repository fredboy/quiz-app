plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
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

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)

    implementation(libs.bundles.coil)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.androidTest)

    testImplementation(platform(libs.koin.bom))
    testImplementation(libs.bundles.test)

    debugImplementation(libs.bundles.debug)
}
