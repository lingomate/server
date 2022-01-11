package io.github.lingomate.types

import io.github.wickedev.graphql.types.PageInfo

data class UserConnect(
    val edges: List<UserEdge>,
    val pageInfo: PageInfo
)