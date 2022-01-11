package io.github.lingomate

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.debug.DebugProbes
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class Application


@OptIn(ExperimentalCoroutinesApi::class)
fun main(args: Array<String>) {
    DebugProbes.enableCreationStackTraces = true
    runApplication<Application>(*args)
}