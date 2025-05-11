package com.example.coursehub.application.usecase.student;

import com.example.coursehub.domain.exception.NotFoundException;
import com.example.coursehub.util.BaseUseCaseTest;
import com.example.coursehub.util.CourseTestFactory;
import com.example.coursehub.util.StudentTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.COURSE_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

class GetStudentsByCourseIdUseCaseTest extends BaseUseCaseTest {

    private GetStudentsByCourseIdUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetStudentsByCourseIdUseCase(studentRepository, courseRepository);
    }

    @Test
    void givenExistingCourse_whenFetchingStudents_thenReturnsList() {
        var course = CourseTestFactory.createSavedCourse(1L);
        var student = StudentTestFactory.createStudent(2L, course);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(studentRepository.findByCourseId(1L)).thenReturn(List.of(student));

        var result = useCase.getCourseStudents(1L);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().courseId()).isEqualTo(1L);
    }

    @Test
    void givenNonExistentCourse_whenFetching_thenThrowsNotFound() {
        when(courseRepository.findById(123L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.getCourseStudents(123L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(COURSE_NOT_FOUND);
    }
}