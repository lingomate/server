package io.github.lingomate.entity

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import io.github.lingomate.interfaces.SimpleUserDetails
import io.github.wickedev.graphql.interfases.Node
import io.github.wickedev.graphql.types.ID
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.time.LocalDateTime

@Table("users")
data class User(
    @Id override val id: ID = ID.Empty,
    @GraphQLIgnore val hashSalt: String,

    val email: String,
    val name: String?,
    val roles: List<String>,
    val deletedAt: LocalDateTime,
) : Node, SimpleUserDetails {

    @GraphQLIgnore
    override fun getUsername(): String = id.encoded

    @GraphQLIgnore
    override fun getPassword(): String = hashSalt

    @GraphQLIgnore
    override fun getAuthorities(): Collection<GrantedAuthority> = roles.map { SimpleGrantedAuthority(it) }
}

