package com.myhrcrmproject.dto.interviewDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for interview date request")
public class InterviewDateRequestDTO {

    @Schema(description = "Date of the interview", example = "2023-10-28")
    private LocalDate date;
}
