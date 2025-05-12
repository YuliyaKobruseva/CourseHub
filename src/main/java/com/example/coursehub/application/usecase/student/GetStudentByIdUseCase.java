package com.example.coursehub.application.usecase.student;

import com.example.coursehub.domain.entity.Student;
import com.example.coursehub.domain.exception.NotFoundException;
import com.example.coursehub.infrastructure.repository.StudentRepository;
import com.example.coursehub.interfaces.rest.dto.response.student.StudentResponse;
import com.example.coursehub.interfaces.rest.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.STUDENT_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class GetStudentByIdUseCase {

    private final StudentRepository studentRepository;

    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(STUDENT_NOT_FOUND));
        return StudentMapper.toResponse(student);
    }
}