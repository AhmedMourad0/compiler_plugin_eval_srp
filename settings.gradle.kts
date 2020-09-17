rootProject.name = "compiler_plugin_eval"

pluginManagement {
    val kotlinVersion: String by settings
    val shadowJarVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
    }
}
include("plugin")
include("sample")
