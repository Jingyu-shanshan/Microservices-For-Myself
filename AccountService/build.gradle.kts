import org.springframework.boot.gradle.tasks.run.BootRun

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.flywaydb:flyway-mysql:9.21.1")
    }
}

plugins {
    id("java")
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.2"
    id("org.flywaydb.flyway") version "9.21.1"
    id("io.github.ximtech.jasypt-encrypt-plugin") version "1.3.3"
}

apply(
        plugin = "io.spring.dependency-management"
)

group = "com.microservices"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // spring boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security:3.2.3")

    // mysql
    implementation("mysql:mysql-connector-java:8.0.31")
    implementation("org.flywaydb:flyway-core:9.21.1")
    implementation("org.flywaydb:flyway-mysql:9.21.1")
    implementation("org.hibernate.orm:hibernate-core:6.4.1.Final")
    testImplementation("com.h2database:h2:2.2.224")

    // unit test
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // utilities
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("org.modelmapper:modelmapper:3.1.1")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.4")

    // jwt token
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")
}

// remove SNAPSHOT-PLAIN.jar
tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks.test {
    useJUnitPlatform()
}

tasks.getByName<BootRun>("bootRun").dependsOn("flywayMigrate")