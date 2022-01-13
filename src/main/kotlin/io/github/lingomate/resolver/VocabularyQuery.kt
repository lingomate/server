package io.github.lingomate.resolver

import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import io.github.wickedev.graphql.types.Upload
import org.springframework.stereotype.Component

@Component
class VocabularyQuery : Query {
    fun test(upload: Upload, env: DataFetchingEnvironment): Int = env.getRoot()
}