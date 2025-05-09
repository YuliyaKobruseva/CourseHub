package com.example.coursehub.interfaces.rest;

import com.example.coursehub.application.usecase.student.*;
import com.example.coursehub.domain.entity.Student;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.coursehub.interfaces.rest.openapi.OpenApiMessages.*;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Tag(name = STUDENT_TAG, description = STUDENT_TAG_DESCRIPTION)
public class StudentController {

    private final CreateStudentUseCase createStudent;
    private final GetAllStudentsUseCase getAllStudents;
    private final GetStudentByIdUseCase getStudentById;
    private final UpdateStudentUseCase updateStudent;
    private final DeleteStudentUseCase deleteStudent;

    @GetMapping
    @Operation(
            summary = STUDENT_GET_ALL,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = OK,
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Student.class))
                    )
            }
    )
    public ResponseEntity<List<Student>> getAll() {
        return ResponseEntity.ok(getAllStudents.getAllStudents());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = STUDENT_GET_BY_ID,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = OK,
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Student.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = NOT_FOUND,
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<Student> getById(
            @Parameter(description = "ID of the student to retrieve")
            @PathVariable Long id) {
        return ResponseEntity.ok(getStudentById.getStudentById(id));
    }

    @PostMapping("/course/{courseId}")
    @Operation(
            summary = STUDENT_CREATE,
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = CREATED,
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Student.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = NOT_FOUND,
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<Student> create(
            @Parameter(description = "ID of the course to assign the student to")
            @PathVariable Long courseId,
            @RequestBody @Valid Student student) {
        Student createdStudent = createStudent.addNewStudent(student, courseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = STUDENT_UPDATE,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = OK,
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Student.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = NOT_FOUND,
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<Student> update(
            @Parameter(description = "ID of the student to update")
            @PathVariable Long id,
            @RequestBody @Valid Student student) {
        return ResponseEntity.ok(updateStudent.updateStudentInfo(id, student));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = STUDENT_DELETE,
            responses = {
                    @ApiResponse(responseCode = "204", description = NO_CONTENT),
                    @ApiResponse(
                            responseCode = "404",
                            description = NOT_FOUND,
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the student to delete")
            @PathVariable Long id
    ) {
        deleteStudent.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
