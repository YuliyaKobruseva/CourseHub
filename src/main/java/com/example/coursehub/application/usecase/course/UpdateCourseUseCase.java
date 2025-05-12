package com.example.coursehub.application.usecase.course;

import com.example.coursehub.domain.entity.Course;
import com.example.coursehub.domain.exception.BadRequestException;
import com.example.coursehub.domain.exception.NotFoundException;
import com.example.coursehub.infrastructure.repository.CourseRepository;
import com.example.coursehub.interfaces.rest.dto.request.course.CourseRequest;
import com.example.coursehub.interfaces.rest.dto.response.course.CourseResponse;
import com.example.coursehub.interfaces.rest.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.COURSE_NOT_FOUND;
import static com.example.coursehub.domain.exception.msg.ExceptionMessages.INVALID_END_DATE;

@Component
@RequiredArgsConstructor
public class UpdateCourseUseCase {

    private final CourseRepository courseRepository;

    public CourseResponse updateCourse(Long id, CourseRequest request) {
        if (request.endDate().isBefore(request.startDate())) {
            throw new BadRequestException(INVALID_END_DATE);
        }

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(COURSE_NOT_FOUND));

        course.setName(request.name());
        course.setDescription(request.description());
        course.setStartDate(request.startDate());
        course.setEndDate(request.endDate());

        Course updatedCourse = courseRepository.save(course);
        return CourseMapper.toResponse(updatedCourse);
    }
}
