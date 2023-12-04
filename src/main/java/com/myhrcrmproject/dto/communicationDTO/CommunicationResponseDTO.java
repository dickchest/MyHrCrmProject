package com.myhrcrmproject.dto.communicationDTO;

import com.myhrcrmproject.domain.enums.CommunicationType;
import com.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import com.myhrcrmproject.dto.clientDTO.ClientShortResponseDTO;
import com.myhrcrmproject.dto.employeeDTO.EmployeeShortResponseDTO;
import com.myhrcrmproject.dto.vacancyDTO.VacancyShortResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO for communication response")
public class CommunicationResponseDTO {

    @Schema(description = "Communication ID", example = "1")
    private Integer id;

    @Schema(description = "Date and time of communication", example = "2023-10-26T13:30:00")
    private LocalDateTime communicationDateTime;

    @Schema(description = "Type of communication", example = "EMAIL")
    private CommunicationType communicationType;

    @Schema(description = "Client information")
    private ClientShortResponseDTO client;

    @Schema(description = "Candidate information")
    private CandidateShortResponseDTO candidate;

    @Schema(description = "Vacancy information")
    private VacancyShortResponseDTO vacancy;

    @Schema(description = "Employee information")
    private EmployeeShortResponseDTO employee;

    @Schema(description = "Date of creation", example = "2023-10-26T13:30:00")
    private LocalDateTime createDate;

    @Schema(description = "Date of last update", example = "2023-10-26T13:30:00")
    private LocalDateTime updateDate;
}