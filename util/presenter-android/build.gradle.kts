plugins{
    id("kotlin-android")
    id("com.android.library")
}

ext {
    set("groupId","io.github.privatik")
    set("artifactId","presenter-android")
}

apply(from = rootProject.file("publishing.gradle"))

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 24
        targetSdk = 31
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(project(":util:presenter-common"))
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10"  )
}