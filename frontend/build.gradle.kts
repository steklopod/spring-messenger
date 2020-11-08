
plugins {
	id("org.jetbrains.kotlin.js")
}

repositories {
	mavenCentral()
}

kotlin {
	js {
		browser {
		}
		binaries.executable()
	}
}

dependencies {
	implementation(kotlin("stdlib-js"))
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.4.1")

	implementation(project(":shared"))

	testImplementation(kotlin("test-js"))
}
