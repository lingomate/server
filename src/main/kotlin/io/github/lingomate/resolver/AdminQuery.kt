package io.github.lingomate.resolver

import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import io.github.lingomate.repository.UserRepository
import io.github.lingomate.types.UserConnect
import io.github.lingomate.types.UserEdge
import io.github.wickedev.graphql.types.Backward
import io.github.wickedev.graphql.types.ID
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class AdminQuery(private val userRepository: UserRepository) : Query {
    fun users(last: Int?, before: ID?, env: DataFetchingEnvironment): CompletableFuture<UserConnect> {
        return userRepository.connection(Backward(last, before), env)
            .thenApply { UserConnect(it.edges.map { e -> UserEdge(e.node, e.cursor) }, it.pageInfo) }
    }
}