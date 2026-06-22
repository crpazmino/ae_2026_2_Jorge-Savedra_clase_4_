package com.pucetec.students.repositories
// Paquete donde viven todos los repositorios del proyecto

import com.pucetec.students.entities.Student
// Entidad que este repositorio maneja

import org.springframework.data.jpa.repository.JpaRepository
// Interfaz de Spring Data JPA que provee métodos CRUD automáticamente

interface StudentRepository : JpaRepository<Student, Long> {
// Interfaz que hereda de JpaRepository
// A diferencia de los otros repositorios — tiene un método extra

    fun existsByEmail(email: String): Boolean
    // Método personalizado que Spring Data JPA genera automáticamente
    // Spring lee el nombre del método y construye el SQL:
    // SELECT COUNT(*) FROM student WHERE email = ? > 0
    //
    // Desglose del nombre:
    // ┌──────────────────────────────────────────────┐
    // │ exists   → verifica si existe (devuelve bool)│
    // │ By       → condición WHERE                   │
    // │ Email    → columna a filtrar                 │
    // └──────────────────────────────────────────────┘
    //
    // Devuelve:
    // true  → si ya existe un estudiante con ese email
    // false → si el email está disponible
}

// Métodos heredados de JpaRepository:
// ┌────────────────────────────────────────────────────────┐
// │ save(student)           → INSERT o UPDATE en la BD     │
// │ findById(id)            → SELECT WHERE id = ?          │
// │ findAll()               → SELECT * FROM student        │
// │ deleteById(id)          → DELETE WHERE id = ?          │
// │ existsById(id)          → SELECT COUNT WHERE id = ?    │
// │ count()                 → SELECT COUNT(*) FROM student  │
// └────────────────────────────────────────────────────────┘