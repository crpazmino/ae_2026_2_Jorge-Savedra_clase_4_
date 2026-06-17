package com.pucetec.students.dto

data class SubjectRequest(
    val name: String = "",
    val code: String = "",
    val professorId: Long = 0L
)

data class SubjectResponse(
    val id: Long = 0L,
    val name: String = "",
    val code: String = "",
    val professor: ProfessorResponse? = null
)