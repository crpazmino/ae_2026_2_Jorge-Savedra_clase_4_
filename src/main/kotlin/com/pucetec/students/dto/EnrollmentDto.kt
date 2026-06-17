package com.pucetec.students.dto

data class EnrollmentRequest(
    val studentId: Long = 0L,
    val subjectId: Long = 0L
)

data class EnrollmentUpdateRequest(
    val status: String = ""
)

data class EnrollmentResponse(
    val id: Long = 0L,
    val createdAt: String = "",
    val status: String = "",
    val student: StudentResponse? = null,
    val subject: SubjectResponse? = null
)