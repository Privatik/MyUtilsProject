plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

ext {
    set("groupId","io.github.privatik")
    set("artifactId","machine")
}

apply(from = rootProject.file("publishing.gradle"))

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.6.0")
}