package com.example.coursehub.interfaces.rest;

import com.example.coursehub.application.usecase.course.*;
import com.example.coursehub.domain.entity.Course;
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
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Tag(name = COURSE_TAG, description = COURSE_TAG_DESCRIPTION)
public class CourseController {

    private final CreateCourseUseCase createCourse;
    private final GetAllCoursesUseCase getAllCourses;
    private final GetCourseByIdUseCase getCourseById;
    private final UpdateCourseUseCase updateCourse;
    private final DeleteCourseUseCase deleteCourse;

    @GetMapping
    @Operation(
            summary = COURSE_GET_ALL,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = OK,
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Course.class))
                    )
            }
    )
    public ResponseEntity<List<Course>> getAll() {
        return ResponseEntity.ok(getAllCourses.getCourses());
    }

    @GetMapping("/{id}")
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
    public ResponseEntity<Course> getById(
            @Parameter(description = "ID of the course to retrieve") @PathVariable Long id) {
        return ResponseEntity.ok(getCourseById.getCourseById(id));
    }

    @PostMapping
    @Operation(
            summary = COURSE_CREATE,
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = CREATED,
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Course.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = BAD_REQUEST,
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<Course> create(
            @Parameter(description = "Course to create") @RequestBody @Valid Course course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createCourse.addCourse(course));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = COURSE_UPDATE,
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
    public ResponseEntity<Course> update(
            @Parameter(description = "ID of the course to update") @PathVariable Long id,
            @Parameter(description = "Updated course data") @RequestBody @Valid Course course) {
        return ResponseEntity.ok(updateCourse.updateCourse(id, course));
    }

    @DeleteMapping("/{id}")
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
}
