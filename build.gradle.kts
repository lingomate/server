import com.palantir.gradle.docker.DockerComposeUp
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

group = "io.github.lingomate"
version = "0.1.0"

val graphQLKotlinVersion = "5.3.1"

plugins {
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

    /* spring */
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-web")

    /* graphql */
    implementation("io.github.wickedev:graphql-jetpack-starter:0.3.8")
    implementation("com.expediagroup:graphql-kotlin-spring-server:$graphQLKotlinVersion")
    implementation("com.expediagroup:graphql-kotlin-spring-client:$graphQLKotlinVersion")
    implementation("com.expediagroup:graphql-kotlin-hooks-provider:$graphQLKotlinVersion")
    implementation("com.graphql-java-kickstart:graphql-spring-boot-starter:12.0.0")

    /* database */
    implementation("io.github.wickedev:spring-data-graphql-r2dbc-starter:0.3.8")
    implementation("io.r2dbc:r2dbc-postgresql")
    implementation("name.nkonev.r2dbc-migrate:r2dbc-migrate-spring-boot-starter:1.8.0")

    /* testing */
    testImplementation("io.kotest:kotest-runner-junit5:5.0.3")
    testImplementation("io.kotest:kotest-assertions-core:5.0.3")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.0")

    /* testing testcontainers */
    testImplementation("org.testcontainers:postgresql:1.16.2")
    testImplementation("org.testcontainers:r2dbc:1.16.2")
    testRuntimeOnly("org.postgresql:postgresql:42.3.1")
}

graphql {
    schema {
        packages = listOf(
            "io.github.lingomate"
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
    useJUnitPlatform {
        includeEngines = setOf("spek2")
    }
}