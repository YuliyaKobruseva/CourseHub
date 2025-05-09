package com.example.coursehub.interfaces.rest.mapper;

import com.example.coursehub.domain.entity.Course;
import com.example.coursehub.interfaces.rest.dto.request.course.CourseRequest;
import com.example.coursehub.interfaces.rest.dto.response.course.CourseResponse;

public class CourseMapper {
    public static Course toEntity(CourseRequest request) {
        return Course.builder()
                .name(request.name())
                .description(request.description())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .build();
    }

    public static CourseResponse toResponse(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getStartDate(),
                course.getEndDate()
        );
    }
}
