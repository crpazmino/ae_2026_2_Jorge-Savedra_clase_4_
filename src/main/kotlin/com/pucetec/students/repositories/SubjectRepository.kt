package com.pucetec.students.repositories
// Paquete donde viven todos los repositorios del proyecto

import com.pucetec.students.entities.Subject
// Entidad que este repositorio maneja

import org.springframework.data.jpa.repository.JpaRepository
// Interfaz de Spring Data JPA que provee métodos CRUD automáticamente

interface SubjectRepository : JpaRepository<Subject, Long>
// Interfaz que hereda de JpaRepository
// No necesita ningún método adicional — JpaRepository los provee todos

// JpaRepository<Subject, Long> recibe dos tipos:
// ┌─────────────────────────────────────────────┐
// │ Subject → la entidad que maneja             │
// │ Long    → el tipo del ID de la entidad      │
// └─────────────────────────────────────────────┘

// Métodos que Spring genera automáticamente sin escribir SQL:
// ┌────────────────────────────────────────────────────────┐
// │ save(subject)           → INSERT o UPDATE en la BD     │
// │ findById(id)            → SELECT WHERE id = ?          │
// │ findAll()               → SELECT * FROM subject        │
// │ deleteById(id)          → DELETE WHERE id = ?          │
// │ existsById(id)          → SELECT COUNT WHERE id = ?    │
// │ count()                 → SELECT COUNT(*) FROM subject  │
// └────────────────────────────────────────────────────────┘