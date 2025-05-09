package com.example.coursehub.application.usecase.course;

import com.example.coursehub.domain.entity.Course;
import com.example.coursehub.infrastructure.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetCourseByIdUseCase {

    private final CourseRepository courseRepository;

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }
}
