package io.github.lingomate.service

import io.github.lingomate.repository.UserRepository
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserService(private val userRepository: UserRepository): ReactiveUserDetailsService {
    override fun findByUsername(email: String): Mono<UserDetails> {
        return userRepository.findByEmail(email)
            .cast(UserDetails::class.java)
    }
}