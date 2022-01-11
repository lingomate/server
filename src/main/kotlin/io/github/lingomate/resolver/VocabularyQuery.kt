package io.github.lingomate.resolver

import com.expediagroup.graphql.server.operations.Query
import io.github.wickedev.graphql.types.Upload
import org.springframework.stereotype.Component

@Component
class VocabularyQuery : Query {
    fun test(upload: Upload): Int = 1
}