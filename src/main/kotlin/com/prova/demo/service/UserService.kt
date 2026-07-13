package com.prova.demo.service


import com.prova.demo.dto.RegistrazioneRequest
import com.prova.demo.dto.UserResponse
import com.prova.demo.model.User
import com.prova.demo.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val repository: UserRepository) {

    fun registra(request: RegistrazioneRequest): UserResponse {
        if (repository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email già registrata")
        }
        val user = User(email = request.email, nome = request.nome, password = request.password)
        return repository.save(user).toResponse()
    }

    fun getById(id: Long): UserResponse =
        repository.findById(id).orElseThrow { NoSuchElementException("Utente $id non trovato") }.toResponse()

    private fun User.toResponse() = UserResponse(id, email, nome)
}