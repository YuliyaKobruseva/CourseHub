package com.example.coursehub.application.usecase.student;

import com.example.coursehub.domain.entity.Course;
import com.example.coursehub.domain.entity.Student;
import com.example.coursehub.domain.exception.NotFoundException;
import com.example.coursehub.infrastructure.repository.CourseRepository;
import com.example.coursehub.infrastructure.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.COURSE_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class CreateStudentUseCase {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public Student addNewStudent(Student student, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException(COURSE_NOT_FOUND));
        student.setCourse(course);
        return studentRepository.save(student);
    }
}
