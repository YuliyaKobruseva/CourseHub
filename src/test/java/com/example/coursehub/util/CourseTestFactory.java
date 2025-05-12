package com.example.coursehub.util;

import com.example.coursehub.domain.entity.Course;
import com.example.coursehub.interfaces.rest.dto.request.course.CourseRequest;

import java.time.LocalDate;

public class CourseTestFactory {

    public static CourseRequest createValidRequest() {
        return new CourseRequest(
                "Spring Boot",
                "Curso completo",
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 2, 1)
        );
    }

    public static Course createSavedCourse(Long id) {
        CourseRequest request = createValidRequest();
        return Course.builder()
                .id(id)
                .name(request.name())
                .description(request.description())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .build();
    }

    public static Course createCourseEntity() {
        return Course.builder()
                .name("Test Course")
                .description("Descripción de prueba")
                .startDate(LocalDate.of(2025, 1, 1))
                .endDate(LocalDate.of(2025, 2, 1))
                .build();
    }
}