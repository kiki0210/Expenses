package com.prova.demo.service

import com.prova.demo.model.Household
import com.prova.demo.model.User
import com.prova.demo.repository.HouseholdRepository
import com.prova.demo.repository.UserRepository
import org.springframework.stereotype.Service


@Service
class HouseholdService(
    private val householdRepository: HouseholdRepository,
    private val userRepository: UserRepository
) {

    fun createHousehold(name: String, admin: User): Household {
        if (householdRepository.existsByName(name)) {
            throw IllegalArgumentException("Household with name $name already exists")
        }
        val household = householdRepository.save(Household(name = name))
        admin.household = household
        admin.isHouseholdAdmin = true
        userRepository.save(admin)
        return household
    }


    fun renameHousehold(householdId: Long, newName: String, requestedBy:User){
        require(requestedBy.isHouseholdAdmin && requestedBy.household?.id == householdId){
            "Solo l'admin della casa può rinominarla"
        }

        val household = householdRepository.findById(householdId).orElseThrow { NoSuchElementException("Household does not exist") }
        if (householdRepository.existsByName(newName)) {
            throw IllegalArgumentException("Household with name $newName already exists")
        }
        household.name = newName
        householdRepository.save(household)
    }

    fun addUserToHousehold(householdId: Long, mail: String, requestedBy: User) {
        require(requestedBy.isHouseholdAdmin && requestedBy.household?.id == householdId){
            "Only admin of household can add member"
        }
        val newMember = userRepository.findByEmail(mail).orElseThrow{ NoSuchElementException("User does not exist") }
        if (newMember.household!=null){
            throw IllegalArgumentException("Household with email $mail has already an household")
        }
        newMember.household = householdRepository.findById(householdId).get()
        newMember.isHouseholdAdmin = false
        userRepository.save(newMember)
    }
}