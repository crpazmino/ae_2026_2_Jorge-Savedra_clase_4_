package com.pucetec.students.services

import com.pucetec.students.dto.ProfessorRequest
import com.pucetec.students.entities.Professor
import com.pucetec.students.exceptions.BlankNameException
import com.pucetec.students.exceptions.ProfessorNotFoundException
import com.pucetec.students.repositories.ProfessorRepository
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
class ProfessorServiceTest {

    @Mock
    private lateinit var professorRepository: ProfessorRepository

    @InjectMocks
    private lateinit var professorService: ProfessorService

    // ─────────────────────────────────────────
    // TESTS DE createProfessor
    // ─────────────────────────────────────────

    @Test
    fun `createProfessor retorna respuesta cuando el nombre es valido`() {
        val request = ProfessorRequest(name = "Dr. Lopez", email = "lopez@puce.edu.ec")
        val savedProfessor = Professor(id = 1L, name = "Dr. Lopez", email = "lopez@puce.edu.ec")

        `when`(professorRepository.save(any())).thenReturn(savedProfessor)

        val response = professorService.createProfessor(request)

        assertNotNull(response)
        assertEquals(1L, response.id)
        assertEquals("Dr. Lopez", response.name)
        assertEquals("lopez@puce.edu.ec", response.email)
    }

    @Test
    fun `createProfessor lanza BlankNameException cuando el nombre esta vacio`() {
        val request = ProfessorRequest(name = "", email = "test@puce.edu.ec")

        assertThrows<BlankNameException> {
            professorService.createProfessor(request)
        }
    }

    @Test
    fun `createProfessor lanza BlankNameException cuando el nombre tiene solo espacios`() {
        val request = ProfessorRequest(name = "   ", email = "test@puce.edu.ec")

        assertThrows<BlankNameException> {
            professorService.createProfessor(request)
        }
    }

    // ─────────────────────────────────────────
    // TESTS DE getAllProfessors
    // ─────────────────────────────────────────

    @Test
    fun `getAllProfessors retorna lista de profesores`() {
        val professors = listOf(
            Professor(id = 1L, name = "Dr. Lopez", email = "lopez@puce.edu.ec"),
            Professor(id = 2L, name = "Dr. Morales", email = "morales@puce.edu.ec")
        )

        `when`(professorRepository.findAll()).thenReturn(professors)

        val response = professorService.getAllProfessors()

        assertEquals(2, response.size)
        assertEquals("Dr. Lopez", response[0].name)
        assertEquals("Dr. Morales", response[1].name)
    }

    @Test
    fun `getAllProfessors retorna lista vacia cuando no hay profesores`() {
        `when`(professorRepository.findAll()).thenReturn(emptyList())

        val response = professorService.getAllProfessors()

        assertEquals(0, response.size)
    }

    // ─────────────────────────────────────────
    // TESTS DE getProfessorById
    // ─────────────────────────────────────────

    @Test
    fun `getProfessorById retorna profesor cuando existe`() {
        val professor = Professor(id = 1L, name = "Dr. Lopez", email = "lopez@puce.edu.ec")

        `when`(professorRepository.findById(1L)).thenReturn(Optional.of(professor))

        val response = professorService.getProfessorById(1L)

        assertEquals(1L, response.id)
        assertEquals("Dr. Lopez", response.name)
    }

    @Test
    fun `getProfessorById lanza ProfessorNotFoundException cuando no existe`() {
        `when`(professorRepository.findById(99L)).thenReturn(Optional.empty())

        assertThrows<ProfessorNotFoundException> {
            professorService.getProfessorById(99L)
        }
    }

    // ─────────────────────────────────────────
    // TESTS DE updateProfessor
    // ─────────────────────────────────────────

    @Test
    fun `updateProfessor retorna profesor actualizado cuando existe y nombre es valido`() {
        val existingProfessor = Professor(id = 1L, name = "Dr. Lopez", email = "lopez@puce.edu.ec")
        val request = ProfessorRequest(name = "Dr. Lopez Actualizado", email = "nuevo@puce.edu.ec")
        val updatedProfessor = Professor(id = 1L, name = "Dr. Lopez Actualizado", email = "nuevo@puce.edu.ec")

        `when`(professorRepository.findById(1L)).thenReturn(Optional.of(existingProfessor))
        `when`(professorRepository.save(any())).thenReturn(updatedProfessor)

        val response = professorService.updateProfessor(1L, request)

        assertEquals(1L, response.id)
        assertEquals("Dr. Lopez Actualizado", response.name)
        assertEquals("nuevo@puce.edu.ec", response.email)
    }

    @Test
    fun `updateProfessor lanza ProfessorNotFoundException cuando no existe`() {
        val request = ProfessorRequest(name = "Dr. Lopez", email = "lopez@puce.edu.ec")

        `when`(professorRepository.findById(99L)).thenReturn(Optional.empty())

        assertThrows<ProfessorNotFoundException> {
            professorService.updateProfessor(99L, request)
        }
    }

    @Test
    fun `updateProfessor lanza BlankNameException cuando el nombre esta vacio`() {
        val existingProfessor = Professor(id = 1L, name = "Dr. Lopez", email = "lopez@puce.edu.ec")
        val request = ProfessorRequest(name = "", email = "lopez@puce.edu.ec")

        `when`(professorRepository.findById(1L)).thenReturn(Optional.of(existingProfessor))

        assertThrows<BlankNameException> {
            professorService.updateProfessor(1L, request)
        }
    }

    // ─────────────────────────────────────────
    // TESTS DE deleteProfessor
    // ─────────────────────────────────────────

    @Test
    fun `deleteProfessor elimina exitosamente cuando existe`() {
        `when`(professorRepository.existsById(1L)).thenReturn(true)

        professorService.deleteProfessor(1L)

        verify(professorRepository).deleteById(1L)
    }

    @Test
    fun `deleteProfessor lanza ProfessorNotFoundException cuando no existe`() {
        `when`(professorRepository.existsById(99L)).thenReturn(false)

        assertThrows<ProfessorNotFoundException> {
            professorService.deleteProfessor(99L)
        }
    }
}
