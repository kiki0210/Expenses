package com.prova.demo.service

import com.prova.demo.dto.CategoriaRequest
import com.prova.demo.dto.CategoriaResponse
import com.prova.demo.model.Categoria
import com.prova.demo.repository.CategoriaRepository
import com.prova.demo.repository.HouseholdRepository
import org.springframework.stereotype.Service

@Service
class CategoriaService(
    private val repository: CategoriaRepository,
    private val householdRepository: HouseholdRepository
) {
    fun getAllByHousehold(householdId: Long): List<CategoriaResponse> = repository.findAllByHouseholdId(householdId).map { it.toResponse() }

    fun create(householdId: Long, request: CategoriaRequest): CategoriaResponse {
      if (repository.existsByNomeAndHouseholdId(request.nome,householdId)) {
          throw IllegalArgumentException("Nome ${request.nome} exists in database")
      }
        val categoria = Categoria(nome = request.nome, household =householdRepository.findById(householdId).orElseThrow{
            NoSuchElementException("Nessuna categoria associata")}, colore =request.colore)
        return repository.save(categoria).toResponse()
    }

    fun delete(householdId: Long, id: Long) {
        val categoria = repository.findByIdAndHouseholdId(id, householdId).orElseThrow { NoSuchElementException("Categoria $id non trovata a questa household") }
        repository.delete(categoria)
    }


    private fun Categoria.toResponse() = CategoriaResponse(id, nome, colore)
}