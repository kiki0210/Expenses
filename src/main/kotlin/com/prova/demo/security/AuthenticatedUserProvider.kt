package com.prova.demo.security

import com.prova.demo.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component


@Component
class AuthenticatedUserProvider(private val userRepository: UserRepository) {

    fun getCurrentUserId(): Long {
        val email = SecurityContextHolder.getContext().authentication?.name ?: ""
        return userRepository.findByEmail(email)
            .orElseThrow { IllegalStateException("Utente autenticato non trovato") }.id
    }
}