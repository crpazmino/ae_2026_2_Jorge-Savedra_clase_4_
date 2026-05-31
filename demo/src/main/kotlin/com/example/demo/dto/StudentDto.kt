package com.example.demo.dto

data class StudentRequest(
    val name: String = "",
    val email: String = ""
)

data class StudentResponse(
    val id: Long = 0L,
    val name: String = "",
    val email: String = ""
)