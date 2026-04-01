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
    private final StudentRepository studentrepository;

    public StudentController(StudentService studentservice,
                             StudentRepository studentrepository) {
        this.studentservice = studentservice;
        this.studentrepository = studentrepository;
    }


    @PostMapping("/batch")
    public BatchResponse ingestBatch(@Valid @RequestBody BatchRequest request) {
        return studentservice.processBatch(request);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentrepository.findAll();
    }

    @GetMapping("/{studentId}")
    public Student getByStudentId(@PathVariable String studentId) {
        return studentrepository.findByStudentId(studentId)
                .orElse(null);
    }

    @GetMapping("/department/{dept}")
    public List<Student> getByDepartment(@PathVariable String dept) {
        return studentrepository.findByDepartment(dept);
    }
}
