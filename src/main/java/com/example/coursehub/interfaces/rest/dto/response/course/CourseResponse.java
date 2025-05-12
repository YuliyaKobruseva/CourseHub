package com.example.coursehub.interfaces.rest.dto.response.course;

import java.time.LocalDate;

public record CourseResponse(
        Long id,
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate
) {
}
