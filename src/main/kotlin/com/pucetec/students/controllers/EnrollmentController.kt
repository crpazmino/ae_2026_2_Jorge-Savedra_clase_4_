package com.pucetec.students.controllers

import com.pucetec.students.dto.EnrollmentRequest
import com.pucetec.students.dto.EnrollmentResponse
import com.pucetec.students.services.EnrollmentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/enrollments")
class EnrollmentController(
    private val enrollmentService: EnrollmentService
) {
    @PostMapping
    fun createEnrollment(@RequestBody request: EnrollmentRequest): EnrollmentResponse =
        enrollmentService.createEnrollment(request)

    @GetMapping
    fun getAllEnrollments(): List<EnrollmentResponse> =
        enrollmentService.getAllEnrollments()
}