package io.github.lingomate.configuration

import io.r2dbc.spi.ConnectionFactory
import name.nkonev.r2dbc.migrate.autoconfigure.R2dbcMigrateAutoConfiguration
import name.nkonev.r2dbc.migrate.autoconfigure.R2dbcMigrateAutoConfiguration.SpringBootR2dbcMigrateProperties
import name.nkonev.r2dbc.migrate.core.SqlQueries
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.sql.init.SqlR2dbcScriptDatabaseInitializer
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SpringBootR2dbcMigrateProperties::class)
class DatabaseConfiguration {

    @Bean(name = ["ar2dbcMigrate"], initMethod = "migrate")
    fun r2dbcMigrate(
        connectionFactory: ConnectionFactory,
        properties: SpringBootR2dbcMigrateProperties,
        @Autowired(required = false) maybeUserDialect: SqlQueries?,
        @Autowired(required = false) initializer: SqlR2dbcScriptDatabaseInitializer?
    ): R2dbcMigrateAutoConfiguration.R2dbcMigrateBlockingInvoker {
        return R2dbcMigrateAutoConfiguration.R2dbcMigrateBlockingInvoker(
            connectionFactory,
            properties,
            maybeUserDialect
        )
    }
}
