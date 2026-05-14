plugins {
    id("org.jetbrains.kotlin.jvm")
    alias(libs.plugins.kotlin.serialization)
}

kotlin { jvmToolchain(11) }
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.moshi.kotlin)

    testImplementation(libs.junit)
    testImplementation(libs.okhttp.mockserver)
}
