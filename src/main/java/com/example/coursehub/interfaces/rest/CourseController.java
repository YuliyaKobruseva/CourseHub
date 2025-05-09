package com.example.coursehub.interfaces.rest;

import com.example.coursehub.application.usecase.course.*;
import com.example.coursehub.application.usecase.student.EnrollStudentUseCase;
import com.example.coursehub.application.usecase.student.GetStudentsByCourseIdUseCase;
import com.example.coursehub.domain.entity.Course;
import com.example.coursehub.interfaces.rest.dto.request.course.CourseRequest;
import com.example.coursehub.interfaces.rest.dto.response.course.CourseResponse;
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
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Tag(name = COURSE_TAG, description = COURSE_TAG_DESCRIPTION)
public class CourseController {

    private final CreateCourseUseCase createCourse;
    private final GetAllCoursesUseCase getAllCourses;
    private final GetCourseByIdUseCase getCourseById;
    private final UpdateCourseUseCase updateCourse;
    private final DeleteCourseUseCase deleteCourse;
    private final GetStudentsByCourseIdUseCase getStudentsByCourseId;
    private final EnrollStudentUseCase enrollStudentUseCase;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(
            summary = COURSE_GET_ALL,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = OK,
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CourseResponse.class))
                    )
            }
    )
    public ResponseEntity<List<CourseResponse>> getAll() {
        return ResponseEntity.ok(getAllCourses.getCourses());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(
            summary = COURSE_GET_BY_ID,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = OK,
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Course.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = NOT_FOUND,
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<CourseResponse> getById(
            @Parameter(description = "ID of the course to retrieve") @PathVariable Long id) {
        return ResponseEntity.ok(getCourseById.getCourseById(id));
    }

    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Get all students assigned to a specific course",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = OK,
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StudentResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = NOT_FOUND
                    )
            }
    )
    public ResponseEntity<List<StudentResponse>> getByCourse(
            @Parameter(description = "Course ID") @PathVariable Long courseId) {
        return ResponseEntity.ok(getStudentsByCourseId.execute(courseId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = COURSE_CREATE,
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = CREATED,
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CourseResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = BAD_REQUEST,
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<CourseResponse> create(
            @Parameter(description = "Course to create") @RequestBody @Valid CourseRequest request) {
        CourseResponse response = createCourse.addCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = COURSE_UPDATE,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = OK,
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CourseResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = NOT_FOUND,
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<CourseResponse> update(
            @Parameter(description = "ID of the course to update") @PathVariable Long id,
            @Parameter(description = "Updated course data") @RequestBody @Valid CourseRequest request) {
        return ResponseEntity.ok(updateCourse.updateCourse(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = COURSE_DELETE,
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
            @Parameter(description = "ID of the course to delete") @PathVariable Long id) {
        deleteCourse.removeCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{courseId}/students/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Enroll existing student into course",
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
            @PathVariable Long courseId,
            @PathVariable Long studentId) {

        StudentResponse resp = enrollStudentUseCase.enrollStudent(studentId, courseId);
        return ResponseEntity.ok(resp);
    }
}
