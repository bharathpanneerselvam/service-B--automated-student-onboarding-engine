package com.onboarding.service_B.service;


import com.onboarding.service_B.dto.BatchRequest;
import com.onboarding.service_B.dto.BatchResponse;
import com.onboarding.service_B.dto.StudentDTO;
import com.onboarding.service_B.entity.Student;
import com.onboarding.service_B.mapper.StudentMapper;
import com.onboarding.service_B.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor

public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;


    public List<Student> getAllStudents() {

        return studentRepository.findAll();
    }

    public Optional<Student> getByStudentId(String studentId) {

        return studentRepository.findByStudentId(studentId);
    }

    public List<Student> getByDepartment(String dept) {

        return studentRepository.findByDepartment(dept);
    }
    public BatchResponse processBatch(BatchRequest request) {

        List<StudentDTO> dtos = request.getStudents();

        if (dtos == null || dtos.isEmpty()) {
            return BatchResponse.builder()
                    .success(true)
                    .message("No data")
                    .inserted(0)
                    .updated(0)
                    .processedAt(LocalDateTime.now())
                    .build();
        }

        int inserted = 0;
        int updated = 0;

        for (StudentDTO dto : dtos) {

            Optional<Student> existing =
                    studentRepository.findByStudentId(dto.getStudentId());

            if (existing.isPresent()) {
                Student student = existing.get();
                studentMapper.mergeInto(dto, student);
                studentRepository.save(student);
                updated++;
            } else {
                Student student = studentMapper.toEntity(dto);
                studentRepository.save(student);
                inserted++;
            }
        }

        return BatchResponse.builder()
                .success(true)
                .inserted(inserted)
                .updated(updated)
                .processedAt(LocalDateTime.now())
                .message("Batch processed successfully")
                .build();
    }
}
