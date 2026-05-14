plugins {
    id("com.android.library")
}

android {
    namespace = "com.extexis.core.presentation"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(project(":core:ui"))

    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    testImplementation(libs.junit)
}
