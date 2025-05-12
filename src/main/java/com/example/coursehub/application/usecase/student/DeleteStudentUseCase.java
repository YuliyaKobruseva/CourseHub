package com.example.coursehub.application.usecase.student;

import com.example.coursehub.domain.exception.NotFoundException;
import com.example.coursehub.infrastructure.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.STUDENT_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class DeleteStudentUseCase {

    private final StudentRepository studentRepository;

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new NotFoundException(STUDENT_NOT_FOUND);
        }
        studentRepository.deleteById(id);
    }
}
