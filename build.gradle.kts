import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}

dependencies {
    val implementation by configurations
    implementation("androidx.recyclerview:recyclerview:1.3.2")
}
