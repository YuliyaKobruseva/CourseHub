package com.example.coursehub.interfaces.rest.dto.response.student;

import java.time.LocalDate;

public record StudentResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        LocalDate birthDate,
        Long courseId
) {}
