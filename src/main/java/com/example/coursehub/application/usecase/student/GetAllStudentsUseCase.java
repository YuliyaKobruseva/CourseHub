package com.example.coursehub.application.usecase.student;

import com.example.coursehub.domain.entity.Student;
import com.example.coursehub.infrastructure.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllStudentsUseCase {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}