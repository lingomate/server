package dev.wickedev.voca.infrastructure.table

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class User(
    @Id val id: Identifier = DEFAULT_ID_VALUE,
    val username: String,
    val hashSalt: String,
)
