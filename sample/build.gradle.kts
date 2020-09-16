import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")
}

group = "dev.ahmedmourad"
version = "0.0.1-SNAPSHOT"

val evalVersion: String by project
val arrowVersion: String by project
val jvmTargetVersion: String by project
val kotlinTestVersion: String by project

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("io.arrow-kt:arrow-annotations:$arrowVersion")

    api(project(path = ":plugin", configuration = "shadow"))

    testImplementation("io.kotlintest:kotlintest-runner-junit4:$kotlinTestVersion")
}

tasks {
    val compileKotlin: KotlinCompile by this
    val compileTestKotlin: KotlinCompile by this
    compileKotlin.kotlinOptions {
        jvmTarget = jvmTargetVersion
        freeCompilerArgs =
            listOf("-Xplugin=${project.rootDir}/plugin/build/libs/plugin-$evalVersion-all.jar")
    }
    compileTestKotlin.kotlinOptions {
        jvmTarget = jvmTargetVersion
    }
    named<ShadowJar>("shadowJar") {
        configurations = listOf(project.configurations.compileOnly.get())
    }
    jar {
        manifest {
            attributes("Main-Class" to "dev.ahmedmourad.validation.sample.MainKt")
        }
    }
}
