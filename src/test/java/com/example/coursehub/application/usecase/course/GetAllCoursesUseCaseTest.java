package com.example.coursehub.application.usecase.course;

import com.example.coursehub.domain.entity.Course;
import com.example.coursehub.util.BaseUseCaseTest;
import com.example.coursehub.util.CourseTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetAllCoursesUseCaseTest extends BaseUseCaseTest {

    private GetAllCoursesUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetAllCoursesUseCase(courseRepository);
    }

    @Test
    void givenCoursesExist_whenFetchingAll_thenReturnsList() {
        Course course = CourseTestFactory.createSavedCourse(1L);
        when(courseRepository.findAll()).thenReturn(List.of(course));

        var result = useCase.getCourses();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().id()).isEqualTo(1L);
    }
}