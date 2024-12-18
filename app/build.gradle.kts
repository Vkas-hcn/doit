import com.github.megatronking.stringfog.plugin.StringFogExtension

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("io.objectbox")
    id("com.google.firebase.crashlytics")
    id("com.google.gms.google-services")
    id("stringfog")
}

apply(plugin = "stringfog")

configure<StringFogExtension> {
    implementation = "com.github.megatronking.stringfog.xor.StringFogImpl"
    enable = true
    kg = com.github.megatronking.stringfog.plugin.kg.RandomKeyGenerator()
    mode = com.github.megatronking.stringfog.plugin.StringFogMode.bytes
}

android {
    namespace = "com.todo.tomato"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.todo.tomato"
        minSdk = 24
        targetSdk = 34
        versionCode = 5
        versionName = "1.0.4"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("com.mikhaellopez:circularprogressbar:3.1.0")

    implementation("androidx.fragment:fragment-ktx:1.6.0")
    implementation("io.objectbox:objectbox-kotlin:4.0.2")
    implementation("com.applovin:applovin-sdk:+")
    implementation("com.github.kittinunf.fuel:fuel:3.0.0-alpha03")
    implementation("com.github.megatronking.stringfog:xor:5.0.0")
    implementation("com.google.android.gms:play-services-ads:23.3.0")
    implementation("com.google.android.ump:user-messaging-platform:3.0.0")
    implementation ("com.facebook.android:facebook-android-sdk:12.3.0")
    implementation(platform("com.google.firebase:firebase-bom:33.2.0"))
    implementation ("com.google.firebase:firebase-analytics-ktx")
    implementation ("com.google.firebase:firebase-config-ktx")
    implementation ("com.google.firebase:firebase-crashlytics-ktx")
}