package com.pucetec.students.entities
// Paquete donde viven todas las entidades de la base de datos

import jakarta.persistence.*
// Importa todas las anotaciones JPA para mapear la clase a una tabla

@Entity
// Le dice a Hibernate que esta clase representa una tabla en PostgreSQL
// Hibernate genera automáticamente el SQL de CREATE TABLE al arrancar

@Table(name = "student")
// El nombre exacto de la tabla en PostgreSQL será "student"
// Sin esta anotación Hibernate usaría "Student" con mayúscula

open class Student(
// "open" es necesario en Kotlin para que Hibernate pueda crear proxies
// Hibernate internamente necesita hacer subclases de las entidades
// En Java las clases son open por defecto, en Kotlin son "final" por defecto

    @Id
    // Este campo es la llave primaria de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // PostgreSQL genera el ID automáticamente: 1, 2, 3...
    // El cliente nunca envía el ID — la base de datos lo asigna
    val id: Long = 0L,
    // Default 0L porque al crear el objeto en memoria aún no tiene ID
    // La base de datos asigna el ID real al hacer el INSERT

    val name: String = "",
    // Nombre del estudiante
    // Hibernate crea automáticamente una columna "name" en la tabla
    // El service valida que no esté en blanco antes de guardar

    val email: String = ""
    // Email del estudiante
    // Hibernate crea automáticamente una columna "email" en la tabla
)