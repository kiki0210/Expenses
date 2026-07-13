package com.prova.demo.repository

import com.prova.demo.model.Spesa
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.time.LocalDate

@Repository
interface SpesaRepository : JpaRepository<Spesa, Long> {
    fun findByUserId(userId: Long): List<Spesa>
    fun findByIdAndUserId(id: Long, userId: Long): Spesa?
    fun findByUserIdAndDataBetween(userId: Long, inizio: LocalDate, fine: LocalDate): List<Spesa>

   @Query("""
       SELECT s.categoria.nome, SUM(s.importo)
       FROM Spesa s
       WHERE s.user.id = :userId AND s.data BETWEEN :inizio AND :fine
       GROUP BY s.categoria.nome
   """)
   fun totalePerCategoria(userId: Long, inizio: LocalDate, fine: LocalDate): List<Pair<String, BigDecimal>>
}