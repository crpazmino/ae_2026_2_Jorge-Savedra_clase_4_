package com.pucetec.students.controllers

import com.pucetec.students.dto.StudentRequest
import com.pucetec.students.dto.StudentResponse
import com.pucetec.students.services.StudentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/students")
class StudentController(
    private val studentService: StudentService
) {
    @PostMapping
    fun createStudent(@RequestBody request: StudentRequest): StudentResponse =
        studentService.createStudent(request)

    @GetMapping
    fun getAllStudents(): List<StudentResponse> =
        studentService.getAllStudents()

    @GetMapping("/{id}")
    fun getStudentById(@PathVariable id: Long): StudentResponse =
        studentService.getStudentById(id)
}
