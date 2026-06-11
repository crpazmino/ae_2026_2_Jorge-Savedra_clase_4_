package com.pucetec.students.mappers

import com.pucetec.students.dto.SubjectRequest
import com.pucetec.students.dto.SubjectResponse
import com.pucetec.students.entities.Subject
import com.pucetec.students.entities.Professor

fun SubjectRequest.toEntity(professor: Professor): Subject = Subject(
    name = this.name,
    professor = professor
)

fun Subject.toResponse(): SubjectResponse = SubjectResponse(
    id = this.id,
    name = this.name,
    professorName = this.professor?.name ?: ""
)