package com.example.coursehub.application.usecase.course;

import com.example.coursehub.domain.entity.Course;
import com.example.coursehub.domain.exception.NotFoundException;
import com.example.coursehub.infrastructure.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.COURSE_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class GetCourseByIdUseCase {

    private final CourseRepository courseRepository;

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(COURSE_NOT_FOUND));
    }
}
