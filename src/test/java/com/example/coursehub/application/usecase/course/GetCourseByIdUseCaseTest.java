package com.example.coursehub.application.usecase.course;

import com.example.coursehub.domain.entity.Course;
import com.example.coursehub.domain.exception.NotFoundException;
import com.example.coursehub.interfaces.rest.dto.response.course.CourseResponse;
import com.example.coursehub.util.BaseUseCaseTest;
import com.example.coursehub.util.CourseTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.COURSE_NOT_FOUND;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetCourseByIdUseCaseTest extends BaseUseCaseTest {

    private GetCourseByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetCourseByIdUseCase(courseRepository);
    }

    @Test
    void givenExistingId_whenFetching_thenReturnsCourseResponse() {
        Course course = CourseTestFactory.createSavedCourse(5L);
        when(courseRepository.findById(5L)).thenReturn(Optional.of(course));

        CourseResponse response = useCase.getCourseById(5L);

        assertThat(response.id()).isEqualTo(5L);
        assertThat(response.name()).isEqualTo(course.getName());
    }

    @Test
    void givenNonExistentId_whenFetching_thenThrowsNotFound() {
        when(courseRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.getCourseById(404L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(COURSE_NOT_FOUND);
    }
}