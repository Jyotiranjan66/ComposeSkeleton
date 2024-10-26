import java.text.SimpleDateFormat
import java.util.*

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {

    defaultConfig {
        val appName = "Android Skeleton"
        val vCode = 1
        val vName = "1.0.0"

        applicationId = "com.yudiz.testing"

        minSdk = 23
        compileSdk = 34
        targetSdk = 34

        versionCode = vCode
        versionName = vName

        multiDexEnabled = true
        buildFeatures.dataBinding = true

        resValue("string", "app_name", appName)
        manifestPlaceholders["appName"] = appName
        base.archivesBaseName =
            "${appName}_${vName}(v${vCode})_${SimpleDateFormat("dd-MMM-yyyy").format(Date())}"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }
//        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.yudiz.testingdemo.api.TestRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kapt {
        correctErrorTypes = true
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}

dependencies {

    //android
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.6.0")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    //kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.20")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")

    //api
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

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

    //data
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    /*implementation(libs.bundles.android)
    implementation(libs.bundles.kotlin)
    implementation(libs.bundles.retrofit)

    //room
    implementation(libs.room.core)
    kapt(libs.room.compiler)

    //testing
    testImplementation(libs.bundles.testing.core)
    androidTestImplementation(libs.bundles.testing.android)
    implementation(libs.timber)*/
}