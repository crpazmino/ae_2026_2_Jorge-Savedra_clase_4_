package com.pucetec.students.exceptions
// Paquete donde viven todas las excepciones personalizadas del proyecto

class BlankNameException(message: String) : RuntimeException(message)
// Excepción personalizada que se lanza cuando el nombre está en blanco

// Desglose:
// ┌─────────────────────┐
// │ class BlankNameException   → nombre descriptivo de la excepción
// │ (message: String)          → recibe un mensaje personalizado
// │ : RuntimeException(message)→ hereda de RuntimeException
// └─────────────────────┘