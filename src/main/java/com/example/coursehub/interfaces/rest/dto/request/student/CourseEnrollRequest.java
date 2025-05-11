package com.example.coursehub.interfaces.rest.dto.request.student;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseEnrollRequest(
        @NotBlank @NotNull String studentFirstName,
        @NotBlank @NotNull String studentLastName,
        @NotBlank @NotNull String courseName
) {}
