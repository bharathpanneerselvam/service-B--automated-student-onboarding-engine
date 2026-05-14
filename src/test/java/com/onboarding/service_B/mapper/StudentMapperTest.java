package com.onboarding.service_B.mapper;

import com.onboarding.service_B.dto.StudentDTO;
import com.onboarding.service_B.entity.Student;
import com.onboarding.service_B.mapper.StudentMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StudentMapperTest {

    private final StudentMapper mapper = new StudentMapper();

    @Test
    void toEntity_shouldMapBasicFields() {

        StudentDTO dto = StudentDTO.builder()
                .studentId("STU001")
                .firstName("Alice")
                .lastName("Smith")
                .email("alice@test.com")
                .department("CS")
                .build();

        Student entity = mapper.toEntity(dto);

        assertThat(entity.getStudentId()).isEqualTo("STU001");
        assertThat(entity.getFirstName()).isEqualTo("Alice");
        assertThat(entity.getLastName()).isEqualTo("Smith");
        assertThat(entity.getEmail()).isEqualTo("alice@test.com");
        assertThat(entity.getDepartment()).isEqualTo("CS");
    }

    @Test
    void mergeInto_shouldUpdateFields() {

        Student existing = Student.builder()
                .studentId("STU001")
                .firstName("Old")
                .lastName("Name")
                .email("old@test.com")
                .department("Math")
                .build();

        StudentDTO dto = StudentDTO.builder()
                .studentId("STU001")
                .firstName("New")
                .lastName("User")
                .email("new@test.com")
                .department("CS")
                .build();

        mapper.mergeInto(dto, existing);

        assertThat(existing.getFirstName()).isEqualTo("New");
        assertThat(existing.getLastName()).isEqualTo("User");
        assertThat(existing.getEmail()).isEqualTo("new@test.com");
        assertThat(existing.getDepartment()).isEqualTo("CS");
    }
}