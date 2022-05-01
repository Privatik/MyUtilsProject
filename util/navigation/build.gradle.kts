
plugins{
    id("kotlin-android")
    id("com.android.library")
}

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

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10"  )

    api("io.github.alexgladkov:odyssey-core:1.0.0-beta03")
    api("io.github.alexgladkov:odyssey-compose:1.0.0-beta03")

}