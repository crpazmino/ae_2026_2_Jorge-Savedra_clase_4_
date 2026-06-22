package com.pucetec.students.services
// Paquete donde viven todos los services del proyecto

import com.pucetec.students.dto.EnrollmentRequest
import com.pucetec.students.dto.EnrollmentResponse
import com.pucetec.students.dto.EnrollmentUpdateRequest
// Los 3 DTOs que usa este service — Request, UpdateRequest y Response

import com.pucetec.students.entities.Enrollment
// Entidad que se guarda en la base de datos

import com.pucetec.students.exceptions.EnrollmentNotFoundException
import com.pucetec.students.exceptions.StudentNotFoundException
import com.pucetec.students.exceptions.SubjectNotFoundException
// 3 excepciones — EnrollmentService es el único service que usa
// excepciones de otras entidades (Student y Subject)

import com.pucetec.students.mappers.toResponse
// Función de extensión para convertir Enrollment entity a DTO

import com.pucetec.students.repositories.EnrollmentRepository
import com.pucetec.students.repositories.StudentRepository
import com.pucetec.students.repositories.SubjectRepository
// 3 repositorios — EnrollmentService necesita acceder a 3 tablas

import org.slf4j.LoggerFactory
// Para crear logs informativos en la consola

import org.springframework.stereotype.Service
// Le dice a Spring que esta clase es un Service

@Service
// Spring crea una instancia única de esta clase (Singleton)
// y la inyecta donde sea necesaria (en EnrollmentController)

class EnrollmentService(
    private val enrollmentRepository: EnrollmentRepository,
    // Para operaciones CRUD de inscripciones

    private val studentRepository: StudentRepository,
    // Para verificar que el estudiante existe antes de inscribir

    private val subjectRepository: SubjectRepository
    // Para verificar que la materia existe antes de inscribir
) {
    private val logger = LoggerFactory.getLogger(EnrollmentService::class.java)
    // Crea un logger asociado a esta clase
    // Permite ver mensajes informativos en la consola al ejecutar
    // Ejemplo: "Creando enrollment para estudiante: Carlos"

    fun createEnrollment(request: EnrollmentRequest): EnrollmentResponse {
        // Recibe studentId y subjectId del cliente

        val student = studentRepository.findById(request.studentId)
            .orElseThrow { StudentNotFoundException("Estudiante no encontrado con id: ${request.studentId}") }
        // Busca el estudiante por ID en la BD
        // Si no existe → lanza StudentNotFoundException → 404
        // Si existe → guarda el objeto Student completo en val student

        val subject = subjectRepository.findById(request.subjectId)
            .orElseThrow { SubjectNotFoundException("Materia no encontrada con id: ${request.subjectId}") }
        // Busca la materia por ID en la BD
        // Si no existe → lanza SubjectNotFoundException → 404
        // Si existe → guarda el objeto Subject completo en val subject

        logger.info("Creando enrollment para estudiante: ${student.name}")
        // Imprime en consola: "Creando enrollment para estudiante: Carlos"
        // Útil para debugging y monitoreo

        return enrollmentRepository.save(
            Enrollment(student = student, subject = subject)
            // Crea el Enrollment con student y subject completos
            // status = "INSCRITO" por defecto (definido en la Entity)
            // createdAt = fecha actual por defecto (definido en la Entity)
        ).toResponse()
        // Guarda en BD y convierte el resultado a EnrollmentResponse
    }

    fun getAllEnrollments(): List<EnrollmentResponse> {
        logger.info("Obteniendo todos los enrollments")
        // Log informativo en consola

        return enrollmentRepository.findAll().map { it.toResponse() }
        // findAll() → trae todos los enrollments de la BD
        // .map { it.toResponse() } → convierte cada Enrollment a DTO
        // Devuelve una lista de EnrollmentResponse
    }

    fun getEnrollmentById(id: Long): EnrollmentResponse {
        val enrollment = enrollmentRepository.findById(id)
            .orElseThrow { EnrollmentNotFoundException("Inscripción no encontrada con id: $id") }
        // Busca el enrollment por ID
        // Si no existe → lanza EnrollmentNotFoundException → 404

        return enrollment.toResponse()
        // Convierte la entity a DTO y devuelve al controller
    }

    fun updateEnrollment(id: Long, request: EnrollmentUpdateRequest): EnrollmentResponse {
        val enrollment = enrollmentRepository.findById(id)
            .orElseThrow { EnrollmentNotFoundException("Inscripción no encontrada con id: $id") }
        // Busca el enrollment existente
        // Si no existe → lanza EnrollmentNotFoundException → 404

        val updated = enrollmentRepository.save(
            Enrollment(
                id = enrollment.id,
                // Mantiene el mismo ID — actualiza el registro existente
                student = enrollment.student,
                // Mantiene el mismo estudiante — no se puede cambiar
                subject = enrollment.subject,
                // Mantiene la misma materia — no se puede cambiar
                status = request.status,
                // SOLO cambia el status — el único campo editable
                createdAt = enrollment.createdAt
                // Mantiene la fecha original — no se actualiza
            )
        )
        return updated.toResponse()
        // Devuelve el enrollment actualizado como DTO
    }

    fun deleteEnrollment(id: Long) {
        if (!enrollmentRepository.existsById(id))
            throw EnrollmentNotFoundException("Inscripción no encontrada con id: $id")
        // Verifica si existe antes de eliminar
        // Si no existe → lanza EnrollmentNotFoundException → 404
        // Diferente a los otros services que usan findById + orElseThrow

        enrollmentRepository.deleteById(id)
        // Elimina el enrollment de la BD
        // No devuelve nada — el controller responde HTTP 204
    }
}