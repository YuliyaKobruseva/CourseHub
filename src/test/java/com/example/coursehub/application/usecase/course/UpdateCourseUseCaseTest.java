package com.example.coursehub.application.usecase.course;

import com.example.coursehub.domain.entity.Course;
import com.example.coursehub.domain.exception.BadRequestException;
import com.example.coursehub.domain.exception.NotFoundException;
import com.example.coursehub.interfaces.rest.dto.request.course.CourseRequest;
import com.example.coursehub.interfaces.rest.dto.response.course.CourseResponse;
import com.example.coursehub.util.BaseUseCaseTest;
import com.example.coursehub.util.CourseTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.COURSE_NOT_FOUND;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateCourseUseCaseTest extends BaseUseCaseTest {

    private UpdateCourseUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateCourseUseCase(courseRepository);
    }

    @Test
    void givenExistingCourse_whenUpdating_thenReturnsUpdatedResponse() {
        Course existing = CourseTestFactory.createSavedCourse(2L);
        CourseRequest request = new CourseRequest(
                "Updated", "New desc",
                LocalDate.of(2025, 3, 1),
                LocalDate.of(2025, 4, 1)
        );
        Course saved = Course.builder()
                .id(2L)
                .name("Updated")
                .description("New desc")
                .startDate(request.startDate())
                .endDate(request.endDate())
                .build();

        when(courseRepository.findById(2L)).thenReturn(Optional.of(existing));
        when(courseRepository.save(any(Course.class))).thenReturn(saved);

        CourseResponse response = useCase.updateCourse(2L, request);

        assertThat(response.id()).isEqualTo(2L);
        assertThat(response.name()).isEqualTo("Updated");
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void givenInvalidDateRange_whenUpdating_thenThrowsBadRequest() {
        CourseRequest invalid = new CourseRequest(
                "Bad", null,
                LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 4, 1)
        );

        assertThatThrownBy(() -> useCase.updateCourse(2L, invalid))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void givenNonExistentId_whenUpdating_thenThrowsNotFound() {
        CourseRequest req = CourseTestFactory.createValidRequest();
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.updateCourse(99L, req))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(COURSE_NOT_FOUND);
    }
}