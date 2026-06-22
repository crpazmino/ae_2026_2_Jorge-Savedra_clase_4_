// ═══════════════════════════════════════════════════════
// SUBJECT MAPPER
// ═══════════════════════════════════════════════════════
package com.pucetec.students.mappers

import com.pucetec.students.dto.SubjectRequest
import com.pucetec.students.dto.SubjectResponse
import com.pucetec.students.entities.Professor
import com.pucetec.students.entities.Subject

fun SubjectRequest.toEntity(professor: Professor): Subject = Subject(
// Función de extensión sobre SubjectRequest
// Recibe el objeto Professor completo como parámetro
// Diferencia clave vs ProfessorMapper y StudentMapper
// El Service primero busca el Professor en la BD y luego lo pasa aquí
    name = this.name,
    // Copia el name del DTO a la Entity
    code = this.code,
    // Copia el code del DTO a la Entity — campo extra vs Professor/Student
    professor = professor
    // Asigna el objeto Professor completo — NO solo el professorId
    // Hibernate guarda automáticamente el professor_id en la tabla subject
)

fun Subject.toResponse(): SubjectResponse = SubjectResponse(
// Convierte Subject entity en SubjectResponse DTO para devolver al cliente
    id = this.id,
    name = this.name,
    code = this.code,
    professor = this.professor?.toResponse()
    // ?.  operador safe call de Kotlin
    // Si professor NO es null → llama ProfessorMapper.toResponse()
    // Si professor ES null   → devuelve null
    // Anida el ProfessorResponse completo dentro de SubjectResponse
)