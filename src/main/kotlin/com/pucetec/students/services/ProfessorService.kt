package com.pucetec.students.services

import com.pucetec.students.dto.ProfessorRequest
import com.pucetec.students.dto.ProfessorResponse
import com.pucetec.students.exceptions.BlankNameException
import com.pucetec.students.exceptions.ProfessorNotFoundException
import com.pucetec.students.mappers.toEntity
import com.pucetec.students.mappers.toResponse
import com.pucetec.students.repositories.ProfessorRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ProfessorService(
    private val professorRepository: ProfessorRepository
) {
    private val logger = LoggerFactory.getLogger(ProfessorService::class.java)

    fun createProfessor(request: ProfessorRequest): ProfessorResponse {
        if (request.name.isBlank()) throw BlankNameException("El nombre no puede estar vacío")
        logger.info("Creando profesor: ${request.email}")
        return professorRepository.save(request.toEntity()).toResponse()
    }

    fun getAllProfessors(): List<ProfessorResponse> {
        logger.info("Obteniendo todos los profesores")
        return professorRepository.findAll().map { it.toResponse() }
    }

    fun getProfessorById(id: Long): ProfessorResponse {
        val professor = professorRepository.findById(id)
            .orElseThrow { ProfessorNotFoundException("Profesor no encontrado con id: $id") }
        return professor.toResponse()
    }
}