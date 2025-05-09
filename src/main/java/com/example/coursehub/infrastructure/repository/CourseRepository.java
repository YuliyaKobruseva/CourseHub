package com.example.coursehub.infrastructure.repository;

import com.example.coursehub.domain.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
