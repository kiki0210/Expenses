package com.prova.demo.controller

import com.prova.demo.dto.CategoriaRequest
import com.prova.demo.dto.CategoriaResponse
import com.prova.demo.security.AuthenticatedUserProvider
import com.prova.demo.service.CategoriaService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/categoria")
@CrossOrigin(origins = ["*"])
class CategoriaController(
    private val categoriaService: CategoriaService,
    private val authProvider: AuthenticatedUserProvider
) {

    @GetMapping
    fun getAll(): List<CategoriaResponse> =
        categoriaService.getAllByUser(authProvider.getCurrentUserId())

    @PostMapping
    fun create(
        @Valid @RequestBody categoriaRequest: CategoriaRequest
    ): ResponseEntity<CategoriaResponse> = ResponseEntity.status(
        HttpStatus.CREATED
    ).body(categoriaService.create(authProvider.getCurrentUserId(), categoriaRequest))

    @DeleteMapping("/{id}")
    fun delete( @PathVariable id: Long): ResponseEntity<Void> {
        categoriaService.delete(authProvider.getCurrentUserId(), id)
        return ResponseEntity.noContent().build()
    }

}
