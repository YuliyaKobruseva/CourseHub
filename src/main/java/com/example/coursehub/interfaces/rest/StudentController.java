package com.example.coursehub.interfaces.rest;

import com.example.coursehub.application.usecase.student.*;
import com.example.coursehub.interfaces.rest.dto.request.student.CourseEnrollRequest;
import com.example.coursehub.interfaces.rest.dto.request.student.StudentRequest;
import com.example.coursehub.interfaces.rest.dto.response.student.StudentResponse;
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
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final EnrollStudentUseCase enrollStudent;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(
            summary = STUDENT_GET_ALL,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = OK,
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StudentResponse.class))
                    )
            }
    )
    public ResponseEntity<List<StudentResponse>> getAll() {
        return ResponseEntity.ok(getAllStudents.getAllStudents());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(
            summary = STUDENT_GET_BY_ID,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = OK,
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StudentResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = NOT_FOUND,
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<StudentResponse> getById(
            @Parameter(description = "ID of the student to retrieve") @PathVariable Long id) {
        return ResponseEntity.ok(getStudentById.getStudentById(id));
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = STUDENT_CREATE,
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = CREATED,
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StudentResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = NOT_FOUND,
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<StudentResponse> create(
            @RequestBody @Valid StudentRequest request) {
        StudentResponse createdStudent = createStudent.addNewStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = STUDENT_UPDATE,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = OK,
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StudentResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = NOT_FOUND,
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<StudentResponse> update(
            @Parameter(description = "ID of the student to update") @PathVariable Long id,
            @RequestBody @Valid StudentRequest request) {
        return ResponseEntity.ok(updateStudent.updateStudentInfo(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
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
            @Parameter(description = "ID of the student to delete") @PathVariable Long id) {
        deleteStudent.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/enroll")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @Operation(
            summary = STUDENT_ENROLL,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = OK,
                            content = @Content(schema = @Schema(implementation = StudentResponse.class))
                    ),
                    @ApiResponse(responseCode = "400", description = BAD_REQUEST),
                    @ApiResponse(responseCode = "404", description = NOT_FOUND)
            }
    )
    public ResponseEntity<StudentResponse> enrollStudent(
            @Parameter(description = "Pre-registration request") @RequestBody @Valid CourseEnrollRequest request) {

        StudentResponse resp = enrollStudent.enrollStudent(request);
        return ResponseEntity.ok(resp);
    }
}
