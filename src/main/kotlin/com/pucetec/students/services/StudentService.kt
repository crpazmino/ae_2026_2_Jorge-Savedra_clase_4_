package com.pucetec.students.services

import com.pucetec.students.dto.StudentRequest
import com.pucetec.students.dto.StudentResponse
import com.pucetec.students.exceptions.BlankNameException
import com.pucetec.students.exceptions.StudentNotFoundException
import com.pucetec.students.mappers.toEntity
import com.pucetec.students.mappers.toResponse
import com.pucetec.students.repositories.StudentRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val studentRepository: StudentRepository
) {
    private val logger = LoggerFactory.getLogger(StudentService::class.java)

    fun createStudent(request: StudentRequest): StudentResponse {
        if (request.name.isBlank()) throw BlankNameException("El nombre no puede estar vacío")
        logger.info("Creando estudiante: ${request.email}")
        return studentRepository.save(request.toEntity()).toResponse()
    }

    fun getAllStudents(): List<StudentResponse> {
        logger.info("Obteniendo todos los estudiantes")
        return studentRepository.findAll().map { it.toResponse() }
    }

    fun getStudentById(id: Long): StudentResponse {
        val student = studentRepository.findById(id)
            .orElseThrow { StudentNotFoundException("Estudiante no encontrado con id: $id") }
        return student.toResponse()
    }
}