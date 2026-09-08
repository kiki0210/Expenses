package com.prova.demo.model

import jakarta.persistence.*



@Entity
@Table(name = "household")
data class Household(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true, nullable = false)
    var name: String,

   @OneToMany(mappedBy = "household", cascade = [(CascadeType.ALL)])
    var members:MutableList<User> = mutableListOf(),

)
