package com.pucetec.students.repositories
// Paquete donde viven todos los repositorios del proyecto

import com.pucetec.students.entities.Professor
// Entidad que este repositorio maneja

import org.springframework.data.jpa.repository.JpaRepository
// Interfaz de Spring Data JPA que provee métodos CRUD automáticamente

interface ProfessorRepository : JpaRepository<Professor, Long>
// Interfaz que hereda de JpaRepository
// No necesita ningún método adicional — JpaRepository los provee todos

// JpaRepository<Professor, Long> recibe dos tipos:
// ┌─────────────────────────────────────────────┐
// │ Professor → la entidad que maneja           │
// │ Long      → el tipo del ID de la entidad    │
// └─────────────────────────────────────────────┘

// Métodos que Spring genera automáticamente sin escribir SQL:
// ┌────────────────────────────────────────────────────────┐
// │ save(professor)         → INSERT o UPDATE en la BD     │
// │ findById(id)            → SELECT WHERE id = ?          │
// │ findAll()               → SELECT * FROM professor      │
// │ deleteById(id)          → DELETE WHERE id = ?          │
// │ existsById(id)          → SELECT COUNT WHERE id = ?    │
// │ count()                 → SELECT COUNT(*) FROM professor│
// └────────────────────────────────────────────────────────┘