package com.pucetec.students.services
// Paquete donde viven todos los services del proyecto

import com.pucetec.students.dto.StudentRequest
import com.pucetec.students.dto.StudentResponse
// DTOs de entrada y salida — Student solo necesita 2 (no tiene UpdateRequest)

import com.pucetec.students.entities.Student
// Entidad que se guarda en la base de datos

import com.pucetec.students.exceptions.BlankNameException
import com.pucetec.students.exceptions.StudentNotFoundException
// 2 excepciones:
// BlankNameException     → nombre vacío → 400
// StudentNotFoundException→ studentId no existe → 404

import com.pucetec.students.mappers.toEntity
// Convierte StudentRequest → Student entity

import com.pucetec.students.mappers.toResponse
// Convierte Student entity → StudentResponse DTO

import com.pucetec.students.repositories.StudentRepository
// Único repositorio que necesita — Student no depende de otras entidades

import org.slf4j.LoggerFactory
// Para crear logs informativos en la consola

import org.springframework.stereotype.Service
// Le dice a Spring que esta clase es un Service

@Service
// Spring crea una instancia única (Singleton)
// Se inyecta en StudentController y en EnrollmentService

class StudentService(
    private val studentRepository: StudentRepository
    // Solo necesita 1 repositorio — igual que ProfessorService
) {
    private val logger = LoggerFactory.getLogger(StudentService::class.java)
    // Logger asociado a esta clase para mensajes en consola

    fun createStudent(request: StudentRequest): StudentResponse {
        if (request.name.isBlank()) throw BlankNameException("El nombre no puede estar vacío")
        // Validación ANTES de guardar
        // isBlank() → true si está vacío "" o solo tiene espacios "   "
        // Si falla → lanza BlankNameException → GlobalExceptionHandler → 400

        logger.info("Creando estudiante: ${request.email}")
        // Log informativo — muestra el email del estudiante que se está creando

        return studentRepository.save(request.toEntity()).toResponse()
        // request.toEntity() → convierte StudentRequest a Student entity
        // .save()            → INSERT en la tabla student, asigna ID automático
        // .toResponse()      → convierte Student entity a StudentResponse DTO
    }

    fun getAllStudents(): List<StudentResponse> {
        logger.info("Obteniendo todos los estudiantes")
        // Log informativo en consola

        return studentRepository.findAll().map { it.toResponse() }
        // findAll()    → SELECT * FROM student
        // .map { }     → itera cada Student entity
        // toResponse() → convierte cada uno a StudentResponse DTO
    }

    fun getStudentById(id: Long): StudentResponse {
        val student = studentRepository.findById(id)
            .orElseThrow { StudentNotFoundException("Estudiante no encontrado con id: $id") }
        // findById(id) → SELECT WHERE id = ?
        // .orElseThrow → si no existe lanza StudentNotFoundException → 404
        // Si existe → guarda el objeto Student en val student

        return student.toResponse()
        // Convierte la entity a DTO y devuelve al controller
    }

    fun updateStudent(id: Long, request: StudentRequest): StudentResponse {
        val student = studentRepository.findById(id)
            .orElseThrow { StudentNotFoundException("Estudiante no encontrado con id: $id") }
        // Primero verifica que el estudiante existe
        // Si no existe → lanza StudentNotFoundException → 404

        if (request.name.isBlank()) throw BlankNameException("El nombre no puede estar vacío")
        // Valida el nombre DESPUÉS de verificar que existe el ID
        // Misma validación que en createStudent

        val updated = studentRepository.save(
            Student(
                id = student.id,
                // Mantiene el mismo ID — Hibernate hace UPDATE en lugar de INSERT
                name = request.name,
                // Actualiza el nombre con el nuevo valor
                email = request.email
                // Actualiza el email con el nuevo valor
            )
        )
        return updated.toResponse()
        // Devuelve el estudiante actualizado como DTO
    }

    fun deleteStudent(id: Long) {
        if (!studentRepository.existsById(id))
            throw StudentNotFoundException("Estudiante no encontrado con id: $id")
        // existsById → SELECT COUNT WHERE id = ? > 0
        // Si no existe → lanza StudentNotFoundException → 404

        studentRepository.deleteById(id)
        // DELETE FROM student WHERE id = ?
        // No devuelve nada — el controller responde HTTP 204
    }
}