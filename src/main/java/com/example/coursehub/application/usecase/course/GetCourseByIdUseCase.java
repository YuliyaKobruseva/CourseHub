package com.example.coursehub.application.usecase.course;

import com.example.coursehub.domain.entity.Course;
import com.example.coursehub.domain.exception.NotFoundException;
import com.example.coursehub.infrastructure.repository.CourseRepository;
import com.example.coursehub.interfaces.rest.dto.response.course.CourseResponse;
import com.example.coursehub.interfaces.rest.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.COURSE_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class GetCourseByIdUseCase {

    private final CourseRepository courseRepository;

    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(COURSE_NOT_FOUND));
        return CourseMapper.toResponse(course);

    }
}
