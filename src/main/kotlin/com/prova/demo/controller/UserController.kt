package com.prova.demo.controller

import com.prova.demo.dto.RegistrazioneRequest
import com.prova.demo.dto.UserResponse
import com.prova.demo.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/utenti")
@CrossOrigin(origins = ["*"])
class UserController(private val service: UserService) {

   @PostMapping("/registrazione")
   fun registra(@Valid @RequestBody request: RegistrazioneRequest) : ResponseEntity<UserResponse> =
       ResponseEntity.status(HttpStatus.CREATED).body(service.registra(request))
}