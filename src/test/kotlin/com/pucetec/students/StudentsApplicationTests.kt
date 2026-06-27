package com.pucetec.students.services
// Paquete donde viven todos los tests de services

import com.pucetec.students.dto.StudentRequest
// DTO de entrada que se pasa al service en los tests

import com.pucetec.students.entities.Student
// Entidad que devuelven los mocks del repositorio

import com.pucetec.students.exceptions.BlankNameException
import com.pucetec.students.exceptions.StudentNotFoundException
// Excepciones que se verifican en los tests de error

import com.pucetec.students.repositories.StudentRepository
// Repositorio que se simula (mock) para no tocar la BD real

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
// Herramientas de JUnit para verificar resultados y excepciones

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
// Herramientas de Mockito para simular dependencias

import org.junit.jupiter.api.extension.ExtendWith
import java.util.Optional

@ExtendWith(MockitoExtension::class)
// Le dice a JUnit que use Mockito para crear los mocks automáticamente
// Sin esto los @Mock y @InjectMocks no funcionan

class StudentServiceTest {

	@Mock
	private lateinit var studentRepository: StudentRepository
	// Crea un repositorio FALSO — no toca la base de datos real
	// Mockito intercepta todas las llamadas y devuelve lo que le configuremos

	@InjectMocks
	private lateinit var studentService: StudentService
	// Crea el StudentService REAL e inyecta el repositorio falso automáticamente
	// Así probamos la lógica del service con dependencias controladas

	// ─────────────────────────────────────────
	// TESTS DE createStudent
	// ─────────────────────────────────────────

	@Test
	fun `createStudent retorna respuesta cuando el nombre es valido`() {
		// ARRANGE — preparar los datos y configurar los mocks
		val request = StudentRequest(name = "Carlos Pazmino", email = "crpazmino@puce.edu.ec")
		// Request con datos válidos que llega del cliente

		val savedStudent = Student(id = 1L, name = "Carlos Pazmino", email = "crpazmino@puce.edu.ec")
		// Estudiante que simula lo que devuelve la BD después del INSERT

		`when`(studentRepository.save(org.mockito.kotlin.any()))
			.thenReturn(savedStudent)
		// Configura el mock: cuando se llame save() con cualquier Student
		// devuelve el savedStudent que definimos arriba

		// ACT — ejecutar el método que se está probando
		val response = studentService.createStudent(request)

		// ASSERT — verificar que el resultado es el esperado
		assertNotNull(response)
		// La respuesta no debe ser null

		assertEquals(1L, response.id)
		// El ID debe ser 1 (el que devolvió el mock)

		assertEquals("Carlos Pazmino", response.name)
		// El nombre debe coincidir

		assertEquals("crpazmino@puce.edu.ec", response.email)
		// El email debe coincidir
	}

	@Test
	fun `createStudent lanza BlankNameException cuando el nombre esta vacio`() {
		// ARRANGE
		val request = StudentRequest(name = "", email = "test@puce.edu.ec")
		// Request con nombre vacío — debe fallar la validación

		// ACT + ASSERT
		assertThrows<BlankNameException> {
			studentService.createStudent(request)
		}
		// Verifica que se lanza exactamente BlankNameException
		// Si no se lanza → el test falla
	}

	@Test
	fun `createStudent lanza BlankNameException cuando el nombre tiene solo espacios`() {
		// ARRANGE
		val request = StudentRequest(name = "   ", email = "test@puce.edu.ec")
		// Request con espacios — isBlank() también devuelve true para espacios

		// ACT + ASSERT
		assertThrows<BlankNameException> {
			studentService.createStudent(request)
		}
	}

	// ─────────────────────────────────────────
	// TESTS DE getAllStudents
	// ─────────────────────────────────────────

	@Test
	fun `getAllStudents retorna lista de estudiantes`() {
		// ARRANGE
		val students = listOf(
			Student(id = 1L, name = "Carlos", email = "carlos@puce.edu.ec"),
			Student(id = 2L, name = "Maria", email = "maria@puce.edu.ec")
		)
		// Lista de estudiantes que simula lo que devuelve la BD

		`when`(studentRepository.findAll()).thenReturn(students)
		// Configura el mock: cuando se llame findAll() devuelve la lista

		// ACT
		val response = studentService.getAllStudents()

		// ASSERT
		assertEquals(2, response.size)
		// Debe devolver 2 estudiantes

		assertEquals("Carlos", response[0].name)
		assertEquals("Maria", response[1].name)
	}

	@Test
	fun `getAllStudents retorna lista vacia cuando no hay estudiantes`() {
		// ARRANGE
		`when`(studentRepository.findAll()).thenReturn(emptyList())
		// Simula BD vacía

		// ACT
		val response = studentService.getAllStudents()

		// ASSERT
		assertEquals(0, response.size)
		// La lista debe estar vacía
	}

	// ─────────────────────────────────────────
	// TESTS DE getStudentById
	// ─────────────────────────────────────────

	@Test
	fun `getStudentById retorna estudiante cuando existe`() {
		// ARRANGE
		val student = Student(id = 1L, name = "Carlos", email = "carlos@puce.edu.ec")

		`when`(studentRepository.findById(1L)).thenReturn(Optional.of(student))
		// Optional.of(student) → simula que SÍ existe el estudiante con id=1

		// ACT
		val response = studentService.getStudentById(1L)

		// ASSERT
		assertEquals(1L, response.id)
		assertEquals("Carlos", response.name)
	}

	@Test
	fun `getStudentById lanza StudentNotFoundException cuando no existe`() {
		// ARRANGE
		`when`(studentRepository.findById(99L)).thenReturn(Optional.empty())
		// Optional.empty() → simula que NO existe el estudiante con id=99

		// ACT + ASSERT
		assertThrows<StudentNotFoundException> {
			studentService.getStudentById(99L)
		}
		// Verifica que se lanza StudentNotFoundException → HTTP 404
	}

	// ─────────────────────────────────────────
	// TESTS DE updateStudent
	// ─────────────────────────────────────────

	@Test
	fun `updateStudent retorna estudiante actualizado cuando existe y nombre es valido`() {
		// ARRANGE
		val existingStudent = Student(id = 1L, name = "Carlos", email = "carlos@puce.edu.ec")
		val request = StudentRequest(name = "Carlos Actualizado", email = "nuevo@puce.edu.ec")
		val updatedStudent = Student(id = 1L, name = "Carlos Actualizado", email = "nuevo@puce.edu.ec")

		`when`(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudent))
		// Simula que el estudiante existe

		`when`(studentRepository.save(org.mockito.kotlin.any())).thenReturn(updatedStudent)
		// Simula el guardado del estudiante actualizado

		// ACT
		val response = studentService.updateStudent(1L, request)

		// ASSERT
		assertEquals(1L, response.id)
		assertEquals("Carlos Actualizado", response.name)
		assertEquals("nuevo@puce.edu.ec", response.email)
	}

	@Test
	fun `updateStudent lanza StudentNotFoundException cuando no existe`() {
		// ARRANGE
		val request = StudentRequest(name = "Carlos", email = "carlos@puce.edu.ec")

		`when`(studentRepository.findById(99L)).thenReturn(Optional.empty())
		// Simula que NO existe el estudiante

		// ACT + ASSERT
		assertThrows<StudentNotFoundException> {
			studentService.updateStudent(99L, request)
		}
	}

	@Test
	fun `updateStudent lanza BlankNameException cuando el nombre esta vacio`() {
		// ARRANGE
		val existingStudent = Student(id = 1L, name = "Carlos", email = "carlos@puce.edu.ec")
		val request = StudentRequest(name = "", email = "carlos@puce.edu.ec")

		`when`(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudent))
		// Simula que el estudiante SÍ existe pero el nombre nuevo está vacío

		// ACT + ASSERT
		assertThrows<BlankNameException> {
			studentService.updateStudent(1L, request)
		}
	}

	// ─────────────────────────────────────────
	// TESTS DE deleteStudent
	// ─────────────────────────────────────────

	@Test
	fun `deleteStudent elimina exitosamente cuando existe`() {
		// ARRANGE
		`when`(studentRepository.existsById(1L)).thenReturn(true)
		// Simula que el estudiante SÍ existe

		// ACT
		studentService.deleteStudent(1L)

		// ASSERT
		verify(studentRepository).deleteById(1L)
		// verify() comprueba que deleteById(1L) fue llamado exactamente una vez
		// Si no se llamó → el test falla
	}

	@Test
	fun `deleteStudent lanza StudentNotFoundException cuando no existe`() {
		// ARRANGE
		`when`(studentRepository.existsById(99L)).thenReturn(false)
		// Simula que el estudiante NO existe

		// ACT + ASSERT
		assertThrows<StudentNotFoundException> {
			studentService.deleteStudent(99L)
		}
	}
}