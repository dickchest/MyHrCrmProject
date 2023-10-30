package com.myhrcrmproject.dto.communicationDTO;

import com.myhrcrmproject.dto.clientDTO.ClientShortResponseDTO;
import com.myhrcrmproject.domain.enums.CommunicationType;
import com.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import com.myhrcrmproject.dto.employeeDTO.EmployeeShortResponseDTO;
import com.myhrcrmproject.dto.vacancyDTO.VacancyShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunicationResponseDTO {
    private Integer id;
    private LocalDateTime communicationDateTime;
    private CommunicationType communicationType;
    private ClientShortResponseDTO client;
    private CandidateShortResponseDTO candidate;
    private VacancyShortResponseDTO vacancy;
    private EmployeeShortResponseDTO employee;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
