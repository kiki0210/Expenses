package com.prova.demo.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.math.BigDecimal
import java.time.LocalDate

data class CategoriaRequest(
    @field:NotBlank val nome: String,
    val colore: String? = null
)

data class CategoriaResponse(val id: Long, val nome: String, val colore: String?)
data class RegistrazioneRequest(
    @field:Email val email: String,
    @field:NotBlank val nome: String,
    @field:Size(min = 6) val password: String
)

data class LoginRequest(
    @field:Email val email: String,
    @field:NotBlank val password: String
)

data class AuthResponse(
    val token: String,
    val userId: Long,
    val email: String,
    val nome: String
)

data class UserResponse(val id: Long, val email: String, val nome: String)

data class SpesaRequest(
    @field:NotBlank val descrizione: String,
    @field:Positive val importo: BigDecimal,
    val data: LocalDate = LocalDate.now(),
    val categoriaId: Long
)

data class SpesaResponse(
    val id: Long,
    val descrizione: String,
    val importo: BigDecimal,
    val data: LocalDate,
    val categoria: CategoriaResponse,
    val userId: Long
)

data class TotaleCategoria(
    val categoria: String,
    val totale: BigDecimal
)

data class RiepilogoMensile(
    val mese: String,
    val totaleGenerale: BigDecimal,
    val perCategoria: List<TotaleCategoria>
)