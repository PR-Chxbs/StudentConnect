plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    // ------ PR added ------

    // dagger
    kotlin("kapt")
    alias(libs.plugins.hilt.android)
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
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // ------ PR added ------

    // dagger
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // navigation
    implementation(libs.navigation.compose)

    // hilt
    implementation(libs.hilt.navigation.compose)
}