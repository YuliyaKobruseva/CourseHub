package com.example.coursehub.infrastructure.repository;

import com.example.coursehub.domain.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
