import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
//    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    `maven-publish`
}

val projectProps = Properties()
projectProps.load(FileInputStream(project.file("project.properties")))

val projectName = projectProps.getProperty("name")
val projectTitle = projectProps.getProperty("title")
val projectVersion = projectProps.getProperty("version")
val projectGroupId = projectProps.getProperty("publication_group_id")
val projectArtifactId = projectProps.getProperty("publication_artifact_id")

val githubUrl = projectProps.getProperty("github_url")
val githubUsername = projectProps.getProperty("github_user_name")
val githubAccessToken = projectProps.getProperty("github_access_token")

android {
    namespace = "kr.co.hconnect.polihealth_sdk_android"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    val ktor_version: String by project
    val serialization_version: String by project
    val logback_version: String by project
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
//<<<<<<< feature/publish_githubpackages
//    implementation("kr.co.hconnect:hconnect-bluetooth-lib-android:0.0.1")
//=======
    implementation(files("../../bluetoothlib_android/bluetoothlib/build/outputs/aar/bluetoothlib-release.aar"))
//>>>>>>> develop
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("org.slf4j:slf4j-simple:1.7.30")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization_version")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}


afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                groupId = projectGroupId
                artifactId = projectArtifactId
                version = projectVersion
                pom.packaging = "aar"
                artifact("$buildDir/outputs/aar/polihealth-sdk-android-debug.aar")

                pom.withXml {
                    val dependenciesNode = asNode().appendNode("dependencies")
                    configurations.implementation.get().allDependencies.forEach { dependency ->
                        if (!dependency.group!!.startsWith("androidx")) {
                            val dependencyNode = dependenciesNode.appendNode("dependency")
                            dependencyNode.appendNode("groupId", dependency.group)
                            dependencyNode.appendNode("artifactId", dependency.name)
                            dependencyNode.appendNode("version", dependency.version)
                        }
                    }
                }
            }
        }

        repositories {
            maven {
                name = "GitHubPackages"
                url = uri(githubUrl)
                credentials {
                    username = githubUsername
                    password = githubAccessToken
                }
            }
        }
    }
}