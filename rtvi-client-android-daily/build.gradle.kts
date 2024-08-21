plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.jetbrains.dokka)
    `maven-publish`
    signing
}

android {
    namespace = "ai.rtvi.client.daily"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
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

    lint {
        targetSdk = 35
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.serialization.json)

    api(libs.daily.android.client)
    api(libs.rtvi.client)

    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation(libs.kotlinx.coroutines.test)
}

publishing {
    repositories {
        maven {
            url = rootProject.layout.buildDirectory.dir("RTVILocalRepo").get().asFile.toURI()
            name = "RTVILocalRepo"
        }
    }

    publications {
        register<MavenPublication>("release") {
            groupId = "ai.rtvi"
            artifactId = "client-daily"
            version = "0.1.3"

            pom {
                name.set("RTVI Client Daily Transport")
                description.set("Daily RTVI client library for Android")
                url.set("https://github.com/rtvi-ai/rtvi-client-android-daily")

                developers {
                    developer {
                        id.set("rtvi.ai")
                        name.set("rtvi.ai")
                    }
                }

                licenses {
                    license {
                        name.set("BSD 2-Clause License")
                        url.set("https://github.com/rtvi-ai/rtvi-client-android-daily/blob/main/LICENSE")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/rtvi-ai/rtvi-client-android-daily.git")
                    developerConnection.set("scm:git:ssh://github.com:rtvi-ai/rtvi-client-android-daily.git")
                    url.set("https://github.com/rtvi-ai/rtvi-client-android-daily")
                }
            }

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}

signing {
    val signingKey = System.getenv("RTVI_GPG_SIGNING_KEY")
    val signingPassphrase = System.getenv("RTVI_GPG_SIGNING_PASSPHRASE")

    if (!signingKey.isNullOrEmpty() || !signingPassphrase.isNullOrEmpty()) {
        useInMemoryPgpKeys(signingKey, signingPassphrase)
        sign(publishing.publications)
    }
}