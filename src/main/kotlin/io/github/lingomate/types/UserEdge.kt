package io.github.lingomate.types

import io.github.lingomate.entity.User
import io.github.wickedev.graphql.types.ConnectionCursor

data class UserEdge(
    val node: User,
    val cursor: ConnectionCursor
)