package com.onboarding.service_B.mapper;

import com.onboarding.service_B.dto.StudentDTO;
import com.onboarding.service_B.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public Student toEntity(StudentDTO dto) {
        if (dto == null) {
            return null;
        }

        return Student.builder()
                .studentId(dto.getStudentId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .department(dto.getDepartment())
                .status(normalizeStatus(dto.getStatus()))
                .build();
    }

    public void mergeInto(StudentDTO dto, Student student) {
        if (dto == null || student == null) {
            return;
        }

        if (dto.getFirstName() != null) {
            student.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            student.setLastName(dto.getLastName());
        }
        if (dto.getEmail() != null) {
            student.setEmail(dto.getEmail());
        }

        if (dto.getDepartment() != null) {
            student.setDepartment(dto.getDepartment());
        }

        if(dto.getStatus()!= null){
            student.setStatus(normalizeStatus(dto.getStatus()));
        }

    }

    private String normalizeStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return "ACTIVE";
        }
        return status.trim().toUpperCase();
    }
}