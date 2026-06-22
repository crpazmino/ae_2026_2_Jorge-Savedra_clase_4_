package com.pucetec.students.services

import com.pucetec.students.dto.SubjectRequest
import com.pucetec.students.dto.SubjectResponse
// DTOs de entrada y salida — Subject solo necesita 2 (no tiene UpdateRequest)

import com.pucetec.students.entities.Subject
// Entidad que se guarda en la base de datos

import com.pucetec.students.exceptions.BlankNameException
import com.pucetec.students.exceptions.ProfessorNotFoundException
import com.pucetec.students.exceptions.SubjectNotFoundException
// 3 excepciones:
// BlankNameException       → nombre vacío → 400
// ProfessorNotFoundException→ professorId no existe → 404
// SubjectNotFoundException  → subjectId no existe → 404

import com.pucetec.students.mappers.toEntity
// Convierte SubjectRequest → Subject entity (necesita Professor completo)

import com.pucetec.students.mappers.toResponse
// Convierte Subject entity → SubjectResponse DTO

import com.pucetec.students.repositories.ProfessorRepository
import com.pucetec.students.repositories.SubjectRepository
// 2 repositorios — SubjectService necesita verificar que el Professor existe

import org.slf4j.LoggerFactory
// Para crear logs informativos en la consola

import org.springframework.stereotype.Service
// Le dice a Spring que esta clase es un Service

@Service
// Spring crea una instancia única (Singleton)
// Se inyecta en SubjectController

class SubjectService(
    private val subjectRepository: SubjectRepository,
    // Para operaciones CRUD de materias

    private val professorRepository: ProfessorRepository
    // Para verificar que el profesor existe antes de crear/actualizar la materia
) {
    private val logger = LoggerFactory.getLogger(SubjectService::class.java)
    // Logger asociado a esta clase

    fun createSubject(request: SubjectRequest): SubjectResponse {
        if (request.name.isBlank()) throw BlankNameException("El nombre no puede estar vacío")
        // Valida que el nombre no esté en blanco antes de continuar
        // Si falla → lanza BlankNameException → 400

        val professor = professorRepository.findById(request.professorId)
            .orElseThrow { ProfessorNotFoundException("Profesor no encontrado con id: ${request.professorId}") }
        // Busca el profesor por ID en la BD
        // Si no existe → lanza ProfessorNotFoundException → 404
        // Si existe → guarda el objeto Professor completo en val professor

        logger.info("Creando materia: ${request.name}")
        // Log informativo en consola

        return subjectRepository.save(request.toEntity(professor)).toResponse()
        // request.toEntity(professor) → convierte SubjectRequest a Subject entity
        //                               pasando el Professor completo
        // .save()                    → INSERT en la tabla subject
        // .toResponse()              → convierte Subject entity a SubjectResponse DTO
    }

    fun getAllSubjects(): List<SubjectResponse> {
        logger.info("Obteniendo todas las materias")
        return subjectRepository.findAll().map { it.toResponse() }
        // findAll()    → SELECT * FROM subject (incluye professor via JOIN)
        // .map { }     → convierte cada Subject entity a SubjectResponse DTO
    }

    fun getSubjectById(id: Long): SubjectResponse {
        val subject = subjectRepository.findById(id)
            .orElseThrow { SubjectNotFoundException("Materia no encontrada con id: $id") }
        // Busca la materia por ID
        // Si no existe → lanza SubjectNotFoundException → 404

        return subject.toResponse()
        // Convierte la entity a DTO incluyendo el Professor anidado
    }

    fun updateSubject(id: Long, request: SubjectRequest): SubjectResponse {
        subjectRepository.findById(id)
            .orElseThrow { SubjectNotFoundException("Materia no encontrada con id: $id") }
        // Verifica que la materia existe
        // Si no existe → lanza SubjectNotFoundException → 404

        if (request.name.isBlank()) throw BlankNameException("El nombre no puede estar vacío")
        // Valida el nombre DESPUÉS de verificar que existe el ID

        val professor = professorRepository.findById(request.professorId)
            .orElseThrow { ProfessorNotFoundException("Profesor no encontrado con id: ${request.professorId}") }
        // Verifica que el nuevo profesor existe
        // Permite cambiar el profesor asignado a la materia al actualizar

        val updated = subjectRepository.save(
            Subject(
                id = id,
                // Mantiene el mismo ID → Hibernate hace UPDATE
                name = request.name,
                // Actualiza el nombre
                code = request.code,
                // Actualiza el código
                professor = professor
                // Actualiza el profesor — puede cambiar a uno diferente
            )
        )
        return updated.toResponse()
    }

    fun deleteSubject(id: Long) {
        if (!subjectRepository.existsById(id))
            throw SubjectNotFoundException("Materia no encontrada con id: $id")
        // Verifica si existe antes de eliminar
        // Si no existe → lanza SubjectNotFoundException → 404

        subjectRepository.deleteById(id)
        // DELETE FROM subject WHERE id = ?
        // No devuelve nada — el controller responde HTTP 204
    }
}