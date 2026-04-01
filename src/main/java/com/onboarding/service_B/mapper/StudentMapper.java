package com.onboarding.service_B.mapper;

import com.onboarding.service_B.dto.StudentDTO;
import com.onboarding.service_B.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public Student toEntity(StudentDTO dto){

        return Student.builder().studentId(dto.getStudentId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .department(dto.getDepartment())
                .build();
    }

    public void mergeInto(StudentDTO dto, Student student){

        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setDepartment(dto.getDepartment());

    }

}
