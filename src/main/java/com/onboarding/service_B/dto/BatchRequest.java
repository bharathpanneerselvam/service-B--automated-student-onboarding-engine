package com.onboarding.service_B.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatchRequest {

    @NotNull
    @Size(min = 1,max = 500)
    @Valid
    private List<StudentDTO> students;

    @JsonProperty("source_file")
    private String sourceFile;

    @JsonProperty("chunk_index")
    private Integer chunkIndex;

    @JsonProperty("total_chunks")
    private Integer totalChunks;

}

