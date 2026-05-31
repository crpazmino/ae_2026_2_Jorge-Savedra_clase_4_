package com.example.demo.controllers

import com.example.demo.dto.StudentRequest
import com.example.demo.dto.StudentResponse
import com.example.demo.services.StudentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/students")
class StudentController(
    private val studentService: StudentService
) {
    @PostMapping
    fun createStudent(@RequestBody request: StudentRequest): StudentResponse {
        return studentService.createStudent(request)
    }

    @GetMapping
    fun getAllStudents(): List<StudentResponse> {
        return studentService.getAllStudents()
    }
}