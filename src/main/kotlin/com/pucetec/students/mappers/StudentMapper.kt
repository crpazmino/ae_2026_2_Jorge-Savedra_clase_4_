// STUDENT MAPPER
// ═══════════════════════════════════════════════════════
package com.pucetec.students.mappers

import com.pucetec.students.dto.StudentRequest
import com.pucetec.students.dto.StudentResponse
import com.pucetec.students.entities.Student

fun StudentRequest.toEntity(): Student = Student(
// Idéntico en estructura a ProfessorMapper
// Convierte StudentRequest DTO en Student entity para guardar en BD
    name = this.name,
    // Copia el name del DTO a la Entity
    email = this.email
    // Copia el email del DTO a la Entity
)

fun Student.toResponse(): StudentResponse = StudentResponse(
// Convierte Student entity en StudentResponse DTO para devolver al cliente
    id = this.id,
    // Incluye el ID generado por la BD
    name = this.name,
    email = this.email
)
