plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.gplusin"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.gplusin"
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

    buildFeatures {
        viewBinding = true
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


   // implementation("com.github.yesterselga:country-picker-android:2.0")
    // implementation 'com.github.mukeshsolanki:android-otpview-pinview:2.1.0'
    implementation("com.intuit.sdp:sdp-android:1.0.6")
    //implementation 'com.github.aabhasr1:OtpView:v1.1.2'

    implementation("io.github.chaosleung:pinview:1.4.4")

    implementation("de.hdodenhof:circleimageview:3.1.0")

//    implementation 'dyanamitechetan.vusikview:vusikview:1.1'
    implementation("com.google.android.gms:play-services-auth:20.1.0")


//    implementation 'gun0912.ted:tedpermission:2.2.3'
//    implementation 'gun0912.ted:tedbottompicker:2.0.1'
//    implementation("com.iceteck.silicompressorr:silicompressor:2.2.3")
    //  implementation 'com.theartofdev.edmodo:android-image-cropper:2.8.0'

    implementation("com.squareup.retrofit2:retrofit:2.6.1")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("io.reactivex.rxjava2:rxjava:2.2.9")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.6.1")
    implementation("androidx.room:room-rxjava2:2.2.0-rc01")
    annotationProcessor("androidx.room:room-compiler:2.2.0-rc01")
    implementation("com.squareup.retrofit2:converter-scalars:2.0.1")
    implementation("com.squareup.retrofit2:converter-gson:2.6.1")
    implementation("com.squareup.retrofit2:retrofit:2.6.1")
    implementation("com.squareup.retrofit2:converter-gson:2.6.1")
    implementation("com.squareup.okhttp3:logging-interceptor:3.4.1")

    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")

    implementation("com.google.firebase:firebase-storage:19.1.0")

//    implementation("com.github.savvisingh:rangepicker:1.3.0")

    //  implementation 'com.github.adwardstark:materialtextdrawable-for-android:1.0.0'

    //   implementation 'com.amulyakhare:com.amulyakhare.textdrawable:1.0.1'

//    implementation 'com.facebook.android:facebook-login:latest.release'

    implementation("ir.mahozad.android:pie-chart:0.7.0")

    implementation("com.google.firebase:firebase-config:20.0.3")
    implementation("com.google.firebase:firebase-messaging:20.0.0")
    implementation("com.google.firebase:firebase-database:19.0.0")
    implementation("com.google.firebase:firebase-auth:21.0.3")
}