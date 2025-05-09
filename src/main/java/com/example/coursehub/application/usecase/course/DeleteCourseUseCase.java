package com.example.coursehub.application.usecase.course;

import com.example.coursehub.infrastructure.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteCourseUseCase {

    private final CourseRepository courseRepository;

    public void removeCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
