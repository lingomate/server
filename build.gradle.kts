import com.palantir.gradle.docker.DockerComposeUp
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

group = "io.github.lingomate"
version = "0.1.0"

val graphQLKotlinVersion = "5.3.1"

plugins {
    java
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.kotlin.plugin.spring") version "1.6.10"
    id("org.springframework.boot") version "2.6.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.palantir.docker-compose") version "0.28.0"
    id("com.expediagroup.graphql") version "5.3.1"
}

repositories {
    mavenCentral()
    maven("https://github.com/wickedev/graphql-jetpack/raw/deploy/maven-repo")
    maven("https://github.com/wickedev/spring-security-jwt-webflux/raw/deploy/maven-repo")
}

dependencies {
    graphqlSDL(project(":"))

    /* kotlin */
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("com.expediagroup:graphql-kotlin-spring-server:5.3.1")

    /* spring */
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    /* graphql */
    implementation("io.github.wickedev:graphql-jetpack-starter:0.4.1")
    implementation("com.expediagroup:graphql-kotlin-spring-server:$graphQLKotlinVersion")
    implementation("com.expediagroup:graphql-kotlin-spring-client:$graphQLKotlinVersion")
    implementation("com.expediagroup:graphql-kotlin-hooks-provider:$graphQLKotlinVersion")

    /* database */
    implementation("io.github.wickedev:spring-data-graphql-r2dbc-starter:0.4.1")
    implementation("io.r2dbc:r2dbc-postgresql")
    implementation("name.nkonev.r2dbc-migrate:r2dbc-migrate-spring-boot-starter:1.8.1")

    /* security */
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.github.wickedev:spring-security-jwt-webflux-starter:0.1.5")

    /* testing */
    testImplementation("io.kotest:kotest-runner-junit5:5.0.3")
    testImplementation("io.kotest:kotest-assertions-core:5.0.3")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.0")

    /* testing testcontainers */
    testImplementation("org.testcontainers:postgresql:1.16.2")
    testImplementation("org.testcontainers:r2dbc:1.16.2")
    testRuntimeOnly("org.postgresql:postgresql:42.3.1")

    /* testing fixture */
    testImplementation("com.appmattus.fixture:fixture:1.2.0")
    testImplementation("com.appmattus.fixture:fixture-generex:1.2.0")
    testImplementation("com.appmattus.fixture:fixture-javafaker:1.2.0")

    /* testing spring */
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(module = "mockito-core")
    }
}

graphql {
    schema {
        packages = listOf(
            "io.github.lingomate",
            "io.github.wickedev.graphql"
        )
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn", "-Xinline-classes")
    }
}

tasks.withType<BootRun> {
    dependsOn(tasks.withType<DockerComposeUp>())
    systemProperty("spring.profiles.active", "dev")
}

tasks.withType<Test> {
    systemProperty("spring.profiles.active", "test")
    useJUnitPlatform()
}