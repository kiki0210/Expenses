package com.prova.demo.service

import com.prova.demo.dto.CategoriaRequest
import com.prova.demo.dto.CategoriaResponse
import com.prova.demo.model.Categoria
import com.prova.demo.repository.CategoriaRepository
import com.prova.demo.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class CategoriaService(
    private val repository: CategoriaRepository,
    private val userRepository: UserRepository
) {
    fun getAllByUser(userId: Long): List<CategoriaResponse> = repository.findByUserId(userId).map { it.toResponse() }

    fun create(userId: Long, request: CategoriaRequest): CategoriaResponse {
        val user = userRepository.findById(userId).orElseThrow { NoSuchElementException("User $userId not found") }
        val categoria = Categoria(nome = request.nome, colore = request.colore, user = user)
        return repository.save(categoria).toResponse()
    }

    fun delete(userId: Long, id: Long) {
        val categoria = repository.findByIdAndUserId(id, userId)
            ?: throw NoSuchElementException("Categoria $id non trovata per questo utente")
        repository.delete(categoria)
    }


    private fun Categoria.toResponse() = CategoriaResponse(id, nome, colore)
}