plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
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
    }
}

dependencies {
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
}