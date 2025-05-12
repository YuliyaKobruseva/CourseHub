package com.example.coursehub.application.usecase.student;

import com.example.coursehub.util.BaseUseCaseTest;
import com.example.coursehub.util.CourseTestFactory;
import com.example.coursehub.util.StudentTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class GetAllStudentsUseCaseTest extends BaseUseCaseTest {

    private GetAllStudentsUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetAllStudentsUseCase(studentRepository);
    }

    @Test
    void givenStudentsExist_whenFetchingAll_thenReturnsStudentResponses() {
        var course = CourseTestFactory.createSavedCourse(1L);
        var student = StudentTestFactory.createStudent(1L, course);
        when(studentRepository.findAll()).thenReturn(List.of(student));

        var result = useCase.getAllStudents();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().id()).isEqualTo(1L);
    }
}