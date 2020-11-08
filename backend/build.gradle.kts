import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.spring")
}


repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-rsocket")
    implementation("org.springframework.boot:spring-boot-starter-mustache")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("org.springframework.boot", "spring-boot-starter-data-r2dbc")
    implementation("io.r2dbc:r2dbc-h2")
//	runtimeOnly ("io.r2dbc:r2dbc-postgresql")
//	runtimeOnly ("org.postgresql:postgresql")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    implementation(project(":shared"))

    testImplementation("org.springframework.boot", "spring-boot-starter-test")
    testImplementation("io.projectreactor", "reactor-test")

//    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.4.20-RC")
//    developmentOnly("org.springframework.boot:spring-boot-devtools")
}

tasks {
    test { useJUnitPlatform() }

    withType<KotlinCompile> {
        kotlinOptions { freeCompilerArgs = listOf("-Xjsr305=strict"); jvmTarget = "11" }
        sourceCompatibility = "11"; targetCompatibility = "11"
    }

    processResources {
        dependsOn(":frontend:browserWebpack")
        from(project(":frontend").projectDir.resolve("build/kotlin-js-min/main/")) {
            include("*")
            into("static")
        }
    }
}
