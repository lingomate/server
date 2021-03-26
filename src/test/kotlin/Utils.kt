import com.appmattus.kotlinfixture.decorator.fake.javafaker.javaFakerStrategy
import com.appmattus.kotlinfixture.kotlinFixture
import com.github.javafaker.Faker
import dev.wickedev.voca.coroutine.mono.await
import dev.wickedev.voca.infrastructure.table.DEFAULT_ID_VALUE
import dev.wickedev.voca.infrastructure.table.User
import io.r2dbc.spi.*
import kotlinx.coroutines.*
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.spekframework.spek2.dsl.LifecycleAware
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactory
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

val faker = Faker()
val fixture = kotlinFixture {
    javaFakerStrategy {
        property(User::id) { DEFAULT_ID_VALUE }
    }
}


class DatabaseContainer(spek: LifecycleAware) {
    private var created = false

    private val connectionFactory = ConnectionFactories.get("r2dbc:tc:postgresql:///test?TC_IMAGE_TAG=13")

    private val repositoryFactory by spek.memoized {
        val operations = R2dbcEntityTemplate(connectionFactory)
        R2dbcRepositoryFactory(operations)
    }

    fun create() {
        runBlocking {
            connectionFactory.create().awaitFirstOrNull()
            created = true
        }
    }

    fun destroy() {
        if (connectionFactory is Closeable) {
            runBlocking {
                connectionFactory.close().awaitFirstOrNull()
            }
        }
    }

    fun r2dbcEntityTemplateFacotry(): R2dbcEntityTemplate {
        return R2dbcEntityTemplate(connectionFactory)
    }

    fun <T> getRepository(repositoryInterface: Class<T>): T {
        return repositoryFactory.getRepository(repositoryInterface)
    }

    fun populate(vararg scriptPaths: String) {
        if (!created) {
            create()
        }

        val populator = CompositeDatabasePopulator(
            scriptPaths.map {
                ResourceDatabasePopulator(
                    ClassPathResource(it)
                )
            }
        )
        runBlocking {
            populator.populate(connectionFactory).await()
        }
    }
}
