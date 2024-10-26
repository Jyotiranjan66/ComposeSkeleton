import java.text.SimpleDateFormat
import java.util.*

plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization") version "1.4.10"
//    id("com.google.firebase.crashlytics")
}

android {

    defaultConfig {
        val appName = "Android Skeleton"
        val vCode = 1
        val vName = "1.0.0"

        applicationId = "com.skeleton.android"

        minSdk = 23
        compileSdk = 31
        targetSdk = 31

        versionCode = vCode
        versionName = vName

        multiDexEnabled = true
        buildFeatures.dataBinding = true

        resValue("string", "app_name", appName)
        manifestPlaceholders["appName"] = appName
        base.archivesBaseName =
            "${appName}_${vName}(v${vCode})_${SimpleDateFormat("dd-MMM-yyyy").format(Date())}"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions += listOf("version")
    productFlavors {
        create("staging") {
            applicationIdSuffix = ".staging"
            versionNameSuffix = "-staging"
            buildConfigField("String", "BASE_URL", "\"http://abc.xyz/\"")
        }

        create("production") {
            versionNameSuffix = "-production"
            buildConfigField("String", "BASE_URL", "\"http://abcd.xyz/\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}

dependencies {

    implementation(project(mapOf("path" to ":domain")))
    implementation(project(mapOf("path" to ":data")))

    //android
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("androidx.fragment:fragment-ktx:1.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")

    //kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")

    //play services
    implementation("com.google.android.gms:play-services-maps:18.0.1")
    implementation("com.google.android.gms:play-services-location:19.0.0")

    //security
    implementation("androidx.security:security-crypto:1.1.0-alpha03")
    implementation("net.zetetic:android-database-sqlcipher:4.3.0")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:29.0.3"))
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics")

    //media
    implementation("io.coil-kt:coil:0.11.0")
    implementation("com.github.jkwiecien:EasyImage:3.1.0")
    implementation("id.zelory:compressor:3.0.1")

    //api
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //data
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.room:room-ktx:2.4.0")
    kapt("androidx.room:room-compiler:2.4.0")

    //di
    implementation("com.google.dagger:hilt-android:2.38.1")
    kapt("com.google.dagger:hilt-android-compiler:2.38.1")

    //util
    implementation("com.github.smokelaboratory:freedom:2.0.1")
    implementation("com.github.smokelaboratory:whereabouts:1.0.3")
    implementation("com.github.fondesa:recycler-view-divider:3.3.0")

    //testing
    implementation("com.jakewharton.timber:timber:5.0.1")
    testImplementation("junit:junit:4.13.2")
    testImplementation("com.github.IvanShafran:shared-preferences-mock:1.1")
    testImplementation("org.mockito:mockito-core:3.11.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation("androidx.room:room-testing:2.4.0")
    androidTestImplementation("org.hamcrest:hamcrest-integration:4.4.0")
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.4.0")
    androidTestImplementation("com.jakewharton.espresso:okhttp3-idling-resource:")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    /*implementation(libs.bundles.android)
    implementation(libs.bundles.kotlin)
    implementation(libs.bundles.playServices)
    implementation(libs.bundles.firebase)
    implementation(libs.bundles.image)
    implementation(libs.permission)
    implementation(libs.recyclerView.divider)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.security)
    implementation(libs.data.local)

    //room
    implementation(libs.room.core)
    kapt(libs.room.compiler)

    //di
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    //testing
    testImplementation(libs.bundles.testing.core)
    androidTestImplementation(libs.bundles.testing.android)
    implementation(libs.timber)*/
}
