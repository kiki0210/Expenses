package com.prova.demo.repository

import com.prova.demo.model.Categoria
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CategoriaRepository : JpaRepository<Categoria, Long> {
    fun findAllByHouseholdId(householdId: Long): List<Categoria>
    fun existsByNomeAndHouseholdId(nome: String, householdId: Long): Boolean
    fun findByIdAndHouseholdId(id: Long, householdId: Long): Optional<Categoria>
}