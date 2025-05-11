package com.example.coursehub.application.usecase.student;

import com.example.coursehub.domain.entity.Course;
import com.example.coursehub.domain.entity.Student;
import com.example.coursehub.domain.exception.NotFoundException;
import com.example.coursehub.infrastructure.repository.CourseRepository;
import com.example.coursehub.infrastructure.repository.StudentRepository;
import com.example.coursehub.interfaces.rest.dto.response.course.CourseStudentsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.COURSE_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class GetStudentsByCourseIdUseCase {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public CourseStudentsResponse getCourseStudents(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException(COURSE_NOT_FOUND));

        List<String> studentNames = studentRepository
                .findByCourseId(course.getId())
                .stream()
                .map(this::fullName)
                .toList();

        return new CourseStudentsResponse(studentNames);
    }

    private String fullName(Student s) {
        return s.getFirstName() + " " + s.getLastName();
    }
}