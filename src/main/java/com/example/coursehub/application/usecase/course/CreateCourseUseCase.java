package com.example.coursehub.application.usecase.course;

import com.example.coursehub.domain.entity.Course;
import com.example.coursehub.domain.exception.BadRequestException;
import com.example.coursehub.infrastructure.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.INVALID_END_DATE;

@Component
@RequiredArgsConstructor
public class CreateCourseUseCase {
    private final CourseRepository courseRepository;

    public Course addCourse(Course course) {
        if (course.getEndDate().isBefore(course.getStartDate())) {
            throw new BadRequestException(INVALID_END_DATE);
        }
        return courseRepository.save(course);
    }
}
