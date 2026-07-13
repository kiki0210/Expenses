package com.prova.demo.service

import com.prova.demo.dto.*
import com.prova.demo.model.Spesa
import com.prova.demo.repository.CategoriaRepository
import com.prova.demo.repository.SpesaRepository
import com.prova.demo.repository.UserRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.YearMonth


@Service
class SpesaService(
    private val spesaRepository: SpesaRepository,
    private val categoriaRepository: CategoriaRepository,
    private val userRepository: UserRepository,
) {

    fun getAllByUser(userId: Long): List<SpesaResponse> = spesaRepository.findByUserId(userId).map { it.toResponse() }
    fun getById(userId: Long, id: Long): SpesaResponse = (spesaRepository.findByIdAndUserId(id, userId)
        ?: throw NoSuchElementException("Spesa $id non trovata")).toResponse()

    fun create(userId: Long, request: SpesaRequest): SpesaResponse {
        val user = userRepository.findById(userId).orElseThrow { NoSuchElementException("User $userId non trovato") }
        val categoria = categoriaRepository.findByIdAndUserId(request.categoriaId, userId)
            ?: throw NoSuchElementException("Categoria ${request.categoriaId} non trovata")

        val spesa = Spesa(
            descrizione = request.descrizione,
            importo = request.importo,
            data = request.data,
            categoria = categoria,
            user = user
        )
        return spesaRepository.save(spesa).toResponse()
    }

    fun riepilogoMensile(userId: Long, annoMese: YearMonth): RiepilogoMensile {
        val inizio = annoMese.atDay(1)
        val fine = annoMese.atEndOfMonth()
        val righe: List<Pair<String, BigDecimal>> = spesaRepository.totalePerCategoria(userId, inizio, fine)
        val perCategoria: List<TotaleCategoria> = righe.map { TotaleCategoria(it.first, it.second) }
        val totale = perCategoria.fold(BigDecimal.ZERO) { acc, tot -> acc.add(tot.totale) }
        return RiepilogoMensile(annoMese.toString(), totale, perCategoria)
    }

    fun delete(userId: Long, id: Long) {
        val spesa = spesaRepository.findByIdAndUserId(id, userId)
            ?: throw NoSuchElementException("Spesa $id non trovata")
        spesaRepository.delete(spesa)
    }

    private fun Spesa.toResponse() = SpesaResponse(
        id, descrizione, importo, data,
        CategoriaResponse(categoria.id, categoria.nome, categoria.colore),
        user.id
    )
}
