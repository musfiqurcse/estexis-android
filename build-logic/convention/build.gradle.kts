plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(libs.gradle)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "travelplanner.android.application"
            implementationClass = "buildlogic.modules.AndroidApplicationConventionPlugin"
        }
        register("version-manager") {
            id = "travelplanner.version.manager"
            implementationClass = "buildlogic.versions.VersionManagerPlugin"
        }
    }
}