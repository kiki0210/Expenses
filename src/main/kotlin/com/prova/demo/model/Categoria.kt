package com.prova.demo.model


import jakarta.persistence.*

@Entity
@Table(
    name = "categorie", uniqueConstraints = [
        UniqueConstraint(columnNames = ["nome", "user_id"]) // stesso nome ok per utenti diversi
    ]
)

data class Categoria(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true)
    var nome: String,

    var colore: String? = null, // es. "#FF5733" per la UI Android

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "household_id", nullable = false)
    var household: Household,
)