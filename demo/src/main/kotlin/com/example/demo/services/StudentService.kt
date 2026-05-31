package com.example.demo.services

import com.example.demo.dto.StudentRequest
import com.example.demo.dto.StudentResponse
import com.example.demo.exceptions.EmailAlreadyExistsException
import com.example.demo.mappers.toEntity
import com.example.demo.mappers.toResponse
import com.example.demo.repositories.StudentRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val studentRepository: StudentRepository
) {
    private val logger = LoggerFactory.getLogger(StudentService::class.java)

    fun createStudent(request: StudentRequest): StudentResponse {
        logger.info("Creando estudiante con email: ${request.email}")
        if (studentRepository.existsByEmail(request.email)) {
            throw EmailAlreadyExistsException("El email ya existe: ${request.email}")
        }
        return studentRepository.save(request.toEntity()).toResponse()
    }

    fun getAllStudents(): List<StudentResponse> {
        logger.info("Tomando todos los estudiantes")
        return studentRepository.findAll().map { it.toResponse() }
    }
}