package com.pucetec.students.exceptions
// Paquete donde viven todas las excepciones personalizadas del proyecto

class ProfessorNotFoundException(message: String) : RuntimeException(message)
// Excepción personalizada que se lanza cuando no se encuentra un profesor por ID
