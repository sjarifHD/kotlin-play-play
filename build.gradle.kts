import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    kotlin("jvm") version "1.4.0"
    kotlin("kapt") version "1.4.0"
    application
}
group = "me.sjarifhd"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
//    maven { url = uri("https://kotlin.bintray.com/kotlinx") }
}
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.0")

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.9.3")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.9.3")
    implementation("com.daveanthonythomas.moshipack:moshipack:1.0.1")

    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
}
tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "11"
}
application {
    mainClassName = "MainKt"
}