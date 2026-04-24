plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
<<<<<<< HEAD
}

android {
    namespace = "com.example.pharmaconnect"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pharmaconnect"
        minSdk = 30
        targetSdk = 34
    }

    buildFeatures {
        viewBinding = true
        compose = true
        dataBinding = false
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
=======
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "youcef.pharmacie"
    compileSdk = 34

    defaultConfig {
        applicationId = "youcef.pharmacie"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
>>>>>>> e808d67db433f496446bc0314bb2fe92d0ebd014
    }
}

dependencies {
<<<<<<< HEAD
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(platform(libs.androidx.compose.bom))

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    implementation("org.osmdroid:osmdroid-android:6.1.18")
    implementation(libs.play.services.analytics.impl)

    debugImplementation("androidx.compose.ui:ui-tooling")
=======

    // 🔥 Core (stable)
    implementation("androidx.core:core-ktx:1.12.0")

    // 🔥 Compose BOM (stable compatible with SDK 34)
    implementation(platform("androidx.compose:compose-bom:2024.10.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.10.00"))

    // Compose UI
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Lifecycle + Activity
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.9.0")

    // Tests Compose
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Material (XML UI)
    implementation("com.google.android.material:material:1.11.0")

    // Network
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // Maps
    implementation("org.osmdroid:osmdroid-android:6.1.17")
>>>>>>> e808d67db433f496446bc0314bb2fe92d0ebd014
}