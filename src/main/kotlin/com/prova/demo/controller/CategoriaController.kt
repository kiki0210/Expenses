package com.prova.demo.controller

import com.prova.demo.dto.CategoriaRequest
import com.prova.demo.dto.CategoriaResponse
import com.prova.demo.security.AuthenticatedUserProvider
import com.prova.demo.service.CategoriaService
import com.prova.demo.service.HouseholdService
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
        categoriaService.getAllByHousehold(authProvider.getCurretHouseholdId())

    @PostMapping
    fun create(
        @Valid @RequestBody categoriaRequest: CategoriaRequest
    ): ResponseEntity<CategoriaResponse> = ResponseEntity.status(
        HttpStatus.CREATED
    ).body(categoriaService.create(authProvider.getCurretHouseholdId(), categoriaRequest))

    @DeleteMapping("/{id}")
    fun delete( @PathVariable id: Long): ResponseEntity<Void> {
        categoriaService.delete(authProvider.getCurretHouseholdId(), id)
        return ResponseEntity.noContent().build()
    }

}
