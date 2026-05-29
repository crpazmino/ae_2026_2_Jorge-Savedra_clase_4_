package com.example.demo.controllers

import com.example.demo.dto.StudentRequest
import com.example.demo.dto.StudentResponse
import com.example.demo.services.StudentService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class StudentController(
    val studentService: StudentService
) {
    private val logger = LoggerFactory.getLogger(StudentController::class.java)

    @PostMapping(value = ["/api/students"])
    fun createStudent(
        @RequestBody request: StudentRequest
    ): StudentResponse {
        logger.info("Creando estudiante ${request.name}")
        return studentService.createStudent(request)
    }

    @GetMapping(value = ["/api/students"])
    fun getAllStudents(): List<StudentResponse> {
        logger.info("Tomando a todos los estudiantes")
        return studentService.getAllStudents()
    }
}