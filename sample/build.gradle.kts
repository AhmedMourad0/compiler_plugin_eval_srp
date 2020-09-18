import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
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
    kotlinCompilerClasspath("org.jetbrains.kotlin:kotlin-script-util:1.3.61") {
        exclude("org.jetbrains.kotlin", "kotlin-stdlib")
        exclude("org.jetbrains.kotlin", "kotlin-compiler")
        exclude("org.jetbrains.kotlin", "kotlin-compiler-embeddable")
    }
    kotlinCompilerClasspath("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.3.61")
    kotlinCompilerClasspath("org.jetbrains.kotlin:kotlin-scripting-compiler-embeddable:1.3.61")

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
    compileKotlin.dependsOn(":plugin:createNewPlugin")
    compileTestKotlin.kotlinOptions {
        jvmTarget = jvmTargetVersion
    }
    jar {
        manifest {
            attributes("Main-Class" to "dev.ahmedmourad.validation.sample.MainKt")
        }
    }
}
