package com.example.coursehub.application.usecase.course;

import com.example.coursehub.domain.entity.Course;
import com.example.coursehub.domain.exception.BadRequestException;
import com.example.coursehub.infrastructure.repository.CourseRepository;
import com.example.coursehub.interfaces.rest.dto.request.course.CourseRequest;
import com.example.coursehub.interfaces.rest.dto.response.course.CourseResponse;
import com.example.coursehub.interfaces.rest.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.INVALID_END_DATE;

@Component
@RequiredArgsConstructor
public class CreateCourseUseCase {
    private final CourseRepository courseRepository;

    public CourseResponse addCourse(CourseRequest request) {
        if (request.endDate().isBefore(request.startDate())) {
            throw new BadRequestException(INVALID_END_DATE);
        }

        Course course = CourseMapper.toEntity(request);
        Course saved = courseRepository.save(course);
        return CourseMapper.toResponse(saved);
    }
}
