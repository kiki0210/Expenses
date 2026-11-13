package com.prova.demo.controller

import com.prova.demo.dto.AuthResponse
import com.prova.demo.dto.LoginRequest
import com.prova.demo.dto.RegistrazioneRequest
import com.prova.demo.model.Household
import com.prova.demo.model.User
import com.prova.demo.repository.HouseholdRepository
import com.prova.demo.repository.UserRepository
import com.prova.demo.security.JwtService
import jakarta.validation.Valid
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userRepository: UserRepository,
    private val householdRepository: HouseholdRepository,
    private val passwordEncoder: PasswordEncoder,
    private val auhtenticationManager: AuthenticationManager,
    private val jwtService: JwtService
) {

    @PostMapping("/registrazione")
    fun registra(@Valid @RequestBody request: RegistrazioneRequest): AuthResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email già esistente")
        }

        val household = when {
            request.newHousehold != null -> {
                if (householdRepository.existsByName(request.newHousehold)) {
                    throw IllegalArgumentException("Una casa con questo nome esiste già")
                }
                val newHousehold = Household(name = request.newHousehold)
                householdRepository.save(newHousehold)
            }
            request.existentHousehold != null -> {
                householdRepository.findByName(request.existentHousehold)
                    .orElseThrow { IllegalArgumentException("Casa non trovata") }
            }
            else -> null
        }

        val user = User(
            email = request.email,
            nome = request.nome,
            password = passwordEncoder.encode(request.password)!!,
            household = household,
            isHouseholdAdmin = household != null && request.newHousehold != null
        )

        val saved = userRepository.save(user)
        val token = jwtService.generaToken(saved.id, saved.email)

        return AuthResponse(token, saved.id, saved.email, saved.nome)
    }

    @PostMapping("/login")
    fun login (@Valid @RequestBody request: LoginRequest): AuthResponse{
        auhtenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.email, request.password))
        val user = userRepository.findByEmail(request.email).orElseThrow{IllegalArgumentException("Email non esistente")}

        val token = jwtService.generaToken(user.id, user.email)
        return AuthResponse(token, user.id, user.email, user.nome)
    }

}