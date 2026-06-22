package com.pucetec.students.mappers
// Paquete donde viven todos los mappers del proyecto

import com.pucetec.students.dto.EnrollmentResponse
// DTO de respuesta que se devuelve al cliente

import com.pucetec.students.entities.Enrollment
// Entidad de base de datos que se convierte a DTO

fun Enrollment.toResponse(): EnrollmentResponse =
// Función de extensión sobre la clase Enrollment
// "Enrollment.toResponse()" significa que se puede llamar
// directamente sobre cualquier objeto Enrollment
// Ejemplo: enrollment.toResponse()
// No necesita ser un método dentro de la clase Enrollment

    EnrollmentResponse(
        id = this.id,
        // this = el objeto Enrollment sobre el que se llama la función
        // Copia el ID de la entidad al DTO

        createdAt = this.createdAt,
        // Copia la fecha de creación — ya es String, no necesita conversión

        status = this.status,
        // Copia el estado actual — "INSCRITO", "APROBADO", etc.

        student = this.student?.toResponse(),
        // ?.  es el operador safe call de Kotlin
        // Si student NO es null → llama student.toResponse() (StudentMapper)
        // Si student ES null   → devuelve null sin lanzar NullPointerException
        // Convierte Student entity en StudentResponse DTO

        subject = this.subject?.toResponse()
        // Igual que student — usa safe call
        // Si subject NO es null → llama subject.toResponse() (SubjectMapper)
        // Si subject ES null   → devuelve null
        // Convierte Subject entity en SubjectResponse DTO
        // SubjectMapper a su vez convierte el Professor anidado también
    )