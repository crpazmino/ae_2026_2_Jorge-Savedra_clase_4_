package com.pucetec.students.mappers

import com.pucetec.students.dto.EnrollmentResponse
import com.pucetec.students.entities.Enrollment

fun Enrollment.toResponse(): EnrollmentResponse = EnrollmentResponse(
    id = this.id,
    studentName = this.student?.name ?: "",
    subjectName = this.subject?.name ?: ""
)