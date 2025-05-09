package com.example.coursehub.application.usecase.student;

import com.example.coursehub.domain.exception.NotFoundException;
import com.example.coursehub.util.BaseUseCaseTest;
import com.example.coursehub.util.CourseTestFactory;
import com.example.coursehub.util.StudentTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.STUDENT_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

class GetStudentByIdUseCaseTest extends BaseUseCaseTest {

    private GetStudentByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetStudentByIdUseCase(studentRepository);
    }

    @Test
    void givenExistingStudent_whenFetchingById_thenReturnsResponse() {
        var course = CourseTestFactory.createSavedCourse(1L);
        var student = StudentTestFactory.createStudent(7L, course);
        when(studentRepository.findById(7L)).thenReturn(Optional.of(student));

        var response = useCase.getStudentById(7L);

        assertThat(response.id()).isEqualTo(7L);
    }

    @Test
    void givenNonExistentStudent_whenFetchingById_thenThrowsNotFound() {
        when(studentRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.getStudentById(404L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(STUDENT_NOT_FOUND);
    }
}