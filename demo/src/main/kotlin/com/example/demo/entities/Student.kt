// Desarrollado por Carlos Pazmino - PUCE
package com.example.demo.entities

import jakarta.persistence.*

// Entity de estudiante - Carlos Pazmino
@Entity
@Table(name = "student")
open class Student(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    val name: String = "",

    val email: String = ""
)