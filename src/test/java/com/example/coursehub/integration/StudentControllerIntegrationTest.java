package com.example.coursehub.integration;

import com.example.coursehub.domain.entity.Course;
import com.example.coursehub.domain.entity.Student;
import com.example.coursehub.infrastructure.repository.CourseRepository;
import com.example.coursehub.infrastructure.repository.StudentRepository;
import com.example.coursehub.interfaces.rest.dto.request.student.StudentRequest;
import com.example.coursehub.security.jwt.JwtService;
import com.example.coursehub.security.user.Role;
import com.example.coursehub.security.user.User;
import com.example.coursehub.security.user.UserRepository;
import com.example.coursehub.util.CourseTestFactory;
import com.example.coursehub.util.StudentTestFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private JwtService jwtService;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private CourseRepository courseRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private ObjectMapper mapper;

    private String adminToken;
    private String studentToken;
    private Long   studentId;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        courseRepository.deleteAll();
        studentRepository.deleteAll();

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
        studentToken  = "Bearer " + jwtService.generateToken(usr);

        Course course   = courseRepository.save(CourseTestFactory.createCourseEntity());
        Student student = studentRepository.save(StudentTestFactory.createStudentEntity(course));
        studentId = student.getId();
    }


    @Test
    void givenAdminToken_whenGetAllStudents_thenReturnsList() throws Exception {
        mockMvc.perform(get("/api/students")
                        .header("Authorization", adminToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("María"));
    }

    @Test
    void givenNoToken_whenGetAllStudents_thenReturns401() throws Exception {
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isForbidden());
    }

    @Test
    void givenAdminToken_whenGetStudentById_thenReturnsStudent() throws Exception {
        mockMvc.perform(get("/api/students/{id}", studentId)
                        .header("Authorization", adminToken)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentId))
                .andExpect(jsonPath("$.firstName").value("María"));
    }

    @Test
    void givenNoToken_whenGetStudentById_thenReturns401() throws Exception {
        mockMvc.perform(get("/api/students/{id}", studentId))
                .andExpect(status().isForbidden());
    }

    @Test
    void givenNonExistentId_whenGetStudentById_thenReturns404() throws Exception {
        mockMvc.perform(get("/api/students/{id}", 99999L)
                        .header("Authorization", adminToken))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenAdminToken_whenCreateStudent_thenReturns201() throws Exception {
        var req = StudentTestFactory.createValidRequest();
        mockMvc.perform(post("/api/students/course/{courseId}", courseRepository.findAll().getFirst().getId())
                        .header("Authorization", adminToken)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(req.firstName()));
    }

    @Test
    void givenStudentToken_whenCreateStudent_thenReturns403() throws Exception {
        var req = StudentTestFactory.createValidRequest();
        mockMvc.perform(post("/api/students/course/{courseId}", courseRepository.findAll().getFirst().getId())
                        .header("Authorization", studentToken)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(req)))
                .andExpect(status().isForbidden());
    }

    @Test
    void givenCourseNotFound_whenCreateStudent_thenReturns404() throws Exception {
        var req = StudentTestFactory.createValidRequest();
        mockMvc.perform(post("/api/students/course/{courseId}", 99999L)
                        .header("Authorization", adminToken)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(req)))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenNoToken_whenCreateStudent_thenReturns401() throws Exception {
        var req = StudentTestFactory.createValidRequest();
        mockMvc.perform(post("/api/students/course/{courseId}", courseRepository.findAll().getFirst().getId())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(req)))
                .andExpect(status().isForbidden());
    }

    @Test
    void givenAdminToken_whenUpdateStudent_thenReturns200() throws Exception {
        var req = new StudentRequest("Ana-Updated", "Pérez", "ana@upd.com", null, LocalDate.of(2000,1,1));
        mockMvc.perform(put("/api/students/{id}", studentId)
                        .header("Authorization", adminToken)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Ana-Updated"));
    }

    @Test
    void givenStudentToken_whenUpdateStudent_thenReturns403() throws Exception {
        var req = StudentTestFactory.createValidRequest();
        mockMvc.perform(put("/api/students/{id}", studentId)
                        .header("Authorization", studentToken)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(req)))
                .andExpect(status().isForbidden());
    }

    @Test
    void givenNonExistentStudent_whenUpdate_thenReturns404() throws Exception {
        var req = StudentTestFactory.createValidRequest();
        mockMvc.perform(put("/api/students/{id}", 99999L)
                        .header("Authorization", adminToken)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(req)))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenNoToken_whenUpdateStudent_thenReturns401() throws Exception {
        var req = StudentTestFactory.createValidRequest();
        mockMvc.perform(put("/api/students/{id}", studentId)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(req)))
                .andExpect(status().isForbidden());
    }

    @Test
    void givenAdminToken_whenDeleteStudent_thenReturns204() throws Exception {
        mockMvc.perform(delete("/api/students/{id}", studentId)
                        .header("Authorization", adminToken))
                .andExpect(status().isNoContent());
    }

    @Test
    void givenStudentToken_whenDeleteStudent_thenReturns403() throws Exception {
        mockMvc.perform(delete("/api/students/{id}", studentId)
                        .header("Authorization", studentToken))
                .andExpect(status().isForbidden());
    }

    @Test
    void givenNonExistentId_whenDeleteStudent_thenReturns404() throws Exception {
        mockMvc.perform(delete("/api/students/{id}", 99999L)
                        .header("Authorization", adminToken))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenNoToken_whenDeleteStudent_thenReturns401() throws Exception {
        mockMvc.perform(delete("/api/students/{id}", studentId))
                .andExpect(status().isForbidden());
    }
}