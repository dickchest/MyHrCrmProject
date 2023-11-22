package com.myhrcrmproject.dto.interviewDTO;

import com.myhrcrmproject.domain.enums.InterviewStatus;
import com.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
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
public class InterviewShortResponseDTO {
    private Integer id;
    private LocalDate date;
    private LocalTime time;
    private InterviewStatus status;
    private String comments;
    private CandidateShortResponseDTO candidate;
}