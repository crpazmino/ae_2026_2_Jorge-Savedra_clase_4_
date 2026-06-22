package com.pucetec.students.entities
// Paquete donde viven todas las entidades de la base de datos

import jakarta.persistence.*
// Importa todas las anotaciones JPA para mapear la clase a una tabla

@Entity
// Le dice a Hibernate que esta clase representa una tabla en la base de datos
// Hibernate genera el SQL para crear/leer/actualizar/eliminar registros

@Table(name = "enrollment")
// El nombre exacto de la tabla en PostgreSQL será "enrollment"
// Sin esta anotación Hibernate usaría "Enrollment" con mayúscula

open class Enrollment(
// "open" es necesario en Kotlin para que Hibernate pueda crear subclases
// Hibernate internamente crea proxies (subclases) de las entidades
// En Java las clases son "open" por defecto, en Kotlin no

    @Id
    // Le dice a Hibernate que este campo es la llave primaria de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // La base de datos genera el ID automáticamente con autoincremento
    // 1, 2, 3, 4... cada vez que se inserta un registro
    val id: Long = 0L,
    // Default 0L porque al crear el objeto aún no tiene ID
    // La base de datos asigna el ID real al guardar

    @ManyToOne
    // Relación: muchos Enrollments pueden tener un mismo Student
    // Un Student puede estar en muchos Enrollments
    @JoinColumn(name = "student_id")
    // Crea una columna "student_id" en la tabla enrollment
    // que guarda el ID del estudiante como llave foránea (FK)
    val student: Student? = null,
    // Nullable porque Hibernate necesita poder crear el objeto sin student
    // En la base de datos la columna student_id referencia a la tabla student

    @ManyToOne
    // Relación: muchos Enrollments pueden tener una misma Subject
    // Una Subject puede estar en muchos Enrollments
    @JoinColumn(name = "subject_id")
    // Crea una columna "subject_id" en la tabla enrollment
    // que guarda el ID de la materia como llave foránea (FK)
    val subject: Subject? = null,
    // Nullable por la misma razón que student

    val status: String = "INSCRITO",
    // Estado de la inscripción
    // Default "INSCRITO" — valor automático al crear
    // Se puede cambiar a "RETIRADO", "APROBADO", "REPROBADO"
    // No necesita anotaciones — Hibernate lo mapea directamente como columna

    val createdAt: String = java.time.LocalDateTime.now().toString()
    // Fecha y hora exacta de cuando se creó la inscripción
    // Se genera automáticamente al instanciar el objeto
    // Ejemplo: "2026-06-17T14:06:15.410"
    // Se guarda como String para simplificar — no requiere configuración extra
)