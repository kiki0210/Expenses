package com.prova.demo.service


import com.prova.demo.dto.RegistrazioneRequest
import com.prova.demo.dto.UserResponse
import com.prova.demo.model.User
import com.prova.demo.repository.HouseholdRepository
import com.prova.demo.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserService(private val repository: UserRepository, private val householdRepository: HouseholdRepository, private val householdService: HouseholdService) {

    fun registra(request: RegistrazioneRequest): UserResponse {
        if (repository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email già registrata")
        }
        val user = User(email = request.email, nome = request.nome, password = request.password)
        val saved = repository.save(user)

        when{
            request.newHousehold!=null ->
                householdService.createHousehold(request.newHousehold, saved)
            request.existentHousehold!=null->{
              val house = householdRepository.findByName(request.existentHousehold).orElseThrow { NoSuchElementException("Nessuna casa trovata con questo nome") }
                saved.household = house
                repository.save(saved)
            }
        }

        return saved.toResponse()
    }

    fun getById(id: Long): UserResponse =
        repository.findById(id).orElseThrow { NoSuchElementException("Utente $id non trovato") }.toResponse()

    private fun User.toResponse() = UserResponse(id, email, nome, household?.id,isHouseholdAdmin)
}