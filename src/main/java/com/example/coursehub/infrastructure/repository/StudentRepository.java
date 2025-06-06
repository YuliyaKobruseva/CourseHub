package com.example.coursehub.infrastructure.repository;

import com.example.coursehub.domain.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByCourseId(Long courseId);
    Optional<Student> findByFirstNameAndLastName(String firstName, String lastName);
}
