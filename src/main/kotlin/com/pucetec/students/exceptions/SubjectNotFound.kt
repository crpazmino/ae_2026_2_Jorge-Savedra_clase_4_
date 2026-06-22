package com.pucetec.students.exceptions
// Paquete donde viven todas las excepciones personalizadas del proyecto

class SubjectNotFoundException(message: String) : RuntimeException(message)
// Excepción personalizada que se lanza cuando no se encuentra una materia por ID