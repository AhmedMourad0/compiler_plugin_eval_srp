rootProject.name = "compiler_plugin_eval"

pluginManagement {
    val kotlinVersion: String by settings
    val shadowJarVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
        id("com.github.johnrengelman.shadow") version shadowJarVersion apply false
    }
}
include("plugin")
include("sample")
