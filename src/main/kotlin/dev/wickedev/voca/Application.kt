package dev.wickedev.voca

import dev.wickedev.voca.infrastructure.configuration.enableR2dbcRepositories
import org.springframework.fu.kofu.r2dbc.dataR2dbc
import org.springframework.fu.kofu.reactiveWebApplication
import org.springframework.fu.kofu.webflux.webFlux

val app = reactiveWebApplication {
    webFlux {}
    dataR2dbc {
        enableR2dbcRepositories()
    }
}

fun main(args: Array<String>) {
    app.run(args)
}
