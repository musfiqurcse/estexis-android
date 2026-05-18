plugins {
    id("org.jetbrains.kotlin.jvm")
}

kotlin { jvmToolchain(11) }
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(project(":core:common"))
}
