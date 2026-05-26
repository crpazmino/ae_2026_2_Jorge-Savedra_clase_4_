package com.example.demo.services

import com.example.demo.dto.StudentRequest
import com.example.demo.dto.StudentResponse
import com.example.demo.entities.Student
import com.example.demo.repositories.StudentRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
open class StudentService(
    private val studentRepository: StudentRepository
) {
    private val logger = LoggerFactory.getLogger(StudentService::class.java)

    open fun createStudent(request: StudentRequest): StudentResponse {
        logger.info("Creating student ${request.name}")

        val studentEntity = Student(
            name = request.name,
            email = request.email
        )

        val savedStudent = studentRepository.save(studentEntity)

        return StudentResponse(
            id = savedStudent.id,
            name = savedStudent.name,
            email = savedStudent.email
        )
    }

    open fun getAllStudents(): List<StudentResponse> {
        logger.info("Getting all students")
        val savedStudents = studentRepository.findAll()

        return savedStudents.map {
            StudentResponse(
                id = it.id,
                name = it.name,
                email = it.email
            )
        }
    }
}