package com.pucetec.students.controllers
// Paquete donde vive esta clase

import com.pucetec.students.dto.SubjectRequest
// DTO con los datos que envía el cliente (name, code, professorId)
// Nota: incluye professorId — diferencia clave vs Student y Professor

import com.pucetec.students.dto.SubjectResponse
// DTO con los datos que devuelve el servidor (id, name, code, professor)
// Nota: devuelve el objeto Professor completo anidado, no solo el professorId

import com.pucetec.students.services.SubjectService
// El service que contiene la lógica de negocio de materias

import org.springframework.http.HttpStatus
// Códigos HTTP (201, 204, etc.)

import org.springframework.web.bind.annotation.*
// Todas las anotaciones REST

@RestController
// Esta clase maneja peticiones HTTP y devuelve JSON

@RequestMapping("/api/subjects")
// Todas las rutas empiezan con /api/subjects

class SubjectController(
    private val subjectService: SubjectService
    // Spring inyecta el SubjectService automáticamente
) {

    @PostMapping
    // POST /api/subjects — crear una nueva materia
    @ResponseStatus(HttpStatus.CREATED)
    // Responde HTTP 201 Created
    fun createSubject(@RequestBody request: SubjectRequest): SubjectResponse =
    // Recibe JSON con name, code y professorId
    // El service busca el Professor por professorId antes de guardar
        // Si el Professor no existe → lanza ProfessorNotFoundException → 404
        subjectService.createSubject(request)

    @GetMapping
    // GET /api/subjects — obtener todas las materias
    // Responde HTTP 200 por defecto
    fun getAllSubjects(): List<SubjectResponse> =
    // Devuelve lista completa de materias
        // Cada materia incluye el objeto Professor completo anidado
        subjectService.getAllSubjects()

    @GetMapping("/{id}")
    // GET /api/subjects/1 — obtener una materia por ID
    fun getSubjectById(@PathVariable id: Long): SubjectResponse =
    // Extrae el ID de la URL
        // Si no existe → service lanza SubjectNotFoundException → 404
        subjectService.getSubjectById(id)

    @PutMapping("/{id}")
    // PUT /api/subjects/1 — actualizar una materia existente
    // Responde HTTP 200 por defecto
    fun updateSubject(
        @PathVariable id: Long,           // ID de la materia a actualizar
        @RequestBody request: SubjectRequest  // Nuevos datos (name, code, professorId)
        // Puede cambiar incluso el profesor asignado a la materia
    ): SubjectResponse =
        subjectService.updateSubject(id, request)

    @DeleteMapping("/{id}")
    // DELETE /api/subjects/1 — eliminar una materia
    @ResponseStatus(HttpStatus.NO_CONTENT)
    // Responde HTTP 204 — éxito sin cuerpo de respuesta
    fun deleteSubject(@PathVariable id: Long) =
    // Si no existe → service lanza SubjectNotFoundException → 404
        // Si existe → lo elimina y no devuelve nada
        subjectService.deleteSubject(id)
}