package io.github.lingomate

import io.github.wickedev.graphql.spring.data.r2dbc.configuration.EnableGraphQLR2dbcRepositories
import io.github.wickedev.spring.reactive.security.EnableJwtWebFluxSecurity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.debug.DebugProbes
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity


@SpringBootApplication
@EnableGraphQLR2dbcRepositories
@EnableWebFluxSecurity
@EnableJwtWebFluxSecurity
class Application

@OptIn(ExperimentalCoroutinesApi::class)
fun main(args: Array<String>) {
    DebugProbes.enableCreationStackTraces = true
    runApplication<Application>(*args)
}