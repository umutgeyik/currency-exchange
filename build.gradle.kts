import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.7"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

group = "com.currency"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("javax.persistence:javax.persistence-api:2.2")
	implementation("javax.validation:validation-api:2.0.0.Final")
	implementation("org.springframework.data:spring-data-jpa:2.6.1")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.3")
	implementation("org.postgresql:postgresql:42.2.0")
	implementation("org.apache.commons:commons-csv:1.5")
	implementation("org.springdoc:springdoc-openapi-data-rest:1.6.7")
	implementation("org.springdoc:springdoc-openapi-ui:1.6.7")
	implementation("org.springdoc:springdoc-openapi-kotlin:1.6.7")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.testcontainers:testcontainers:1.17.1")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
