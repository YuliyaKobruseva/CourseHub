package com.example.coursehub.util;

import com.example.coursehub.infrastructure.repository.CourseRepository;
import com.example.coursehub.infrastructure.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.time.LocalDate;

public abstract class BaseUseCaseTest {
    protected CourseRepository courseRepository;
    protected StudentRepository studentRepository;

    protected final LocalDate defaultStartDate = LocalDate.of(2025, 1, 1);
    protected final LocalDate defaultEndDate = LocalDate.of(2025, 2, 1);

    @BeforeEach
    void initMocks() {
        courseRepository = Mockito.mock(CourseRepository.class);
        studentRepository = Mockito.mock(StudentRepository.class);
    }
}
