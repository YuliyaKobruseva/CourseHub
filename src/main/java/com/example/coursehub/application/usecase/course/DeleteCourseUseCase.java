package com.example.coursehub.application.usecase.course;

import com.example.coursehub.domain.exception.NotFoundException;
import com.example.coursehub.infrastructure.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.COURSE_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class DeleteCourseUseCase {

    private final CourseRepository courseRepository;

    public void removeCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new NotFoundException(COURSE_NOT_FOUND);
        }
        courseRepository.deleteById(id);
    }
}
