package com.pucetec.students.entities

import jakarta.persistence.*

@Entity
@Table(name = "enrollment")
open class Enrollment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @ManyToOne
    @JoinColumn(name = "student_id")
    val student: Student? = null,
    @ManyToOne
    @JoinColumn(name = "subject_id")
    val subject: Subject? = null
)