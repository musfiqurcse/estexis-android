package buildlogic.versions

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.extensions.core.extra
import java.io.File
import java.util.Properties

class VersionManagerPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val versionManager = VersionManager(target)
        target.extra.apply {
            set("versionCode", versionManager.versionCode)
            set("versionName", versionManager.versionName)
        }
    }

}

class VersionManager(val project: Project) {

    private val versionMap = getVersionProperties()

    var versionCode =
        (versionMap["major"] as Int) * 10000 + (versionMap["minor"] as Int) * 1000 + (versionMap["patch"] as Int) + versionMap["rc"] as Int

    var versionName =
        if(isRelease()) "${versionMap["major"]}.${versionMap["minor"]}.${versionMap["patch"]}"
        else "${versionMap["major"]}.${versionMap["minor"]}.${versionMap["patch"]}.rc${versionMap["rc"]}"

    private fun isRelease(): Boolean {
        return true
    }

    private fun getVersionProperties(): Map<String, Int> {
        val map = mutableMapOf<String, Int>()
        val versionProperties = File(project.rootDir, "version.properties").inputStream().use {
            Properties().apply { load(it) }
        }
        map["major"] = (versionProperties.getValue("version.major") as String).toInt()
        map["minor"] = (versionProperties.getValue("version.minor") as String).toInt()
        map["patch"] = (versionProperties.getValue("version.patch") as String).toInt()
        map["rc"] = (versionProperties.getValue("version.rc") as String).toInt()
        return map
    }

}
