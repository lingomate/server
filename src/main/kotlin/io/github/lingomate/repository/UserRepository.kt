package io.github.lingomate.repository

import io.github.lingomate.entity.User
import io.github.wickedev.graphql.spring.data.r2dbc.repository.interfaces.GraphQLR2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : GraphQLR2dbcRepository<User>