package com.pucetec.students.services

import com.pucetec.students.dto.EnrollmentRequest
import com.pucetec.students.dto.EnrollmentUpdateRequest
import com.pucetec.students.entities.Enrollment
import com.pucetec.students.entities.Professor
import com.pucetec.students.entities.Student
import com.pucetec.students.entities.Subject
import com.pucetec.students.exceptions.EnrollmentNotFoundException
import com.pucetec.students.exceptions.StudentNotFoundException
import com.pucetec.students.exceptions.SubjectNotFoundException
import com.pucetec.students.repositories.EnrollmentRepository
import com.pucetec.students.repositories.StudentRepository
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
class EnrollmentServiceTest {

    @Mock
    private lateinit var enrollmentRepository: EnrollmentRepository

    @Mock
    private lateinit var studentRepository: StudentRepository

    @Mock
    private lateinit var subjectRepository: SubjectRepository

    @InjectMocks
    private lateinit var enrollmentService: EnrollmentService

    // ─────────────────────────────────────────
    // TESTS DE createEnrollment
    // ─────────────────────────────────────────

    @Test
    fun `createEnrollment retorna respuesta cuando estudiante y materia existen`() {
        val professor = Professor(id = 1L, name = "Dr. Lopez", email = "lopez@puce.edu.ec")
        val student = Student(id = 1L, name = "Carlos", email = "carlos@puce.edu.ec")
        val subject = Subject(id = 1L, name = "Matematicas", code = "MAT101", professor = professor)
        val request = EnrollmentRequest(studentId = 1L, subjectId = 1L)
        val savedEnrollment = Enrollment(id = 1L, student = student, subject = subject)

        `when`(studentRepository.findById(1L)).thenReturn(Optional.of(student))
        `when`(subjectRepository.findById(1L)).thenReturn(Optional.of(subject))
        `when`(enrollmentRepository.save(any())).thenReturn(savedEnrollment)

        val response = enrollmentService.createEnrollment(request)

        assertNotNull(response)
        assertEquals(1L, response.id)
        assertEquals("Carlos", response.student?.name)
        assertEquals("Matematicas", response.subject?.name)
    }

    @Test
    fun `createEnrollment lanza StudentNotFoundException cuando el estudiante no existe`() {
        val request = EnrollmentRequest(studentId = 99L, subjectId = 1L)

        `when`(studentRepository.findById(99L)).thenReturn(Optional.empty())

        assertThrows<StudentNotFoundException> {
            enrollmentService.createEnrollment(request)
        }
    }

    @Test
    fun `createEnrollment lanza SubjectNotFoundException cuando la materia no existe`() {
        val student = Student(id = 1L, name = "Carlos", email = "carlos@puce.edu.ec")
        val request = EnrollmentRequest(studentId = 1L, subjectId = 99L)

        `when`(studentRepository.findById(1L)).thenReturn(Optional.of(student))
        `when`(subjectRepository.findById(99L)).thenReturn(Optional.empty())

        assertThrows<SubjectNotFoundException> {
            enrollmentService.createEnrollment(request)
        }
    }

    // ─────────────────────────────────────────
    // TESTS DE getAllEnrollments
    // ─────────────────────────────────────────

    @Test
    fun `getAllEnrollments retorna lista de inscripciones`() {
        val professor = Professor(id = 1L, name = "Dr. Lopez", email = "lopez@puce.edu.ec")
        val student = Student(id = 1L, name = "Carlos", email = "carlos@puce.edu.ec")
        val subject = Subject(id = 1L, name = "Matematicas", code = "MAT101", professor = professor)
        val enrollments = listOf(
            Enrollment(id = 1L, student = student, subject = subject),
            Enrollment(id = 2L, student = student, subject = subject)
        )

        `when`(enrollmentRepository.findAll()).thenReturn(enrollments)

        val response = enrollmentService.getAllEnrollments()

        assertEquals(2, response.size)
    }

    @Test
    fun `getAllEnrollments retorna lista vacia cuando no hay inscripciones`() {
        `when`(enrollmentRepository.findAll()).thenReturn(emptyList())

        val response = enrollmentService.getAllEnrollments()

        assertEquals(0, response.size)
    }

    // ─────────────────────────────────────────
    // TESTS DE getEnrollmentById
    // ─────────────────────────────────────────

    @Test
    fun `getEnrollmentById retorna inscripcion cuando existe`() {
        val professor = Professor(id = 1L, name = "Dr. Lopez", email = "lopez@puce.edu.ec")
        val student = Student(id = 1L, name = "Carlos", email = "carlos@puce.edu.ec")
        val subject = Subject(id = 1L, name = "Matematicas", code = "MAT101", professor = professor)
        val enrollment = Enrollment(id = 1L, student = student, subject = subject)

        `when`(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment))

        val response = enrollmentService.getEnrollmentById(1L)

        assertEquals(1L, response.id)
    }

    @Test
    fun `getEnrollmentById lanza EnrollmentNotFoundException cuando no existe`() {
        `when`(enrollmentRepository.findById(99L)).thenReturn(Optional.empty())

        assertThrows<EnrollmentNotFoundException> {
            enrollmentService.getEnrollmentById(99L)
        }
    }

    // ─────────────────────────────────────────
    // TESTS DE updateEnrollment
    // ─────────────────────────────────────────

    @Test
    fun `updateEnrollment retorna inscripcion actualizada cuando existe`() {
        val professor = Professor(id = 1L, name = "Dr. Lopez", email = "lopez@puce.edu.ec")
        val student = Student(id = 1L, name = "Carlos", email = "carlos@puce.edu.ec")
        val subject = Subject(id = 1L, name = "Matematicas", code = "MAT101", professor = professor)
        val existingEnrollment = Enrollment(id = 1L, student = student, subject = subject, status = "INSCRITO")
        val request = EnrollmentUpdateRequest(status = "APROBADO")
        val updatedEnrollment = Enrollment(id = 1L, student = student, subject = subject, status = "APROBADO")

        `when`(enrollmentRepository.findById(1L)).thenReturn(Optional.of(existingEnrollment))
        `when`(enrollmentRepository.save(any())).thenReturn(updatedEnrollment)

        val response = enrollmentService.updateEnrollment(1L, request)

        assertEquals(1L, response.id)
        assertEquals("APROBADO", response.status)
    }

    @Test
    fun `updateEnrollment lanza EnrollmentNotFoundException cuando no existe`() {
        val request = EnrollmentUpdateRequest(status = "APROBADO")

        `when`(enrollmentRepository.findById(99L)).thenReturn(Optional.empty())

        assertThrows<EnrollmentNotFoundException> {
            enrollmentService.updateEnrollment(99L, request)
        }
    }

    // ─────────────────────────────────────────
    // TESTS DE deleteEnrollment
    // ─────────────────────────────────────────

    @Test
    fun `deleteEnrollment elimina exitosamente cuando existe`() {
        `when`(enrollmentRepository.existsById(1L)).thenReturn(true)

        enrollmentService.deleteEnrollment(1L)

        verify(enrollmentRepository).deleteById(1L)
    }

    @Test
    fun `deleteEnrollment lanza EnrollmentNotFoundException cuando no existe`() {
        `when`(enrollmentRepository.existsById(99L)).thenReturn(false)

        assertThrows<EnrollmentNotFoundException> {
            enrollmentService.deleteEnrollment(99L)
        }
    }
}
