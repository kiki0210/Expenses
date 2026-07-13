package com.prova.demo.repository

import com.prova.demo.model.Categoria
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoriaRepository : JpaRepository<Categoria, Long> {
    fun findByUserId(userId: Long): List<Categoria>
    fun findByIdAndUserId(id: Long, userId: Long): Categoria?
}