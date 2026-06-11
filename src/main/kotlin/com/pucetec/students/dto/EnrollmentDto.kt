package com.pucetec.students.dto

data class EnrollmentRequest(
    val studentId: Long = 0L,
    val subjectId: Long = 0L
)

data class EnrollmentResponse(
    val id: Long = 0L,
    val studentName: String = "",
    val subjectName: String = ""
)