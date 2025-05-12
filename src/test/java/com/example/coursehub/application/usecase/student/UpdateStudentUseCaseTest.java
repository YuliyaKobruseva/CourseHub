package com.example.coursehub.application.usecase.student;

import com.example.coursehub.domain.exception.NotFoundException;
import com.example.coursehub.util.BaseUseCaseTest;
import com.example.coursehub.util.CourseTestFactory;
import com.example.coursehub.util.StudentTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.STUDENT_NOT_FOUND;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateStudentUseCaseTest extends BaseUseCaseTest {

    private UpdateStudentUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateStudentUseCase(studentRepository);
    }

    @Test
    void givenExistingStudent_whenUpdating_thenReturnsUpdatedResponse() {
        var course = CourseTestFactory.createSavedCourse(1L);
        var existing = StudentTestFactory.createStudent(10L, course);
        var updated = StudentTestFactory.createStudent(10L, course);
        var request = StudentTestFactory.createValidRequest();

        when(studentRepository.findById(10L)).thenReturn(Optional.of(existing));
        when(studentRepository.save(any())).thenReturn(updated);

        var response = useCase.updateStudentInfo(10L, request);

        assertThat(response.id()).isEqualTo(10L);
        verify(studentRepository).save(any());
    }

    @Test
    void givenNonExistentStudent_whenUpdating_thenThrowsNotFoundException() {
        var request = StudentTestFactory.createValidRequest();
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.updateStudentInfo(99L, request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(STUDENT_NOT_FOUND);
    }
}