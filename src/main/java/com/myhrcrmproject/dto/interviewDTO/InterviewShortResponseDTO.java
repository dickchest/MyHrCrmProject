package com.myhrcrmproject.dto.interviewDTO;

import com.myhrcrmproject.domain.enums.InterviewStatus;
import com.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO for interview short response")
public class InterviewShortResponseDTO {

    @Schema(description = "Interview ID", example = "1")
    private Integer id;

    @Schema(description = "Date of the interview", example = "2023-10-28")
    private LocalDate date;

    @Schema(description = "Time of the interview", example = "14:00:00.000000")
    private LocalTime time;

    @Schema(description = "Status of the interview", example = "SCHEDULED")
    private InterviewStatus status;

    @Schema(description = "Comments to the interview", example = "Scheduled interview for Peter Song")
    private String comments;

    @Schema(description = "Details of the associated candidate")
    private CandidateShortResponseDTO candidate;
}
