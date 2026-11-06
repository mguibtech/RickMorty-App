plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id("dev.detekt")
}

detekt {
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    autoCorrect = true
}

tasks.named("compileKotlin") {
    dependsOn("detekt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}
