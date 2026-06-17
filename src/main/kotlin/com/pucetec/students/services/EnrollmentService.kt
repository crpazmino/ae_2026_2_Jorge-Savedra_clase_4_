package com.pucetec.students.services

import com.pucetec.students.dto.EnrollmentRequest
import com.pucetec.students.dto.EnrollmentResponse
import com.pucetec.students.dto.EnrollmentUpdateRequest
import com.pucetec.students.entities.Enrollment
import com.pucetec.students.exceptions.EnrollmentNotFoundException
import com.pucetec.students.exceptions.StudentNotFoundException
import com.pucetec.students.exceptions.SubjectNotFoundException
import com.pucetec.students.mappers.toResponse
import com.pucetec.students.repositories.EnrollmentRepository
import com.pucetec.students.repositories.StudentRepository
import com.pucetec.students.repositories.SubjectRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EnrollmentService(
    private val enrollmentRepository: EnrollmentRepository,
    private val studentRepository: StudentRepository,
    private val subjectRepository: SubjectRepository
) {
    private val logger = LoggerFactory.getLogger(EnrollmentService::class.java)

    fun createEnrollment(request: EnrollmentRequest): EnrollmentResponse {
        val student = studentRepository.findById(request.studentId)
            .orElseThrow { StudentNotFoundException("Estudiante no encontrado con id: ${request.studentId}") }
        val subject = subjectRepository.findById(request.subjectId)
            .orElseThrow { SubjectNotFoundException("Materia no encontrada con id: ${request.subjectId}") }
        logger.info("Creando enrollment para estudiante: ${student.name}")
        return enrollmentRepository.save(Enrollment(student = student, subject = subject)).toResponse()
    }

    fun getAllEnrollments(): List<EnrollmentResponse> {
        logger.info("Obteniendo todos los enrollments")
        return enrollmentRepository.findAll().map { it.toResponse() }
    }

    fun getEnrollmentById(id: Long): EnrollmentResponse {
        val enrollment = enrollmentRepository.findById(id)
            .orElseThrow { EnrollmentNotFoundException("Inscripción no encontrada con id: $id") }
        return enrollment.toResponse()
    }

    fun updateEnrollment(id: Long, request: EnrollmentUpdateRequest): EnrollmentResponse {
        val enrollment = enrollmentRepository.findById(id)
            .orElseThrow { EnrollmentNotFoundException("Inscripción no encontrada con id: $id") }
        val updated = enrollmentRepository.save(
            Enrollment(
                id = enrollment.id,
                student = enrollment.student,
                subject = enrollment.subject,
                status = request.status,
                createdAt = enrollment.createdAt
            )
        )
        return updated.toResponse()
    }

    fun deleteEnrollment(id: Long) {
        if (!enrollmentRepository.existsById(id)) throw EnrollmentNotFoundException("Inscripción no encontrada con id: $id")
        enrollmentRepository.deleteById(id)
    }
}