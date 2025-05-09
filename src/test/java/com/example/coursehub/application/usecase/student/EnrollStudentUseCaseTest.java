package com.example.coursehub.application.usecase.student;

import com.example.coursehub.domain.exception.BadRequestException;
import com.example.coursehub.util.BaseUseCaseTest;
import com.example.coursehub.util.CourseTestFactory;
import com.example.coursehub.util.StudentTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.STUDENT_ALREADY_ENROLLED;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class EnrollStudentUseCaseTest extends BaseUseCaseTest {

    private EnrollStudentUseCase useCase;

    @BeforeEach
    void init() {
        useCase = new EnrollStudentUseCase(studentRepository, courseRepository);
    }

    @Test
    void givenStudentWithoutCourse_whenEnroll_thenSuccess() {
        var course  = CourseTestFactory.createSavedCourse(1L);
        var student = StudentTestFactory.createStudent(8L,null);
        when(studentRepository.findById(8L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(studentRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        var resp = useCase.enrollStudent(8L,1L);

        assertThat(resp.courseId()).isEqualTo(1L);
    }

    @Test
    void givenStudentAlreadyInOtherCourse_whenEnroll_thenBadRequest() {
        var other = CourseTestFactory.createSavedCourse(2L);
        var student = StudentTestFactory.createStudent(9L, other);
        when(studentRepository.findById(9L)).thenReturn(Optional.of(student));

        assertThatThrownBy(() -> useCase.enrollStudent(9L,1L))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(STUDENT_ALREADY_ENROLLED);
    }
}