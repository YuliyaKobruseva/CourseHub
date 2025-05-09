package com.example.coursehub.application.usecase.course;

import com.example.coursehub.domain.entity.Course;
import com.example.coursehub.domain.exception.BadRequestException;
import com.example.coursehub.interfaces.rest.dto.request.course.CourseRequest;
import com.example.coursehub.interfaces.rest.dto.response.course.CourseResponse;
import com.example.coursehub.util.BaseUseCaseTest;
import com.example.coursehub.util.CourseTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.INVALID_END_DATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class CreateCourseUseCaseTest extends BaseUseCaseTest {

    private CreateCourseUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateCourseUseCase(courseRepository);
    }


    @Test
    void givenValidCourseRequest_whenCreatingCourse_thenReturnsResponseWithId() {
        CourseRequest request = CourseTestFactory.createValidRequest();
        Course savedCourse = CourseTestFactory.createSavedCourse(1L);
        when(courseRepository.save(any(Course.class))).thenReturn(savedCourse);

        CourseResponse response = useCase.addCourse(request);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("Spring Boot");

        ArgumentCaptor<Course> captor = ArgumentCaptor.forClass(Course.class);
        verify(courseRepository).save(captor.capture());

        Course courseSaved = captor.getValue();
        assertThat(courseSaved.getStartDate()).isEqualTo(request.startDate());
        assertThat(courseSaved.getEndDate()).isEqualTo(request.endDate());
    }

    @Test
    void givenInvalidDateRange_whenCreatingCourse_thenThrowsBadRequestException() {
        CourseRequest request = new CourseRequest(
                "Invalid",
                "Bad date range",
                LocalDate.of(2025, 2, 1),
                LocalDate.of(2025, 1, 1)
        );

        assertThatThrownBy(() -> useCase.addCourse(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(INVALID_END_DATE);
    }

    @Test
    void givenDatabaseError_whenSavingCourse_thenPropagatesException() {
        CourseRequest request = CourseTestFactory.createValidRequest();
        when(courseRepository.save(any())).thenThrow(new RuntimeException("DB error"));

        assertThatThrownBy(() -> useCase.addCourse(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("DB error");
    }
}