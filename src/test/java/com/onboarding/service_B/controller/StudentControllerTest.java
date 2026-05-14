package com.onboarding.service_B.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onboarding.service_B.dto.BatchRequest;
import com.onboarding.service_B.dto.BatchResponse;
import com.onboarding.service_B.dto.StudentDTO;
import com.onboarding.service_B.entity.Student;
import com.onboarding.service_B.repository.StudentRepository;
import com.onboarding.service_B.service.StudentService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;


    private StudentDTO dto(String id) {
        return StudentDTO.builder()
                .studentId(id)
                .firstName("Alice")
                .lastName("Smith")
                .email("alice@test.com")
                .department("CS")
                .status("ACTIVE")
                .build();
    }



    @Test
    void postBatch_shouldReturnSuccess() throws Exception {

        BatchRequest req = BatchRequest.builder()
                .students(List.of(dto("STU1")))
                .build();

        BatchResponse res = BatchResponse.builder()
                .success(true)
                .inserted(1)
                .updated(0)
                .build();

        when(studentService.processBatch(any()))
                .thenReturn(res);

        mockMvc.perform(post("/api/students/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.inserted").value(1));
    }

    @Test
    void getAllStudents_shouldReturnList() throws Exception {

        List<Student> list = List.of(
                Student.builder().studentId("STU1").build()
        );

        when(studentService.getAllStudents()).thenReturn(list);

        mockMvc.perform(get("/api/students/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].studentId").value("STU1"));
    }

    @Test
    void getByStudentId_found() throws Exception {

        Student s = Student.builder().studentId("STU2").build();

        when(studentService.getByStudentId("STU2"))
                .thenReturn(Optional.of(s));

        mockMvc.perform(get("/api/students/STU2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value("STU2"));
    }

    @Test
    void getByStudentId_notFound_returnsNull() throws Exception {

        when(studentService.getByStudentId("X"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/students/X"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void getByDepartment_shouldReturnList() throws Exception {

        when(studentService.getByDepartment("CS"))
                .thenReturn(List.of(
                        Student.builder().studentId("STU3").build()
                ));

        mockMvc.perform(get("/api/students/department/CS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].studentId").value("STU3"));
    }

    @Test
    void deleteByStudentId_shouldReturnMessage() throws Exception {

        mockMvc.perform(delete("/api/students/STU10"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted student with ID: STU10"));
    }

    @Test
    void deleteAllStudents_shouldReturnMessage() throws Exception {

        mockMvc.perform(delete("/api/students"))
                .andExpect(status().isOk())
                .andExpect(content().string("All students deleted"));
    }
}