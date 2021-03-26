package dev.wickedev.voca.infrastructure.repository

import dev.wickedev.voca.infrastructure.table.Identifier
import dev.wickedev.voca.infrastructure.table.User
import org.springframework.data.repository.reactive.ReactiveSortingRepository

interface UserRepository : ReactiveSortingRepository<User, Identifier>
