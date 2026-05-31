package com.example.demo.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleEmailAlreadyExists(
        ex: EmailAlreadyExistsException
    ): Map<String, Any> {
        return mapOf(
            "timestamp" to Instant.now().toString(),
            "status" to 400,
            "error" to "Bad Request",
            "message" to (ex.message ?: "Email already exists")
        )
    }
}