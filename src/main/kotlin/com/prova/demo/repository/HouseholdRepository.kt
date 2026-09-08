package com.prova.demo.repository

import com.prova.demo.model.Household
import com.prova.demo.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface HouseholdRepository : JpaRepository<Household, Long>{
    fun findByName(name: String): Optional<Household>
    fun existsByName(name: String): Boolean
}