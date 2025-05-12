package com.example.coursehub.application.usecase.student;

import com.example.coursehub.domain.entity.Student;
import com.example.coursehub.infrastructure.repository.StudentRepository;
import com.example.coursehub.interfaces.rest.dto.request.student.StudentRequest;
import com.example.coursehub.interfaces.rest.dto.response.student.StudentResponse;
import com.example.coursehub.interfaces.rest.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CreateStudentUseCase {

    private final StudentRepository studentRepository;

    public StudentResponse addNewStudent(StudentRequest request) {
        Student student = StudentMapper.toEntity(request);
        Student saved = studentRepository.save(student);
        return StudentMapper.toResponse(saved);
    }
}
