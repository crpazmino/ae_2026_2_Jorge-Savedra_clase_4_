package com.pucetec.students.controllers
// Paquete donde vive esta clase

import com.pucetec.students.dto.ProfessorRequest
// DTO con los datos que envía el cliente (name, email)

import com.pucetec.students.dto.ProfessorResponse
// DTO con los datos que devuelve el servidor (id, name, email)

import com.pucetec.students.services.ProfessorService
// El service que contiene la lógica de negocio de profesores

import org.springframework.http.HttpStatus
// Códigos HTTP (201, 204, etc.)

import org.springframework.web.bind.annotation.*
// Todas las anotaciones REST

@RestController
// Esta clase maneja peticiones HTTP y devuelve JSON

@RequestMapping("/api/professors")
// Todas las rutas empiezan con /api/professors

class ProfessorController(
    private val professorService: ProfessorService
    // Spring inyecta el ProfessorService automáticamente
) {

    @PostMapping
    // POST /api/professors — crear un nuevo profesor
    @ResponseStatus(HttpStatus.CREATED)
    // Responde HTTP 201
    fun createProfessor(@RequestBody request: ProfessorRequest): ProfessorResponse =
    // Recibe JSON → lo convierte en ProfessorRequest → llama al service
        // El service valida que name no esté en blanco antes de guardar
        professorService.createProfessor(request)

    @GetMapping
    // GET /api/professors — obtener todos los profesores
    // Responde HTTP 200 por defecto
    fun getAllProfessors(): List<ProfessorResponse> =
        // Devuelve lista completa de profesores como JSON
        professorService.getAllProfessors()

    @GetMapping("/{id}")
    // GET /api/professors/1 — obtener un profesor por ID
    fun getProfessorById(@PathVariable id: Long): ProfessorResponse =
    // Extrae el ID de la URL
        // Si no existe → service lanza ProfessorNotFoundException → 404
        professorService.getProfessorById(id)

    @PutMapping("/{id}")
    // PUT /api/professors/1 — actualizar un profesor existente
    // Responde HTTP 200 por defecto
    fun updateProfessor(
        @PathVariable id: Long,            // ID del profesor a actualizar
        @RequestBody request: ProfessorRequest  // Nuevos datos (name, email)
        // Nota: usa el mismo DTO de Request que el POST
        // porque los campos a actualizar son los mismos
    ): ProfessorResponse =
        professorService.updateProfessor(id, request)

    @DeleteMapping("/{id}")
    // DELETE /api/professors/1 — eliminar un profesor
    @ResponseStatus(HttpStatus.NO_CONTENT)
    // Responde HTTP 204 — éxito sin cuerpo de respuesta
    fun deleteProfessor(@PathVariable id: Long) =
    // Si no existe → service lanza ProfessorNotFoundException → 404
        // Si existe → lo elimina y no devuelve nada
        professorService.deleteProfessor(id)
}