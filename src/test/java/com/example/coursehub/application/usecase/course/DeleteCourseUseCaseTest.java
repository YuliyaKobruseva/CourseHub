package com.example.coursehub.application.usecase.course;

import com.example.coursehub.domain.exception.NotFoundException;
import com.example.coursehub.util.BaseUseCaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.COURSE_NOT_FOUND;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteCourseUseCaseTest extends BaseUseCaseTest {

    private DeleteCourseUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeleteCourseUseCase(courseRepository);
    }

    @Test
    void givenExistingId_whenDeleting_thenCallsRepository() {
        when(courseRepository.existsById(7L)).thenReturn(true);

        useCase.removeCourse(7L);

        verify(courseRepository).deleteById(7L);
    }

    @Test
    void givenNonExistentId_whenDeleting_thenThrowsNotFound() {
        when(courseRepository.existsById(77L)).thenReturn(false);

        assertThatThrownBy(() -> useCase.removeCourse(77L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(COURSE_NOT_FOUND);
    }
}