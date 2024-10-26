plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {

    defaultConfig {
        minSdk = 23
        compileSdk = 31
        targetSdk = 31
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

    //kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")

    //data
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.room:room-ktx:2.4.0")
    kapt("androidx.room:room-compiler:2.4.0")

    //api
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //security
    implementation("androidx.security:security-crypto:1.1.0-alpha03")
    implementation("net.zetetic:android-database-sqlcipher:4.3.0")

    /*implementation(libs.bundles.kotlin)

    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.security)
    implementation(libs.data.local)

    //room
    implementation(libs.room.core)
    kapt(libs.room.compiler)*/
}
