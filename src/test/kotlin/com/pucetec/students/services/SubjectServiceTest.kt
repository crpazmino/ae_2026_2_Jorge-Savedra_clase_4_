package com.pucetec.students.services

import com.pucetec.students.dto.SubjectRequest
import com.pucetec.students.entities.Professor
import com.pucetec.students.entities.Subject
import com.pucetec.students.exceptions.BlankNameException
import com.pucetec.students.exceptions.ProfessorNotFoundException
import com.pucetec.students.exceptions.SubjectNotFoundException
import com.pucetec.students.repositories.ProfessorRepository
import com.pucetec.students.repositories.SubjectRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class SubjectServiceTest {

    @Mock
    private lateinit var subjectRepository: SubjectRepository

    @Mock
    private lateinit var professorRepository: ProfessorRepository

    @InjectMocks
    private lateinit var subjectService: SubjectService

    // ─────────────────────────────────────────
    // TESTS DE createSubject
    // ─────────────────────────────────────────

    @Test
    fun `createSubject retorna respuesta cuando el nombre es valido y profesor existe`() {
        val professor = Professor(id = 1L, name = "Dr. Lopez", email = "lopez@puce.edu.ec")
        val request = SubjectRequest(name = "Matematicas", code = "MAT101", professorId = 1L)
        val savedSubject = Subject(id = 1L, name = "Matematicas", code = "MAT101", professor = professor)

        `when`(professorRepository.findById(1L)).thenReturn(Optional.of(professor))
        `when`(subjectRepository.save(any())).thenReturn(savedSubject)

        val response = subjectService.createSubject(request)

        assertNotNull(response)
        assertEquals(1L, response.id)
        assertEquals("Matematicas", response.name)
        assertEquals("MAT101", response.code)
        assertEquals("Dr. Lopez", response.professor?.name)
    }

    @Test
    fun `createSubject lanza BlankNameException cuando el nombre esta vacio`() {
        val request = SubjectRequest(name = "", code = "MAT101", professorId = 1L)

        assertThrows<BlankNameException> {
            subjectService.createSubject(request)
        }
    }

    @Test
    fun `createSubject lanza BlankNameException cuando el nombre tiene solo espacios`() {
        val request = SubjectRequest(name = "   ", code = "MAT101", professorId = 1L)

        assertThrows<BlankNameException> {
            subjectService.createSubject(request)
        }
    }

    @Test
    fun `createSubject lanza ProfessorNotFoundException cuando el profesor no existe`() {
        val request = SubjectRequest(name = "Matematicas", code = "MAT101", professorId = 99L)

        `when`(professorRepository.findById(99L)).thenReturn(Optional.empty())

        assertThrows<ProfessorNotFoundException> {
            subjectService.createSubject(request)
        }
    }

    // ─────────────────────────────────────────
    // TESTS DE getAllSubjects
    // ─────────────────────────────────────────

    @Test
    fun `getAllSubjects retorna lista de materias`() {
        val professor = Professor(id = 1L, name = "Dr. Lopez", email = "lopez@puce.edu.ec")
        val subjects = listOf(
            Subject(id = 1L, name = "Matematicas", code = "MAT101", professor = professor),
            Subject(id = 2L, name = "Fisica", code = "FIS101", professor = professor)
        )

        `when`(subjectRepository.findAll()).thenReturn(subjects)

        val response = subjectService.getAllSubjects()

        assertEquals(2, response.size)
        assertEquals("Matematicas", response[0].name)
        assertEquals("Fisica", response[1].name)
    }

    @Test
    fun `getAllSubjects retorna lista vacia cuando no hay materias`() {
        `when`(subjectRepository.findAll()).thenReturn(emptyList())

        val response = subjectService.getAllSubjects()

        assertEquals(0, response.size)
    }

    // ─────────────────────────────────────────
    // TESTS DE getSubjectById
    // ─────────────────────────────────────────

    @Test
    fun `getSubjectById retorna materia cuando existe`() {
        val professor = Professor(id = 1L, name = "Dr. Lopez", email = "lopez@puce.edu.ec")
        val subject = Subject(id = 1L, name = "Matematicas", code = "MAT101", professor = professor)

        `when`(subjectRepository.findById(1L)).thenReturn(Optional.of(subject))

        val response = subjectService.getSubjectById(1L)

        assertEquals(1L, response.id)
        assertEquals("Matematicas", response.name)
    }

    @Test
    fun `getSubjectById lanza SubjectNotFoundException cuando no existe`() {
        `when`(subjectRepository.findById(99L)).thenReturn(Optional.empty())

        assertThrows<SubjectNotFoundException> {
            subjectService.getSubjectById(99L)
        }
    }

    // ─────────────────────────────────────────
    // TESTS DE updateSubject
    // ─────────────────────────────────────────

    @Test
    fun `updateSubject retorna materia actualizada cuando existe, nombre es valido y profesor existe`() {
        val professor = Professor(id = 1L, name = "Dr. Lopez", email = "lopez@puce.edu.ec")
        val existingSubject = Subject(id = 1L, name = "Matematicas", code = "MAT101", professor = professor)
        val request = SubjectRequest(name = "Matematicas Avanzadas", code = "MAT202", professorId = 1L)
        val updatedSubject = Subject(id = 1L, name = "Matematicas Avanzadas", code = "MAT202", professor = professor)

        `when`(subjectRepository.findById(1L)).thenReturn(Optional.of(existingSubject))
        `when`(professorRepository.findById(1L)).thenReturn(Optional.of(professor))
        `when`(subjectRepository.save(any())).thenReturn(updatedSubject)

        val response = subjectService.updateSubject(1L, request)

        assertEquals(1L, response.id)
        assertEquals("Matematicas Avanzadas", response.name)
        assertEquals("MAT202", response.code)
    }

    @Test
    fun `updateSubject lanza SubjectNotFoundException cuando la materia no existe`() {
        val request = SubjectRequest(name = "Matematicas", code = "MAT101", professorId = 1L)

        `when`(subjectRepository.findById(99L)).thenReturn(Optional.empty())

        assertThrows<SubjectNotFoundException> {
            subjectService.updateSubject(99L, request)
        }
    }

    @Test
    fun `updateSubject lanza BlankNameException cuando el nombre esta vacio`() {
        val professor = Professor(id = 1L, name = "Dr. Lopez", email = "lopez@puce.edu.ec")
        val existingSubject = Subject(id = 1L, name = "Matematicas", code = "MAT101", professor = professor)
        val request = SubjectRequest(name = "", code = "MAT101", professorId = 1L)

        `when`(subjectRepository.findById(1L)).thenReturn(Optional.of(existingSubject))

        assertThrows<BlankNameException> {
            subjectService.updateSubject(1L, request)
        }
    }

    @Test
    fun `updateSubject lanza ProfessorNotFoundException cuando el profesor no existe`() {
        val professor = Professor(id = 1L, name = "Dr. Lopez", email = "lopez@puce.edu.ec")
        val existingSubject = Subject(id = 1L, name = "Matematicas", code = "MAT101", professor = professor)
        val request = SubjectRequest(name = "Matematicas", code = "MAT101", professorId = 99L)

        `when`(subjectRepository.findById(1L)).thenReturn(Optional.of(existingSubject))
        `when`(professorRepository.findById(99L)).thenReturn(Optional.empty())

        assertThrows<ProfessorNotFoundException> {
            subjectService.updateSubject(1L, request)
        }
    }

    // ─────────────────────────────────────────
    // TESTS DE deleteSubject
    // ─────────────────────────────────────────

    @Test
    fun `deleteSubject elimina exitosamente cuando existe`() {
        `when`(subjectRepository.existsById(1L)).thenReturn(true)

        subjectService.deleteSubject(1L)

        verify(subjectRepository).deleteById(1L)
    }

    @Test
    fun `deleteSubject lanza SubjectNotFoundException cuando no existe`() {
        `when`(subjectRepository.existsById(99L)).thenReturn(false)

        assertThrows<SubjectNotFoundException> {
            subjectService.deleteSubject(99L)
        }
    }
}
