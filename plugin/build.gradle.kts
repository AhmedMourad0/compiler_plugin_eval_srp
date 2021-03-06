import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

group = "dev.ahmedmourad"
version = "0.0.1-SNAPSHOT"

val jvmTargetVersion: String by project
val arrowMetaVersion: String by project

dependencies {
    compileOnly(kotlin("stdlib"))
    compileOnly(kotlin("compiler-embeddable"))
    compileOnly("io.arrow-kt:compiler-plugin:$arrowMetaVersion")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = jvmTargetVersion
        }
    }
    // Create a new JAR with: Arrow Meta + new plugin
    register<org.gradle.jvm.tasks.Jar>("createNewPlugin") {
        dependsOn(classes)
        archiveClassifier.set("all")
        from("build/classes/kotlin/main")
        from("build/resources/main")
        from(sourceSets.main.get().compileClasspath.find { it.absolutePath.contains("io.arrow-kt\\compiler-plugin") }!!.let(::zipTree)) {
            exclude("META-INF/services/org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar")
        }
    }
}
