package com.myhrcrmproject.dto.candidateDTO;

import com.myhrcrmproject.dto.addressDetailsDTO.AddressDetailsDTO;
import com.myhrcrmproject.dto.contactDetailsDTO.ContactDetailsDTO;
import com.myhrcrmproject.dto.interviewDTO.InterviewShortResponseDTO;
import com.myhrcrmproject.dto.vacancyDTO.VacancyShortResponseDTO;
import com.myhrcrmproject.domain.enums.CandidateStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response DTO for candidate details")
public class CandidateResponseDTO {

    @Schema(description = "ID of the candidate", example = "1")
    private Integer id;

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

    @Schema(description = "Details of the associated vacancy")
    private VacancyShortResponseDTO vacancy;

    @Schema(description = "Status of the candidate", example = "BLACK_LISTED")
    private CandidateStatus status;

    @Schema(description = "List of short responses for associated interviews")
    private List<InterviewShortResponseDTO> interviewsList;
}