package com.example.coursehub.application.usecase.student;
import com.example.coursehub.domain.entity.Course;
import com.example.coursehub.domain.entity.Student;
import com.example.coursehub.interfaces.rest.dto.request.student.StudentRequest;
import com.example.coursehub.interfaces.rest.dto.response.student.StudentResponse;
import com.example.coursehub.util.BaseUseCaseTest;
import com.example.coursehub.util.CourseTestFactory;
import com.example.coursehub.util.StudentTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateStudentUseCaseTest extends BaseUseCaseTest {

    private CreateStudentUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateStudentUseCase(studentRepository);
    }

    @Test
    void givenValidRequestAndCourse_whenCreatingStudent_thenReturnsStudentResponse() {
        StudentRequest request = StudentTestFactory.createValidRequest();
        Course course = CourseTestFactory.createSavedCourse(1L);
        Student saved = StudentTestFactory.createStudent(5L, course);

        when(studentRepository.save(any(Student.class))).thenReturn(saved);

        StudentResponse response = useCase.addNewStudent(request);

        assertThat(response.id()).isEqualTo(5L);
        assertThat(response.firstName()).isEqualTo("María");
        assertThat(response.courseId()).isEqualTo(1L);

        verify(studentRepository).save(any(Student.class));
    }
}