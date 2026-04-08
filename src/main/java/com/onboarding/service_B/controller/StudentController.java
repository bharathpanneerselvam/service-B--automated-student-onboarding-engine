package com.onboarding.service_B.controller;

import com.onboarding.service_B.dto.BatchRequest;
import com.onboarding.service_B.dto.BatchResponse;
import com.onboarding.service_B.entity.Student;
import com.onboarding.service_B.repository.StudentRepository;
import com.onboarding.service_B.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentservice;


    public StudentController(StudentService studentservice,
                             StudentRepository studentrepository) {
        this.studentservice = studentservice;
    }


    @PostMapping("/batch")
    public BatchResponse ingestBatch(@Valid @RequestBody BatchRequest request) {
        return studentservice.processBatch(request);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentservice.getAllStudents();
    }

    @GetMapping("/{studentId}")
    public Student getByStudentId(@PathVariable String studentId) {
        return studentservice.getByStudentId(studentId)
                .orElse(null);
    }

    @GetMapping("/department/{dept}")
    public List<Student> getByDepartment(@PathVariable String dept) {
        return studentservice.getByDepartment(dept);
    }

    @DeleteMapping("/{studentId}")
    public String deleteByStudentId(@PathVariable String studentId) {
        studentservice.deleteByStudentId(studentId);
        return "Deleted student with ID: " + studentId;
    }

    @DeleteMapping
    public String deleteAllStudents() {
        studentservice.deleteAllStudents();
        return "All students deleted";
    }
}
