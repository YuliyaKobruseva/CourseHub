package com.example.coursehub.integration;

import com.example.coursehub.domain.entity.Course;
import com.example.coursehub.infrastructure.repository.CourseRepository;
import com.example.coursehub.security.jwt.JwtService;
import com.example.coursehub.security.user.Role;
import com.example.coursehub.security.user.User;
import com.example.coursehub.security.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static com.example.coursehub.util.CourseTestFactory.createCourseEntity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private JwtService jwtService;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private CourseRepository courseRepository;
    @Autowired private ObjectMapper mapper;

    private String adminToken;
    private String userToken;
    private Long   courseId;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        courseRepository.deleteAll();

        var admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .role(Role.ADMIN)
                .build();
        var usr = User.builder()
                .username("student")
                .password(passwordEncoder.encode("student123"))
                .role(Role.STUDENT)
                .build();
        userRepository.saveAll(List.of(admin, usr));
        adminToken = "Bearer " + jwtService.generateToken(admin);
        userToken  = "Bearer " + jwtService.generateToken(usr);

        var course = Course.builder()
                .name("Initial Course")
                .description("Desc")
                .startDate(LocalDate.of(2025,1,1))
                .endDate(LocalDate.of(2025,2,1))
                .build();
        course = courseRepository.save(course);
        courseId = course.getId();
    }

    @Test
    void givenAdminToken_whenGetAllCourses_thenReturnsList() throws Exception {
        mockMvc.perform(get("/api/courses")
                        .header("Authorization", adminToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(courseId))
                .andExpect(jsonPath("$[0].name").value("Initial Course"));
    }

    @Test
    void givenUserToken_whenGetAllCourses_thenReturnsList() throws Exception {
        mockMvc.perform(get("/api/courses")
                        .header("Authorization", userToken))
                .andExpect(status().isOk());
    }

    @Test
    void givenAdminToken_whenGetCourseById_thenReturnsCourse() throws Exception {
        mockMvc.perform(get("/api/courses/{id}", courseId)
                        .header("Authorization", adminToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(courseId))
                .andExpect(jsonPath("$.name").value("Initial Course"));
    }

    @Test
    void givenUserToken_whenGetCourseById_thenReturnsCourse() throws Exception {
        mockMvc.perform(get("/api/courses/{id}", courseId)
                        .header("Authorization", userToken))
                .andExpect(status().isOk());
    }

    @Test
    void givenNonExistentId_whenGetCourseById_thenReturns404() throws Exception {
        mockMvc.perform(get("/api/courses/{id}", 99999L)
                        .header("Authorization", adminToken))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenAdminToken_whenCreateCourse_thenReturns201() throws Exception {
        var req = createCourseEntity();
        mockMvc.perform(post("/api/courses")
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Course"));
    }

    @Test
    void givenUserToken_whenCreateCourse_thenReturns403() throws Exception {
        var req = createCourseEntity();
        mockMvc.perform(post("/api/courses")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isForbidden());
    }

    @Test
    void givenAdminToken_whenUpdateCourse_thenReturns200() throws Exception {
        var update = createCourseEntity();
        mockMvc.perform(put("/api/courses/{id}", courseId)
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Course"));
    }

    @Test
    void givenUserToken_whenUpdateCourse_thenReturns403() throws Exception {
        var update = createCourseEntity();
        mockMvc.perform(put("/api/courses/{id}", courseId)
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(update)))
                .andExpect(status().isForbidden());
    }

    @Test
    void givenNonExistentId_whenUpdateCourse_thenReturns404() throws Exception {
        var update = createCourseEntity();
        mockMvc.perform(put("/api/courses/{id}", 99999L)
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(update)))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenAdminToken_whenDeleteCourse_thenReturns204() throws Exception {
        mockMvc.perform(delete("/api/courses/{id}", courseId)
                        .header("Authorization", adminToken))
                .andExpect(status().isNoContent());
    }

    @Test
    void givenUserToken_whenDeleteCourse_thenReturns403() throws Exception {
        mockMvc.perform(delete("/api/courses/{id}", courseId)
                        .header("Authorization", userToken))
                .andExpect(status().isForbidden());
    }

    @Test
    void givenNonExistentId_whenDeleteCourse_thenReturns404() throws Exception {
        mockMvc.perform(delete("/api/courses/{id}", 99999L)
                        .header("Authorization", adminToken))
                .andExpect(status().isNotFound());
    }
}
