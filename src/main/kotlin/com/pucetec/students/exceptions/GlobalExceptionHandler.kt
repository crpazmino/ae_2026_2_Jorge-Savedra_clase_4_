package com.pucetec.students.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleStudentNotFound(ex: StudentNotFoundException): Map<String, Any> =
        errorMap(404, "Not Found", ex.message)

    @ExceptionHandler(ProfessorNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleProfessorNotFound(ex: ProfessorNotFoundException): Map<String, Any> =
        errorMap(404, "Not Found", ex.message)

    @ExceptionHandler(SubjectNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleSubjectNotFound(ex: SubjectNotFoundException): Map<String, Any> =
        errorMap(404, "Not Found", ex.message)

    @ExceptionHandler(EnrollmentNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleEnrollmentNotFound(ex: EnrollmentNotFoundException): Map<String, Any> =
        errorMap(404, "Not Found", ex.message)

    @ExceptionHandler(BlankNameException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBlankName(ex: BlankNameException): Map<String, Any> =
        errorMap(400, "Bad Request", ex.message)

    private fun errorMap(status: Int, error: String, message: String?): Map<String, Any> =
        mapOf(
            "timestamp" to Instant.now().toString(),
            "status" to status,
            "error" to error,
            "message" to (message ?: "Error")
        )
}