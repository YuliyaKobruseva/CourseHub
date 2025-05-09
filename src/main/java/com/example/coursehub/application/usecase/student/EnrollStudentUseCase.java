package com.example.coursehub.application.usecase.student;

import com.example.coursehub.domain.entity.Course;
import com.example.coursehub.domain.entity.Student;
import com.example.coursehub.domain.exception.BadRequestException;
import com.example.coursehub.domain.exception.NotFoundException;
import com.example.coursehub.infrastructure.repository.CourseRepository;
import com.example.coursehub.infrastructure.repository.StudentRepository;
import com.example.coursehub.interfaces.rest.dto.response.student.StudentResponse;
import com.example.coursehub.interfaces.rest.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.*;

@Component
@RequiredArgsConstructor
public class EnrollStudentUseCase {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentResponse enrollStudent(Long studentId, Long courseId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(STUDENT_NOT_FOUND));

        if (student.getCourse() != null && !student.getCourse().getId().equals(courseId)) {
            throw new BadRequestException(STUDENT_ALREADY_ENROLLED);
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException(COURSE_NOT_FOUND));

        student.setCourse(course);
        Student saved = studentRepository.save(student);
        return StudentMapper.toResponse(saved);
    }
}