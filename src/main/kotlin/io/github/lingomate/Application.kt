package io.github.lingomate

import io.github.wickedev.graphql.spring.data.r2dbc.configuration.EnableGraphQLR2dbcRepositories
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.debug.DebugProbes
import name.nkonev.r2dbc.migrate.autoconfigure.R2dbcMigrateAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication(exclude = [R2dbcMigrateAutoConfiguration::class])
@EnableGraphQLR2dbcRepositories
class Application


@OptIn(ExperimentalCoroutinesApi::class)
fun main(args: Array<String>) {
    DebugProbes.enableCreationStackTraces = true
    runApplication<Application>(*args)
}