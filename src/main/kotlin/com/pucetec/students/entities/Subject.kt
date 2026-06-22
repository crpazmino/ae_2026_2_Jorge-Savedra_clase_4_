package com.pucetec.students.entities
// Paquete donde viven todas las entidades de la base de datos

import jakarta.persistence.*
// Importa todas las anotaciones JPA para mapear la clase a una tabla

@Entity
// Le dice a Hibernate que esta clase representa una tabla en PostgreSQL
// Hibernate genera automáticamente el SQL de CREATE TABLE al arrancar

@Table(name = "subject")
// El nombre exacto de la tabla en PostgreSQL será "subject"
// Sin esta anotación Hibernate usaría "Subject" con mayúscula

open class Subject(
// "open" es necesario en Kotlin para que Hibernate pueda crear proxies
// Hibernate internamente necesita hacer subclases de las entidades

    @Id
    // Este campo es la llave primaria de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // PostgreSQL genera el ID automáticamente: 1, 2, 3...
    val id: Long = 0L,
    // Default 0L porque al crear el objeto en memoria aún no tiene ID

    val name: String = "",
    // Nombre de la materia
    // Hibernate crea automáticamente una columna "name" en la tabla
    // El service valida que no esté en blanco antes de guardar

    val code: String = "",
    // Código único de la materia — diferencia clave vs Professor y Student
    // Ejemplo: "MAT101", "INF202", "FIS301"
    // Hibernate crea automáticamente una columna "code" en la tabla

    @ManyToOne
    // Relación: muchas Subject pueden tener un mismo Professor
    // Un Professor puede tener muchas Subject
    // Hibernate entiende que debe crear una llave foránea
    @JoinColumn(name = "professor_id")
    // Crea una columna "professor_id" en la tabla subject
    // que guarda el ID del profesor como llave foránea (FK)
    // Referencia la columna "id" de la tabla "professor"
    val professor: Professor? = null
    // Objeto Professor completo — Hibernate lo carga de la base de datos
    // Nullable (?) porque Hibernate necesita poder crear el objeto sin professor
    // En la base de datos la columna professor_id puede ser NULL
)