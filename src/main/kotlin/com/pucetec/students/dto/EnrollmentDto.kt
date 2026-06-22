package com.pucetec.students.dto
// Paquete donde viven todos los DTOs del proyecto

// ─────────────────────────────────────────
// DTO DE CREACIÓN — lo que envía el cliente
// ─────────────────────────────────────────
data class EnrollmentRequest(
    val studentId: Long = 0L,
    // ID del estudiante a inscribir
    // Solo se recibe el ID, no el objeto completo
    // Default 0L para evitar errores si el cliente no lo envía

    val subjectId: Long = 0L
    // ID de la materia en la que se inscribe
    // Solo se recibe el ID, no el objeto completo
)
// Lo que llega del cliente en Postman:
// {
//   "studentId": 1,
//   "subjectId": 2
// }

// ─────────────────────────────────────────
// DTO DE ACTUALIZACIÓN — solo cambia status
// ─────────────────────────────────────────
data class EnrollmentUpdateRequest(
    val status: String = ""
    // Solo se puede cambiar el status de la inscripción
    // Ejemplos: "INSCRITO", "RETIRADO", "APROBADO", "REPROBADO"
    // No se puede cambiar el student ni el subject una vez creado
)
// Lo que llega del cliente en Postman al hacer PUT:
// {
//   "status": "APROBADO"
// }

// ─────────────────────────────────────────
// DTO DE RESPUESTA — lo que devuelve el servidor
// ─────────────────────────────────────────
data class EnrollmentResponse(
    val id: Long = 0L,
    // ID único de la inscripción generado por la base de datos

    val createdAt: String = "",
    // Fecha y hora de cuando se creó la inscripción
    // Se genera automáticamente con LocalDateTime.now() en la Entity
    // Ejemplo: "2026-06-17T14:06:15.410"

    val status: String = "",
    // Estado actual de la inscripción
    // Por defecto "INSCRITO" cuando se crea

    val student: StudentResponse? = null,
    // Objeto StudentResponse completo anidado
    // Nullable (?) por si en algún caso el student no carga
    // Incluye id, name, email del estudiante

    val subject: SubjectResponse? = null
    // Objeto SubjectResponse completo anidado
    // Nullable (?) por si en algún caso el subject no carga
    // Incluye id, name, code y el Professor anidado también
)
// Lo que devuelve el servidor al cliente:
// {
//   "id": 1,
//   "createdAt": "2026-06-17T14:06:15.410",
//   "status": "INSCRITO",
//   "student": {
//     "id": 1,
//     "name": "Carlos",
//     "email": "carlos@puce.edu.ec"
//   },
//   "subject": {
//     "id": 2,
//     "name": "Matematicas",
//     "code": "MAT101",
//     "professor": {
//       "id": 1,
//       "name": "Dr. Lopez",
//       "email": "lopez@puce.edu.ec"
//     }
//   }
// }