package com.pucetec.students.dto
// Paquete donde viven todos los DTOs del proyecto

// ─────────────────────────────────────────
// DTO DE CREACIÓN/ACTUALIZACIÓN
// ─────────────────────────────────────────
data class StudentRequest(
    val name: String = "",
    // Nombre del estudiante
    // Default "" para evitar errores si el cliente no lo envía
    // El service valida que no esté en blanco antes de guardar
    // Si está vacío → lanza BlankNameException → HTTP 400

    val email: String = ""
    // Email del estudiante
    // Default "" igual que name
)
// Lo que llega del cliente en Postman al hacer POST o PUT:
// {
//   "name": "Carlos Pazmino",
//   "email": "crpazmino@puce.edu.ec"
// }

// ─────────────────────────────────────────
// DTO DE RESPUESTA
// ─────────────────────────────────────────
data class StudentResponse(
    val id: Long = 0L,
    // ID único generado automáticamente por la base de datos
    // El cliente nunca envía el ID — solo lo recibe

    val name: String = "",
    // Nombre del estudiante guardado en la base de datos

    val email: String = ""
    // Email del estudiante guardado en la base de datos
)
// Lo que devuelve el servidor al cliente:
// {
//   "id": 1,
//   "name": "Carlos Pazmino",
//   "email": "crpazmino@puce.edu.ec"
// }