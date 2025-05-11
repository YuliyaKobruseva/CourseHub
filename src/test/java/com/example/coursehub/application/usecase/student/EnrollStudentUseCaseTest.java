package com.example.coursehub.application.usecase.student;

import com.example.coursehub.domain.exception.BadRequestException;
import com.example.coursehub.domain.exception.NotFoundException;
import com.example.coursehub.interfaces.rest.dto.request.student.CourseEnrollRequest;
import com.example.coursehub.util.BaseUseCaseTest;
import com.example.coursehub.util.CourseTestFactory;
import com.example.coursehub.util.StudentTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.*;
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
        var student = StudentTestFactory.createStudent(8L, null);

        when(studentRepository
                .findByFirstNameAndLastName(student.getFirstName(), student.getLastName()))
                .thenReturn(Optional.of(student));

        when(courseRepository.findByName(course.getName()))
                .thenReturn(Optional.of(course));

        when(studentRepository.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        var request = new CourseEnrollRequest(
                student.getFirstName(),
                student.getLastName(),
                course.getName()
        );

        var response = useCase.enrollStudent(request);

        assertThat(response.courseId()).isEqualTo(course.getId());
    }

    @Test
    void givenStudentAlreadyInCourse_whenEnroll_thenBadRequest() {
        var existingCourse = CourseTestFactory.createSavedCourse(2L);
        var student = StudentTestFactory.createStudent(9L, existingCourse);

        when(studentRepository
                .findByFirstNameAndLastName(student.getFirstName(), student.getLastName()))
                .thenReturn(Optional.of(student));

        var request = new CourseEnrollRequest(
                student.getFirstName(),
                student.getLastName(),
                "Any Course Name"
        );

        assertThatThrownBy(() -> useCase.enrollStudent(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(STUDENT_ALREADY_ENROLLED);
    }

    @Test
    void givenStudentNotFound_whenEnroll_thenNotFound() {
        when(studentRepository.findByFirstNameAndLastName("No","Body"))
                .thenReturn(Optional.empty());

        var request = new CourseEnrollRequest("No", "Body", "Some Course");

        assertThatThrownBy(() -> useCase.enrollStudent(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(STUDENT_NOT_FOUND);
    }

    @Test
    void givenCourseNotFound_whenEnroll_thenNotFound() {
        var student = StudentTestFactory.createStudent(10L, null);
        when(studentRepository
                .findByFirstNameAndLastName(student.getFirstName(), student.getLastName()))
                .thenReturn(Optional.of(student));

        when(courseRepository.findByName("Unknown Course")).thenReturn(Optional.empty());

        var request = new CourseEnrollRequest(
                student.getFirstName(),
                student.getLastName(),
                "Unknown Course"
        );

        assertThatThrownBy(() -> useCase.enrollStudent(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(COURSE_NOT_FOUND);
    }
}