package com.onboarding.service_B.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class BatchResponse {
    private boolean success;
    private int inserted;
    private int updated;
    private LocalDateTime processedAt;
    private String message;
}
