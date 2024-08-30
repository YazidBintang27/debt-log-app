plugins {
   alias(libs.plugins.android.application)
   alias(libs.plugins.jetbrains.kotlin.android)
   id("com.google.devtools.ksp")
   id("com.google.dagger.hilt.android")
}

android {
   namespace = "com.latihan.debtnote"
   compileSdk = 34

   buildFeatures {
      viewBinding = true
   }

   defaultConfig {
      applicationId = "com.latihan.debtnote"
      minSdk = 21
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
      sourceCompatibility = JavaVersion.VERSION_1_8
      targetCompatibility = JavaVersion.VERSION_1_8
   }
   kotlinOptions {
      jvmTarget = "1.8"
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

   //Room Database
   implementation(libs.androidx.room.runtime)
   ksp(libs.androidx.room.compiler)
   implementation(libs.androidx.room.ktx)

   //Dagger Hilt
   implementation(libs.hilt.android)
   ksp(libs.dagger.compiler) // Dagger compiler
   ksp(libs.hilt.compiler)   // Hilt compiler

   //ViewModel
   implementation(libs.androidx.activity.ktx)

   implementation(libs.androidx.appcompat)
   implementation(libs.material)
}