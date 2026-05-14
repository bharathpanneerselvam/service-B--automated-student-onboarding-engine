package com.onboarding.service_B.repository;

import com.onboarding.service_B.entity.Student;
import com.onboarding.service_B.repository.StudentRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void cleanUp() {
        studentRepository.deleteAll();
    }

    private Student save(String id, String dept) {
        return studentRepository.save(
                Student.builder()
                        .studentId(id)
                        .firstName("Test")
                        .lastName("User")
                        .email(id + "@test.com")
                        .department(dept)
                        .build()
        );
    }

    @Test
    void findByStudentId_found() {
        save("STU1", "CS");

        Optional<Student> result = studentRepository.findByStudentId("STU1");

        assertThat(result).isPresent();
        assertThat(result.get().getStudentId()).isEqualTo("STU1");
    }

    @Test
    void findByStudentId_notFound() {
        Optional<Student> result = studentRepository.findByStudentId("X");

        assertThat(result).isEmpty();
    }


    @Test
    void existsByStudentId_true() {
        save("STU2", "CS");

        assertThat(studentRepository.existsByStudentId("STU2")).isTrue();
    }

    @Test
    void existsByStudentId_false() {
        assertThat(studentRepository.existsByStudentId("NO")).isFalse();
    }


    @Test
    void findByDepartment_returnsStudents() {
        save("STU3", "CS");
        save("STU4", "CS");
        save("STU5", "Math");

        List<Student> cs = studentRepository.findByDepartment("CS");

        assertThat(cs).hasSize(2);
    }

    @Test
    void findByDepartment_empty() {
        List<Student> result = studentRepository.findByDepartment("Unknown");

        assertThat(result).isEmpty();
    }
}