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
open class StudentController(
    val studentService: StudentService
) {
    private val logger = LoggerFactory.getLogger(StudentController::class.java)

    @PostMapping("/api/students")
    open fun createStudent(@RequestBody request: StudentRequest): StudentResponse {
        logger.info("Creating student ${request.name}")
        return studentService.createStudent(request)
    }

    @GetMapping("/api/students")
    open fun getAllStudents(): List<StudentResponse> {
        logger.info("Getting all students")
        return studentService.getAllStudents()
    }
}