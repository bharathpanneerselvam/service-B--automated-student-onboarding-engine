package com.onboarding.service_B.service;

import com.onboarding.service_B.dto.BatchRequest;
import com.onboarding.service_B.dto.BatchResponse;
import com.onboarding.service_B.dto.StudentDTO;
import com.onboarding.service_B.entity.Student;
import com.onboarding.service_B.repository.StudentRepository;
import com.onboarding.service_B.mapper.StudentMapper;
import com.onboarding.service_B.service.StudentService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Spy
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentService studentService;

    private StudentDTO dto(String id) {
        return StudentDTO.builder()
                .studentId(id)
                .firstName("Test")
                .lastName("User")
                .build();
    }

    private BatchRequest batch(List<StudentDTO> list) {
        return BatchRequest.builder().students(list).build();
    }


    @Test
    void emptyList_returnsSuccess() {
        BatchResponse res = studentService.processBatch(batch(Collections.emptyList()));

        assertThat(res.isSuccess()).isTrue();
        assertThat(res.getInserted()).isZero();
        assertThat(res.getUpdated()).isZero();

        verifyNoInteractions(studentRepository);
    }

    @Test
    void nullList_returnsSuccess() {
        BatchRequest req = BatchRequest.builder().students(null).build();

        BatchResponse res = studentService.processBatch(req);

        assertThat(res.isSuccess()).isTrue();

        verifyNoInteractions(studentRepository);
    }

    @Test
    void newStudent_isInserted() {
        StudentDTO dto = dto("STU1");

        when(studentRepository.findByStudentId("STU1"))
                .thenReturn(Optional.empty());

        BatchResponse res = studentService.processBatch(batch(List.of(dto)));

        assertThat(res.getInserted()).isEqualTo(1);
        assertThat(res.getUpdated()).isEqualTo(0);

        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void existingStudent_isUpdated() {
        StudentDTO dto = dto("STU2");
        Student existing = Student.builder().studentId("STU2").build();

        when(studentRepository.findByStudentId("STU2"))
                .thenReturn(Optional.of(existing));

        BatchResponse res = studentService.processBatch(batch(List.of(dto)));

        assertThat(res.getInserted()).isEqualTo(0);
        assertThat(res.getUpdated()).isEqualTo(1);

        verify(studentMapper, times(1)).mergeInto(eq(dto), eq(existing));
        verify(studentRepository, times(1)).save(existing);
    }

    @Test
    void mixedBatch_insertAndUpdate() {
        StudentDTO newDto = dto("STU3");
        StudentDTO existingDto = dto("STU4");

        when(studentRepository.findByStudentId("STU3"))
                .thenReturn(Optional.empty());

        when(studentRepository.findByStudentId("STU4"))
                .thenReturn(Optional.of(new Student()));

        BatchResponse res = studentService.processBatch(
                batch(List.of(newDto, existingDto)));

        assertThat(res.getInserted()).isEqualTo(1);
        assertThat(res.getUpdated()).isEqualTo(1);
    }
}