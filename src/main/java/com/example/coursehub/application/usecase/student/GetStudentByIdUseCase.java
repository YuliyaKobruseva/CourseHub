package com.example.coursehub.application.usecase.student;

import com.example.coursehub.domain.entity.Student;
import com.example.coursehub.domain.exception.NotFoundException;
import com.example.coursehub.infrastructure.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.STUDENT_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class GetStudentByIdUseCase {

    private final StudentRepository studentRepository;

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(STUDENT_NOT_FOUND));
    }
}