package com.pucetec.students.controllers
// Define en qué paquete vive esta clase dentro del proyecto

import com.pucetec.students.dto.EnrollmentRequest
// DTO que representa los datos que llegan del cliente al CREAR una inscripción

import com.pucetec.students.dto.EnrollmentResponse
// DTO que representa los datos que se DEVUELVEN al cliente

import com.pucetec.students.dto.EnrollmentUpdateRequest
// DTO que representa los datos que llegan al ACTUALIZAR una inscripción (solo status)

import com.pucetec.students.services.EnrollmentService
// Importa el Service que contiene la lógica de negocio

import org.springframework.http.HttpStatus
// Importa los códigos HTTP (201, 204, etc.)

import org.springframework.web.bind.annotation.*
// Importa todas las anotaciones REST: @RestController, @RequestMapping, @GetMapping, etc.

@RestController
// Le dice a Spring que esta clase maneja peticiones HTTP y devuelve JSON directamente

@RequestMapping("/api/enrollments")
// Todas las rutas de esta clase empiezan con /api/enrollments

class EnrollmentController(
    private val enrollmentService: EnrollmentService
    // Spring inyecta automáticamente el EnrollmentService (Dependency Injection)
    // El controller no crea el service, Spring se lo pasa
) {

    @PostMapping
    // Escucha peticiones POST en /api/enrollments
    @ResponseStatus(HttpStatus.CREATED)
    // Responde con HTTP 201 Created en lugar del 200 por defecto
    fun createEnrollment(@RequestBody request: EnrollmentRequest): EnrollmentResponse =
    // @RequestBody convierte el JSON del cliente en un objeto EnrollmentRequest
        // Llama al service y devuelve un EnrollmentResponse que Spring convierte a JSON
        enrollmentService.createEnrollment(request)

    @GetMapping
    // Escucha peticiones GET en /api/enrollments
    // No necesita @ResponseStatus porque 200 OK es el default
    fun getAllEnrollments(): List<EnrollmentResponse> =
        // Devuelve una lista de todas las inscripciones como JSON
        enrollmentService.getAllEnrollments()

    @GetMapping("/{id}")
    // Escucha GET en /api/enrollments/1 por ejemplo
    // {id} es una variable en la URL
    fun getEnrollmentById(@PathVariable id: Long): EnrollmentResponse =
    // @PathVariable extrae el número de la URL y lo pasa como parámetro
        // Si no existe ese ID el service lanza EnrollmentNotFoundException → 404
        enrollmentService.getEnrollmentById(id)

    @PutMapping("/{id}")
    // Escucha PUT en /api/enrollments/1
    // Se usa para actualizar una inscripción existente
    fun updateEnrollment(
        @PathVariable id: Long,               // ID de la inscripción a actualizar
        @RequestBody request: EnrollmentUpdateRequest  // Solo contiene el nuevo status
    ): EnrollmentResponse =
        enrollmentService.updateEnrollment(id, request)

    @DeleteMapping("/{id}")
    // Escucha DELETE en /api/enrollments/1
    @ResponseStatus(HttpStatus.NO_CONTENT)
    // Responde con HTTP 204 No Content — éxito pero sin cuerpo de respuesta
    fun deleteEnrollment(@PathVariable id: Long) =
    // No devuelve nada (Unit en Kotlin)
        // Si no existe el ID el service lanza EnrollmentNotFoundException → 404
        enrollmentService.deleteEnrollment(id)
}