plugins {
    java
    id("org.springframework.boot") version "3.1.3"
}
apply(plugin = "io.spring.dependency-management")

group = "com.lorenzoconsultores"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.assertj:assertj-core:3.11.1")
    testImplementation("org.mockito:mockito-core:3.+")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}