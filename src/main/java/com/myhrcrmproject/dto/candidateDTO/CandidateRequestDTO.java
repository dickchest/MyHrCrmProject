package com.myhrcrmproject.dto.candidateDTO;

import com.myhrcrmproject.dto.addressDetailsDTO.AddressDetailsDTO;
import com.myhrcrmproject.dto.contactDetailsDTO.ContactDetailsDTO;
import com.myhrcrmproject.domain.enums.CandidateStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Request DTO for creating or updating a candidate")
public class CandidateRequestDTO {

    @Schema(description = "First name of the candidate", example = "Taras")
    private String firstName;

    @Schema(description = "Last name of the candidate", example = "Gorenko")
    private String lastName;

    @Schema(description = "Date of birth of the candidate", example = "1990-01-01")
    private LocalDate dateOfBirth;

    @Schema(description = "Contact details of the candidate")
    private ContactDetailsDTO contactDetails;

    @Schema(description = "Address details of the candidate")
    private AddressDetailsDTO addressDetails;

    @Schema(description = "ID of the vacancy to which the candidate is applying", example = "1")
    private Integer vacancyId;

    @Schema(description = "Status of the candidate", example = "ACTIVE")
    private CandidateStatus status;
}
