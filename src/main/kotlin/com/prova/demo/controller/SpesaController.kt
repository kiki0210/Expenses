package com.prova.demo.controller

import com.prova.demo.dto.RiepilogoMensile
import com.prova.demo.dto.SpesaRequest
import com.prova.demo.dto.SpesaResponse
import com.prova.demo.security.AuthenticatedUserProvider
import com.prova.demo.service.SpesaService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.YearMonth

@RestController
@RequestMapping("/api/spese")
@CrossOrigin(origins = ["*"])
class SpesaController(private val service: SpesaService,
    private val authProvider: AuthenticatedUserProvider
) {

    @GetMapping
    fun getAll(): List<SpesaResponse> =
        service.getAllByUser(authProvider.getCurrentUserId())

    @PostMapping
    fun create(
        @Valid @RequestBody request: SpesaRequest
    ): ResponseEntity<SpesaResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(service.create(authProvider.getCurrentUserId(), request))

    @DeleteMapping("/{id}")
    fun delete( @PathVariable id: Long): ResponseEntity<Void> {
        service.delete(authProvider.getCurrentUserId(), id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/riepilogo/{annoMese}")
    fun riepilogoMensile(
        @PathVariable annoMese: String
    ): RiepilogoMensile = service.riepilogoMensile(authProvider.getCurrentUserId(), YearMonth.parse(annoMese))
}