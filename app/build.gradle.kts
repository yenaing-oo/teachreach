plugins {
    id("com.android.application")
}

android {
    namespace = "comp3350.teachreach"
    compileSdk = 34

    defaultConfig {
        applicationId = "comp3350.teachreach"
        minSdk = 33
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_9
        targetCompatibility = JavaVersion.VERSION_1_9
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(files("libs/bcrypt-0.10.2.jar"))
    implementation(files("libs/bytes-1.6.1.jar"))
    implementation("org.hsqldb:hsqldb:2.4.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("com.jakewharton.threetenabp:threetenabp:1.3.1")
    implementation("com.google.code.gson:gson:2.10")
    testImplementation("junit:junit:4.13.2")
    testImplementation("com.google.guava:guava:25.1-jre")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}