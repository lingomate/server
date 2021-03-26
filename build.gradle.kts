import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

group = "dev.wickedev.voca"
version = "0.1.0"

val kotlinCoroutineVersion = "1.4.3"
val spekVersion = "2.1.0-alpha.0.27+e76356a"

plugins {
    kotlin("jvm") version "1.4.31"
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
    id("org.springframework.boot") version "2.4.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

ktlint {
    version.set("0.39.0")
    disabledRules.set(
        setOf(
            "no-wildcard-imports",
            "no-consecutive-blank-lines"
        )
    )
    filter {
        exclude { element ->
            element.file.path
                .contains("generated")
        }
        exclude("*.kts")
        include("**/kotlin/**")
    }
}

repositories {
    jcenter()
    maven("https://dl.bintray.com/spekframework/spek-dev/")
    maven("https://github.com/novonetworks/spring-fu/raw/patch-context/maven-repo")
}

dependencies {
    /* kotlin */
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${kotlinCoroutineVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${kotlinCoroutineVersion}")

    /* spring */
    implementation("org.springframework.fu:spring-fu-kofu:0.5.0.7-patch-context")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.security:spring-security-crypto")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    /* database */
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("io.r2dbc:r2dbc-postgresql:0.8.4.RELEASE")
    implementation("org.mariadb:r2dbc-mariadb:1.0.0")
    runtimeOnly("org.postgresql:postgresql")

    /* security */
    implementation("io.jsonwebtoken:jjwt-api:0.11.1")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.1")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.1")
    implementation("org.bouncycastle:bcpkix-jdk15on:1.66")

    /* testing */
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spekVersion")
    testImplementation("com.winterbe:expekt:0.5.0")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spekVersion")
    testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("io.mockk:mockk:1.10.4")

    /* testing testcontainers */
    testImplementation("org.testcontainers:postgresql:1.15.1")
    testImplementation("org.testcontainers:r2dbc:1.15.1")

    /* testing fixture */
    testImplementation("com.appmattus.fixture:fixture:1.0.0")
    testImplementation("com.appmattus.fixture:fixture-generex:1.0.0")
    testImplementation("com.appmattus.fixture:fixture-javafaker:1.0.0")
}

sourceSets {
    main {
        java {
            srcDir("build/generated/source/proto/main/java")
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

tasks.withType<BootRun> {
    systemProperty("spring.profiles.active", "dev")
    systemProperty("spring.devtools.restart.enabled", "true")
    systemProperty("spring.devtools.livereload.enabled", "true")
}

tasks.withType<Test> {
    systemProperty("spring.profiles.active", "test")
    useJUnitPlatform {
        includeEngines = setOf("spek2")
    }
}