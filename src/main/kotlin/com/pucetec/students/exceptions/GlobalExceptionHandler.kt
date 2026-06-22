package com.pucetec.students.exceptions
// Paquete donde viven todas las excepciones personalizadas del proyecto

import org.springframework.http.HttpStatus
// Códigos HTTP (NOT_FOUND=404, BAD_REQUEST=400, etc.)

import org.springframework.web.bind.annotation.*
// Importa @RestControllerAdvice, @ExceptionHandler, @ResponseStatus

import java.time.Instant
// Para generar el timestamp exacto del momento del error

@RestControllerAdvice
// Le dice a Spring que esta clase intercepta excepciones de TODOS los controllers
// Es un "interceptor global" — no hay que configurar nada en cada controller
// Combina @ControllerAdvice + @ResponseBody (devuelve JSON automáticamente)

class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException::class)
    // Intercepta específicamente StudentNotFoundException
    // Cuando cualquier controller lanza esta excepción, Spring llama este método
    @ResponseStatus(HttpStatus.NOT_FOUND)
    // La respuesta HTTP tendrá código 404
    fun handleStudentNotFound(ex: StudentNotFoundException): Map<String, Any> =
    // Recibe la excepción con su mensaje
        // Devuelve un Map que Spring convierte automáticamente a JSON
        errorMap(404, "Not Found", ex.message)

    @ExceptionHandler(ProfessorNotFoundException::class)
    // Intercepta ProfessorNotFoundException
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleProfessorNotFound(ex: ProfessorNotFoundException): Map<String, Any> =
        errorMap(404, "Not Found", ex.message)

    @ExceptionHandler(SubjectNotFoundException::class)
    // Intercepta SubjectNotFoundException
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleSubjectNotFound(ex: SubjectNotFoundException): Map<String, Any> =
        errorMap(404, "Not Found", ex.message)

    @ExceptionHandler(EnrollmentNotFoundException::class)
    // Intercepta EnrollmentNotFoundException
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleEnrollmentNotFound(ex: EnrollmentNotFoundException): Map<String, Any> =
        errorMap(404, "Not Found", ex.message)

    @ExceptionHandler(BlankNameException::class)
    // Intercepta BlankNameException
    // Única excepción con código 400 en lugar de 404
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBlankName(ex: BlankNameException): Map<String, Any> =
        errorMap(400, "Bad Request", ex.message)

    private fun errorMap(status: Int, error: String, message: String?): Map<String, Any> =
    // Función privada reutilizada por todos los handlers
        // Construye el mismo formato de error para todas las excepciones
        mapOf(
            "timestamp" to Instant.now().toString(),
            // Momento exacto del error — útil para debugging
            // Ejemplo: "2026-06-17T19:06:15.410Z"

            "status" to status,
            // Código HTTP numérico — 404 o 400

            "error" to error,
            // Descripción textual del error — "Not Found" o "Bad Request"

            "message" to (message ?: "Error")
            // Mensaje específico de la excepción
            // ?: "Error" es el valor por defecto si message es null
            // Ejemplo: "Student with id 99 not found"
        )
}