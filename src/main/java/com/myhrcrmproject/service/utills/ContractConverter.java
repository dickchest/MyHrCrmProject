package com.myhrcrmproject.service.utills;

import com.myhrcrmproject.domain.Contract;
import com.myhrcrmproject.dto.contractDTO.ContractRequestDTO;
import com.myhrcrmproject.dto.contractDTO.ContractResponseDTO;
import com.myhrcrmproject.dto.contractDTO.ContractShortResponseDTO;
import com.myhrcrmproject.repository.CandidateRepository;
import com.myhrcrmproject.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ContractConverter {
    private final CandidateConverter candidateConverter;
    private final EmployeeConverter employeeConverter;
    private final CandidateRepository candidateRepository;
    private final ClientConverter clientConverter;
    private final ClientRepository clientRepository;

    public ContractResponseDTO toDTO(Contract entity) {
        return ContractResponseDTO.builder()
                .id(entity.getId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .salary(entity.getSalary())
                .contractType(entity.getContractType())
                .candidate(entity.getCandidate() != null ?
                        candidateConverter.toShortDTO(entity.getCandidate()) : null)
                .employee(entity.getEmployee() != null ?
                        employeeConverter.toShortDTO(entity.getEmployee()) : null)
                .client(entity.getClient() != null ?
                        clientConverter.toShortDTO(entity.getClient()) : null)
                .build();
    }

    public Contract fromDTO(Contract entity, ContractRequestDTO request) {
        Optional.ofNullable(request.getStartDate()).ifPresent(entity::setStartDate);
        Optional.ofNullable(request.getEndDate()).ifPresent(entity::setEndDate);
        Optional.ofNullable(request.getSalary()).ifPresent(entity::setSalary);
        Optional.ofNullable(request.getContractType()).ifPresent(entity::setContractType);
        Helper.setEntityById(
                request::getClientId, entity::setClient, clientRepository, "Client");
        Helper.setEntityById(
                request::getCandidateId, entity::setCandidate, candidateRepository, "Candidate");
        return entity;
    }

    public Contract newEntity() {
        return new Contract();
    }

    public ContractShortResponseDTO toShortDTO(Contract entity) {
        return ContractShortResponseDTO.builder()
                .id(entity.getId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .salary(entity.getSalary())
                .build();
    }
}
