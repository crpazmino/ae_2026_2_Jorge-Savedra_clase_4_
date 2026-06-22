package com.pucetec.students.dto
// Paquete donde viven todos los DTOs del proyecto

// ─────────────────────────────────────────
// DTO DE CREACIÓN/ACTUALIZACIÓN
// ─────────────────────────────────────────
data class SubjectRequest(
    val name: String = "",
    // Nombre de la materia
    // Default "" para evitar errores si el cliente no lo envía
    // El service valida que no esté en blanco antes de guardar
    // Si está vacío → lanza BlankNameException → HTTP 400

    val code: String = "",
    // Código único de la materia
    // Ejemplo: "MAT101", "INF202", "FIS301"
    // Diferencia clave vs Professor y Student — tiene un campo extra

    val professorId: Long = 0L
    // ID del profesor que dicta esta materia
    // El service busca el Professor en la base de datos con este ID
    // Si no existe → lanza ProfessorNotFoundException → HTTP 404
    // Default 0L para evitar errores si el cliente no lo envía
)
// Lo que llega del cliente en Postman al hacer POST o PUT:
// {
//   "name": "Matematicas",
//   "code": "MAT101",
//   "professorId": 1
// }

// ─────────────────────────────────────────
// DTO DE RESPUESTA
// ─────────────────────────────────────────
data class SubjectResponse(
    val id: Long = 0L,
    // ID único generado automáticamente por la base de datos

    val name: String = "",
    // Nombre de la materia guardado en la base de datos

    val code: String = "",
    // Código de la materia guardado en la base de datos

    val professor: ProfessorResponse? = null
    // Objeto ProfessorResponse completo anidado
    // Nullable (?) por si en algún caso el professor no carga
    // El Mapper convierte el Professor entity en ProfessorResponse
    // Nota: el cliente envía professorId (Long) pero recibe professor (objeto)
)
// Lo que devuelve el servidor al cliente:
// {
//   "id": 1,
//   "name": "Matematicas",
//   "code": "MAT101",
//   "professor": {
//     "id": 1,
//     "name": "Dr. Lopez",
//     "email": "lopez@puce.edu.ec"
//   }
// }