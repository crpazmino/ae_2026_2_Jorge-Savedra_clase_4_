package com.pucetec.students.services

import com.pucetec.students.dto.SubjectRequest
import com.pucetec.students.dto.SubjectResponse
import com.pucetec.students.entities.Subject
import com.pucetec.students.exceptions.BlankNameException
import com.pucetec.students.exceptions.ProfessorNotFoundException
import com.pucetec.students.exceptions.SubjectNotFoundException
import com.pucetec.students.mappers.toEntity
import com.pucetec.students.mappers.toResponse
import com.pucetec.students.repositories.ProfessorRepository
import com.pucetec.students.repositories.SubjectRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SubjectService(
    private val subjectRepository: SubjectRepository,
    private val professorRepository: ProfessorRepository
) {
    private val logger = LoggerFactory.getLogger(SubjectService::class.java)

    fun createSubject(request: SubjectRequest): SubjectResponse {
        if (request.name.isBlank()) throw BlankNameException("El nombre no puede estar vacío")
        if (request.code.isBlank()) throw BlankNameException("El código no puede estar vacío")
        val professor = professorRepository.findById(request.professorId)
            .orElseThrow { ProfessorNotFoundException("Profesor no encontrado con id: ${request.professorId}") }
        logger.info("Creando materia: ${request.name}")
        return subjectRepository.save(request.toEntity(professor)).toResponse()
    }

    fun getAllSubjects(): List<SubjectResponse> {
        logger.info("Obteniendo todas las materias")
        return subjectRepository.findAll().map { it.toResponse() }
    }

    fun getSubjectById(id: Long): SubjectResponse {
        val subject = subjectRepository.findById(id)
            .orElseThrow { SubjectNotFoundException("Materia no encontrada con id: $id") }
        return subject.toResponse()
    }

    fun updateSubject(id: Long, request: SubjectRequest): SubjectResponse {
        val subject = subjectRepository.findById(id)
            .orElseThrow { SubjectNotFoundException("Materia no encontrada con id: $id") }
        if (request.name.isBlank()) throw BlankNameException("El nombre no puede estar vacío")
        if (request.code.isBlank()) throw BlankNameException("El código no puede estar vacío")
        val professor = professorRepository.findById(request.professorId)
            .orElseThrow { ProfessorNotFoundException("Profesor no encontrado con id: ${request.professorId}") }
        val updated = subjectRepository.save(Subject(id = subject.id, name = request.name, code = request.code, professor = professor))
        return updated.toResponse()
    }

    fun deleteSubject(id: Long) {
        if (!subjectRepository.existsById(id)) throw SubjectNotFoundException("Materia no encontrada con id: $id")
        subjectRepository.deleteById(id)
    }
}