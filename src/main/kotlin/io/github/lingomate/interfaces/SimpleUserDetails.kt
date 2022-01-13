package io.github.lingomate.interfaces

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import io.github.wickedev.graphql.types.ID
import io.github.wickedev.spring.reactive.security.IdentifiableUserDetails
import org.springframework.security.core.userdetails.UserDetails

@GraphQLIgnore
interface SimpleUserDetails : IdentifiableUserDetails{
    @GraphQLIgnore
    override fun isAccountNonExpired(): Boolean = true

    @GraphQLIgnore
    override fun isAccountNonLocked(): Boolean = true

    @GraphQLIgnore
    override fun isCredentialsNonExpired(): Boolean = true

    @GraphQLIgnore
    override fun isEnabled(): Boolean = true
}