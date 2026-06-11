package com.pucetec.students.controllers

import com.pucetec.students.dto.SubjectRequest
import com.pucetec.students.dto.SubjectResponse
import com.pucetec.students.services.SubjectService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/subjects")
class SubjectController(
    private val subjectService: SubjectService
) {
    @PostMapping
    fun createSubject(@RequestBody request: SubjectRequest): SubjectResponse =
        subjectService.createSubject(request)

    @GetMapping
    fun getAllSubjects(): List<SubjectResponse> =
        subjectService.getAllSubjects()

    @GetMapping("/{id}")
    fun getSubjectById(@PathVariable id: Long): SubjectResponse =
        subjectService.getSubjectById(id)
}