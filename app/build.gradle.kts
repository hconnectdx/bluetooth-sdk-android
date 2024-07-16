plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "kr.co.hconnect.polihealth_sdk_android_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "kr.co.hconnect.polihealth_sdk_android_app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField(
            "String", "API_URL",
            "\"https://mapi-stg.health-on.co.kr\""
        )
        buildConfigField(
            "String",
            "CLIENT_ID", "\"3270e7da-55b1-4dd4-abb9-5c71295b849b\""
        )
        buildConfigField(
            "String",
            "CLIENT_SECRET",
            "\"eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJpbmZyYSI6IkhlYWx0aE9uLVN0YWdpbmciLCJjbGllbnQtaWQiOiIzMjcwZTdkYS01NWIxLTRkZDQtYWJiOS01YzcxMjk1Yjg0OWIifQ.u0rBK-2t3l4RZ113EzudZsKb0Us9PEtiPcFDBv--gYdJf9yZJQOpo41XqzbgSdDa6Z1VDrgZXiOkIZOTeeaEYA\""
        )
    }

    buildTypes {
        debug {

        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField("String", "API_URL", "\"https://api.example.com/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildFeatures {
        buildConfig = true
    }


}

dependencies {
    val lifecycle_version = "2.8.3"

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("com.airbnb.android:lottie-compose:6.4.1")

    implementation("kr.co.hconnect:hconnect-permission-lib-android:0.0.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")

    //implementation(project(":polihealth-sdk-android"))
    implementation("kr.co.hconnect:polihealth-sdk-android:0.0.1")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}