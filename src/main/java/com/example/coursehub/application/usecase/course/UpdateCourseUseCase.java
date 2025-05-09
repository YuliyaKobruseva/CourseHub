package com.example.coursehub.application.usecase.course;

import com.example.coursehub.domain.entity.Course;
import com.example.coursehub.domain.exception.BadRequestException;
import com.example.coursehub.domain.exception.NotFoundException;
import com.example.coursehub.infrastructure.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.COURSE_NOT_FOUND;
import static com.example.coursehub.domain.exception.msg.ExceptionMessages.INVALID_END_DATE;

@Component
@RequiredArgsConstructor
public class UpdateCourseUseCase {

    private final CourseRepository courseRepository;

    public Course updateCourse(Long id, Course updatedCourse) {
        if (updatedCourse.getEndDate().isBefore(updatedCourse.getStartDate())) {
            throw new BadRequestException(INVALID_END_DATE);
        }

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
