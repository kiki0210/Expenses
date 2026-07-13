package com.prova.demo.security

import com.prova.demo.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.User as SpringUser
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService(
    private val userRepo: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepo.findByEmail(username)
            .orElseThrow { UsernameNotFoundException("User not found with username: $username") }


        return SpringUser.builder()
            .username(user.email)
            .password(user.password)
            .authorities(emptyList())
            .build()
    }

}