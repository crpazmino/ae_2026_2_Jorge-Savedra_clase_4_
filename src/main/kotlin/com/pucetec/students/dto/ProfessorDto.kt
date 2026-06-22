package com.pucetec.students.dto
// Paquete donde viven todos los DTOs del proyecto

// ─────────────────────────────────────────
// DTO DE CREACIÓN/ACTUALIZACIÓN
// ─────────────────────────────────────────
data class ProfessorRequest(
    val name: String = "",
    // Nombre del profesor
    // Default "" para evitar errores si el cliente no lo envía
    // El service valida que no esté en blanco antes de guardar
    // Si está vacío → lanza BlankNameException → HTTP 400

    val email: String = ""
    // Email del profesor
    // Default "" igual que name
)
// Lo que llega del cliente en Postman al hacer POST o PUT:
// {
//   "name": "Dr. Lopez",
//   "email": "lopez@puce.edu.ec"
// }

// ─────────────────────────────────────────
// DTO DE RESPUESTA
// ─────────────────────────────────────────
data class ProfessorResponse(
    val id: Long = 0L,
    // ID único generado automáticamente por la base de datos
    // El cliente nunca envía el ID — solo lo recibe

    val name: String = "",
    // Nombre del profesor guardado en la base de datos

    val email: String = ""
    // Email del profesor guardado en la base de datos
)
// Lo que devuelve el servidor al cliente:
// {
//   "id": 1,
//   "name": "Dr. Lopez",
//   "email": "lopez@puce.edu.ec"
// }