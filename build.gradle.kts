// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ktlint)
    alias(libs.plugins.android.library) apply false
}

subprojects {
    plugins.withId("org.jetbrains.kotlin.android") {
        apply(plugin = "org.jlleitschuh.gradle.ktlint")

        ktlint {
            version.set("1.6.0")
            outputColorName.set("RED")
        }
    }
}
