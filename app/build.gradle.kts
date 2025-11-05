plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.prince.studentconnect"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.prince.studentconnect"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.material)

    // ------ PR added ------

    // Retrofit 2
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // OkHttp
    implementation(libs.okhttp)

    // Supabase
    implementation(libs.supabase.gotrue)
    implementation(libs.supabase.postgrest)
    implementation(libs.supabase.storage)

    // Ktor
    implementation(libs.ktor.client.okhttp)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Icons
    implementation(libs.material.icons.extended)

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.core)

    // Moshi
    implementation(libs.moshi.core)
    implementation(libs.moshi.kotlin)

    // Coil
    implementation(libs.coil.core)     // Core Coil dependency
    implementation(libs.coil.compose)

    implementation(libs.androidx.datastore.preferences)
}