package com.myhrcrmproject.dto.communicationDTO;

import com.myhrcrmproject.domain.enums.CommunicationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for communication request")
public class CommunicationRequestDTO {

    @Schema(description = "Date and time of communication", example = "2023-10-26T13:30:00")
    private LocalDateTime communicationDateTime;

    @Schema(description = "Type of communication", example = "EMAIL")
    private CommunicationType communicationType;

    @Schema(description = "Client ID", example = "1")
    private Integer clientId;

    @Schema(description = "Candidate ID", example = "2")
    private Integer candidateId;

    @Schema(description = "Vacancy ID", example = "2")
    private Integer vacancyId;

    @Schema(description = "Employee ID", example = "3")
    private Integer employeeId;
}
