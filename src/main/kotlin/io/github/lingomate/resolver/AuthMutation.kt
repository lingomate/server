package io.github.lingomate.resolver


import com.expediagroup.graphql.server.operations.Mutation
import graphql.schema.DataFetchingEnvironment
import io.github.lingomate.entity.User
import io.github.lingomate.exception.UserAlreadyExistException
import io.github.lingomate.repository.UserRepository
import io.github.wickedev.coroutine.reactive.extensions.mono.await
import io.github.wickedev.graphql.exceptions.ApolloError
import io.github.wickedev.spring.reactive.security.jwt.AuthResponse
import io.github.wickedev.spring.reactive.security.jwt.ReactiveJwtAuthenticationService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AuthMutation(
    private val jwtAuthenticationService: ReactiveJwtAuthenticationService,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) : Mutation {

    suspend fun signUp(email: String, password: String, name: String?, env: DataFetchingEnvironment): User {
        val exists = userRepository.existsByEmail(email).await()

        if (exists) {
            throw UserAlreadyExistException(env)
        }

        return userRepository.save(
            User(
                email = email,
                name = name,
                hashSalt = passwordEncoder.encode(password),
                roles = listOf()
            )
        ).await()
    }

    suspend fun login(email: String, password: String, env: DataFetchingEnvironment): AuthResponse {
        val path = env.executionStepInfo.path
        val sourceLocation = env.field.sourceLocation
        return jwtAuthenticationService.signIn(email, password).await()
            ?: throw ApolloError.AuthenticationError("invalid email or password", path, sourceLocation)
    }

    suspend fun refresh(token: String, env: DataFetchingEnvironment): AuthResponse {
        val path = env.executionStepInfo.path
        val sourceLocation = env.field.sourceLocation
        return jwtAuthenticationService.refresh(token).await()
            ?: throw ApolloError.AuthenticationError("invalid refresh token", path, sourceLocation)
    }
}
