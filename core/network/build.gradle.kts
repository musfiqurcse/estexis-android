plugins {
    id("com.android.library")
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.estexis.core.network"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://grihoo-prod.up.railway.app/api/\"")
            buildConfigField("Boolean", "IS_DEBUG", "true")
        }

        create("stage") {
            initWith(getByName("debug"))
            buildConfigField("String", "BASE_URL", "\"https://grihoo-prod.up.railway.app/api/\"")
            buildConfigField("Boolean", "IS_DEBUG", "true")
        }

        release {
            buildConfigField("String", "BASE_URL", "\"https://grihoo-prod.up.railway.app/api/\"")
            buildConfigField("Boolean", "IS_DEBUG", "false")
        }
    }
}

dependencies {
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.adapters)
    ksp(libs.moshi.codegen)

    testImplementation(libs.junit)
    testImplementation(libs.okhttp.mockserver)
}
