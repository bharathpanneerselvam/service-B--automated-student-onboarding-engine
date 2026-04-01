package com.onboarding.service_B.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String department;
}

