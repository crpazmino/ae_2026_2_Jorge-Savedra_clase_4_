package com.pucetec.students.entities

import jakarta.persistence.*

@Entity
@Table(name = "subject")
open class Subject(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val name: String = "",
    @ManyToOne
    @JoinColumn(name = "professor_id")
    val professor: Professor? = null
)