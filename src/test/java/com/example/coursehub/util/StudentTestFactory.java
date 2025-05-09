package com.example.coursehub.util;

import com.example.coursehub.domain.entity.Course;
import com.example.coursehub.domain.entity.Student;
import com.example.coursehub.interfaces.rest.dto.request.student.StudentRequest;
import com.example.coursehub.interfaces.rest.dto.response.student.StudentResponse;

import java.time.LocalDate;

public class StudentTestFactory {

    public static StudentRequest createValidRequest() {
        return new StudentRequest(
                "María",
                "Gómez",
                "maria@example.com",
                "+34666777888",
                LocalDate.of(2000, 5, 15)
        );
    }

    public static Student createStudent(Long id, Course course) {
        StudentRequest request = createValidRequest();
        Student student = new Student();
        student.setId(id);
        student.setFirstName(request.firstName());
        student.setLastName(request.lastName());
        student.setEmail(request.email());
        student.setPhone(request.phone());
        student.setBirthDate(request.birthDate());
        student.setCourse(course);
        return student;
    }

    public static StudentResponse createResponse(Student student) {
        return new StudentResponse(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getPhone(),
                student.getBirthDate(),
                student.getCourse() != null ? student.getCourse().getId() : null
        );
    }

    public static Student createStudentEntity(Course course) {
        return Student.builder()
                .firstName("María")
                .lastName("Gómez")
                .email("maria@example.com")
                .phone("+34666777888")
                .birthDate(LocalDate.of(2000, 5, 15))
                .course(course)
                .build();
    }
}
