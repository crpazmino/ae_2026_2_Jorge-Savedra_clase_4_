package com.pucetec.students.controllers

import com.pucetec.students.dto.ProfessorRequest
import com.pucetec.students.dto.ProfessorResponse
import com.pucetec.students.services.ProfessorService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/professors")
class ProfessorController(
    private val professorService: ProfessorService
) {
    @PostMapping
    fun createProfessor(@RequestBody request: ProfessorRequest): ProfessorResponse =
        professorService.createProfessor(request)

    @GetMapping
    fun getAllProfessors(): List<ProfessorResponse> =
        professorService.getAllProfessors()

    @GetMapping("/{id}")
    fun getProfessorById(@PathVariable id: Long): ProfessorResponse =
        professorService.getProfessorById(id)
}