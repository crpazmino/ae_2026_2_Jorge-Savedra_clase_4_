package com.pucetec.students.repositories

import com.pucetec.students.entities.Subject
import org.springframework.data.jpa.repository.JpaRepository

interface SubjectRepository : JpaRepository<Subject, Long>