package io.github.lingomate

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@AutoConfigureBefore(R2dbcAutoConfiguration::class)
class TestingDatabaseConfiguration {

    @Bean
    fun connectionFactory(): ConnectionFactory =
        ConnectionFactories.get("r2dbc:tc:postgresql:///test?TC_IMAGE_TAG=9.6.23")
}
