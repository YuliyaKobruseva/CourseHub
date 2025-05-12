package com.example.coursehub.interfaces.rest.mapper;

import com.example.coursehub.domain.entity.Student;
import com.example.coursehub.interfaces.rest.dto.request.student.StudentRequest;
import com.example.coursehub.interfaces.rest.dto.response.student.StudentResponse;

public class StudentMapper {
    public static Student toEntity(StudentRequest request) {
        Student student = new Student();
        student.setFirstName(request.firstName());
        student.setLastName(request.lastName());
        student.setEmail(request.email());
        student.setPhone(request.phone());
        student.setBirthDate(request.birthDate());
        return student;
    }

    public static StudentResponse toResponse(Student student) {
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
}