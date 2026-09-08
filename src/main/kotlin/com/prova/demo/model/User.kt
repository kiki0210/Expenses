package com.prova.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true, nullable = false)
    var email: String,

    var nome: String,

    @Column(nullable = false)
    var password: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "household_id")
    var household: Household? = null,

    @Column(nullable = false)
    var isHouseholdAdmin:Boolean = false,
)
