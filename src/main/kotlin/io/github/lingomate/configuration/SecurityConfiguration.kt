package io.github.lingomate.configuration

import io.github.wickedev.spring.reactive.security.DslRoleHierarchy
import io.github.wickedev.spring.reactive.security.decoder.JwtDecoder
import io.github.wickedev.spring.reactive.security.jwt.JwtAuthenticationWebFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository

@Configuration
class SecurityConfiguration {

    @Bean
    fun roleHierarchy(): RoleHierarchy = DslRoleHierarchy {
        "ROLE_ADMIN" {
            "ROLE_MANAGER" {
                +"ROLE_USER"
            }
        }
    }

    @Bean
    fun configure(http: ServerHttpSecurity, jwtDecoder: JwtDecoder): SecurityWebFilterChain {
        http.securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
        return http {
            csrf { disable() }
            httpBasic { disable() }
            formLogin { disable() }
            logout { disable() }
            authorizeExchange {
                authorize("/**", permitAll)
            }
            addFilterAt(JwtAuthenticationWebFilter(jwtDecoder), SecurityWebFiltersOrder.AUTHENTICATION)
        }
    }
}