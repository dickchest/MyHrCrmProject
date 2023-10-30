package com.myhrcrmproject.dto.communicationDTO;

import com.myhrcrmproject.dto.clientDTO.ClientShortResponseDTO;
import com.myhrcrmproject.domain.enums.CommunicationType;
import com.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import com.myhrcrmproject.dto.employeeDTO.EmployeeShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunicationShortResponseDTO {
    private Integer id;
    private LocalDateTime communicationDateTime;
    private CommunicationType communicationType;
    private ClientShortResponseDTO client;
    private CandidateShortResponseDTO candidate;
    private EmployeeShortResponseDTO employee;
}
