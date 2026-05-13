rootProject.name = "build-logic"
//include(":convention")

pluginManagement {
    include(":convention")
    //includeBuild("build-logic")
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") { from(files("../gradle/libs.versions.toml")) }
    }
}
