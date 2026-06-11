package com.pucetec.students.services

import com.pucetec.students.dto.EnrollmentRequest
import com.pucetec.students.dto.EnrollmentResponse
import com.pucetec.students.entities.Enrollment
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
}