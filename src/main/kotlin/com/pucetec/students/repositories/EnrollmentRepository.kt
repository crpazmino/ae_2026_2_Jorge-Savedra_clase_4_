package com.pucetec.students.repositories

import com.pucetec.students.entities.Enrollment
import org.springframework.data.jpa.repository.JpaRepository

interface EnrollmentRepository : JpaRepository<Enrollment, Long>