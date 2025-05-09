package com.example.coursehub.application.usecase.student;

import com.example.coursehub.domain.exception.NotFoundException;
import com.example.coursehub.util.BaseUseCaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.STUDENT_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeleteStudentUseCaseTest extends BaseUseCaseTest {

    private DeleteStudentUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeleteStudentUseCase(studentRepository);
    }

    @Test
    void givenExistingStudentId_whenDeleting_thenDeletesSuccessfully() {
        when(studentRepository.existsById(1L)).thenReturn(true);

        useCase.deleteStudent(1L);

        verify(studentRepository).deleteById(1L);
    }

    @Test
    void givenNonExistentId_whenDeleting_thenThrowsNotFound() {
        when(studentRepository.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> useCase.deleteStudent(999L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(STUDENT_NOT_FOUND);
    }
}