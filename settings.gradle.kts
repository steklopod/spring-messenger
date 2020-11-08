rootProject.name = "spring-messenger"

include("backend", "frontend", "bot", "shared")

pluginManagement {
	val springBootVersion = "2.3.5.RELEASE"
	repositories {
		gradlePluginPortal()
	}
	resolutionStrategy {
		eachPlugin {
			if (requested.id.id == "org.springframework.boot") {
				useModule("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
			}
		}
	}
	plugins {
		val kotlinVersion = "1.4.20-RC"
		id("org.jetbrains.kotlin.js") version kotlinVersion apply false
		id("org.jetbrains.kotlin.multiplatform") version kotlinVersion  apply false
		id("org.jetbrains.kotlin.jvm") version kotlinVersion apply false
		id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion apply false
		id("org.springframework.boot") version springBootVersion apply false
		id("io.spring.dependency-management") version "1.0.10.RELEASE" apply false
	}
}
