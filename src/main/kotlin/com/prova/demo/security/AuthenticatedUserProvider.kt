package com.prova.demo.security

import com.prova.demo.repository.HouseholdRepository
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

    fun getCurretHouseholdId(): Long {
        val email = SecurityContextHolder.getContext().authentication?.name ?: ""
        val user = userRepository.findByEmail(email).orElseThrow { IllegalStateException("Utente autenticato non trovato") }
        return user.household?.id ?: throw IllegalStateException("L'utente non appartiene a nessuna casa")
    }
}