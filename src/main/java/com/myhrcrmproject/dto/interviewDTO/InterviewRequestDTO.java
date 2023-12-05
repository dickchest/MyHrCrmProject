package com.myhrcrmproject.dto.interviewDTO;

import com.myhrcrmproject.domain.enums.InterviewStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for interview request")
public class InterviewRequestDTO {

    @Schema(description = "Date of the interview", example = "2023-10-28")
    private LocalDate date;

    @Schema(description = "Time of the interview", example = "14:00:00.000000")
    private LocalTime time;

    @Schema(description = "Location of the interview", example = "Room 1")
    private String location;

    @Schema(description = "Comments to the interview", example = "Scheduled interview for Peter Song")
    private String comments;

    @Schema(description = "Associated candidate ID", example = "3")
    private Integer candidateId;

    @Schema(description = "Associated employee ID", example = "1")
    private Integer employeeId;

    @Schema(description = "Status of the interview", example = "SCHEDULED")
    private InterviewStatus status;
}
