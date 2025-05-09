package com.example.coursehub.application.usecase.course;

import com.example.coursehub.domain.entity.Course;
import com.example.coursehub.domain.exception.NotFoundException;
import com.example.coursehub.infrastructure.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.COURSE_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class UpdateCourseUseCase {

    private final CourseRepository courseRepository;

    public Course updateCourse(Long id, Course updatedCourse) {
        return courseRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedCourse.getName());
                    existing.setDescription(updatedCourse.getDescription());
                    existing.setStartDate(updatedCourse.getStartDate());
                    existing.setEndDate(updatedCourse.getEndDate());
                    return courseRepository.save(existing);
                })
                .orElseThrow(() -> new NotFoundException(COURSE_NOT_FOUND));

    }
}
