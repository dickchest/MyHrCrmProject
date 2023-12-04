package com.myhrcrmproject.dto.candidateDTO;

import com.myhrcrmproject.domain.enums.CandidateStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Short response DTO for candidate details")
public class CandidateShortResponseDTO {

    @Schema(description = "ID of the candidate", example = "1")
    private Integer id;

    @Schema(description = "First name of the candidate", example = "Taras")
    private String firstName;

    @Schema(description = "Last name of the candidate", example = "Gorenko")
    private String lastName;

    @Schema(description = "Email of the candidate", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Phone number of the candidate", example = "+1234567890")
    private String phone;

    @Schema(description = "Status of the candidate", example = "BLACK_LISTED")
    private CandidateStatus status;
}