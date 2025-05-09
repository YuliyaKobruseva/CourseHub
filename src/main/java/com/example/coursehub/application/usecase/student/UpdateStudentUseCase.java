package com.example.coursehub.application.usecase.student;

import com.example.coursehub.domain.entity.Student;
import com.example.coursehub.domain.exception.NotFoundException;
import com.example.coursehub.infrastructure.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.coursehub.domain.exception.msg.ExceptionMessages.STUDENT_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class UpdateStudentUseCase {

    private final StudentRepository studentRepository;

    public Student updateStudentInfo(Long id, Student updatedStudent) {
        return studentRepository.findById(id)
                .map(existing -> {
                    existing.setFirstName(updatedStudent.getFirstName());
                    existing.setLastName(updatedStudent.getLastName());
                    existing.setEmail(updatedStudent.getEmail());
                    existing.setPhone(updatedStudent.getPhone());
                    existing.setBirthDate(updatedStudent.getBirthDate());
                    return studentRepository.save(existing);
                })
                .orElseThrow(() -> new NotFoundException(STUDENT_NOT_FOUND));
    }
}
