package com.example.demo.entities

import jakarta.persistence.*

@Entity
@Table(name = "student")
open class Student(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val name: String = "",
    val email: String = ""
)