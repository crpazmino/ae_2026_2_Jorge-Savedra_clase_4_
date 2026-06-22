// ═══════════════════════════════════════════════════════
// PROFESSOR MAPPER
// ═══════════════════════════════════════════════════════
package com.pucetec.students.mappers

import com.pucetec.students.dto.ProfessorRequest
import com.pucetec.students.dto.ProfessorResponse
import com.pucetec.students.entities.Professor

fun ProfessorRequest.toEntity(): Professor = Professor(
// Función de extensión sobre ProfessorRequest
// Convierte el DTO que llegó del cliente en una Entity para guardar en BD
// No recibe parámetros extra — Professor no depende de ninguna otra entidad
    name = this.name,
    // Copia el name del DTO a la Entity
    email = this.email
    // Copia el email del DTO a la Entity
    // No se copia el ID — la base de datos lo genera automáticamente
)

fun Professor.toResponse(): ProfessorResponse = ProfessorResponse(
// Función de extensión sobre Professor entity
// Convierte la Entity de la BD en un DTO para devolver al cliente
    id = this.id,
    // Ahora sí incluye el ID — fue generado por la BD al guardar
    name = this.name,
    email = this.email
)