package com.myhrcrmproject.service.utills;

import com.myhrcrmproject.domain.Compensation;
import com.myhrcrmproject.repository.CandidateRepository;
import com.myhrcrmproject.repository.ContractRepository;
import com.myhrcrmproject.dto.compensationDTO.CompensationRequestDTO;
import com.myhrcrmproject.dto.compensationDTO.CompensationResponseDTO;
import com.myhrcrmproject.dto.compensationDTO.CompensationShortResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CompensationConverter {
    private final CandidateConverter candidateConverter;
    private final CandidateRepository candidateRepository;
    private final ContractConverter contractConverter;
    private final ContractRepository contractRepository;

    public CompensationResponseDTO toDTO(Compensation entity) {
        return CompensationResponseDTO.builder()
                .id(entity.getId())
                .salary(entity.getSalary())
                .paymentDate(entity.getPaymentDate())
                .comments(entity.getComments())
                .candidate(candidateConverter.toShortDTO(entity.getCandidate()))
                .contract(contractConverter.toShortDTO(entity.getContract()))
                .build();
    }

    public Compensation fromDTO(Compensation entity, CompensationRequestDTO request) {
        Optional.ofNullable(request.getSalary()).ifPresent(entity::setSalary);
        Optional.ofNullable(request.getPaymentDate()).ifPresent(entity::setPaymentDate);
        Optional.ofNullable(request.getComments()).ifPresent(entity::setComments);
        Helper.setEntityById(
                request::getCandidateId, entity::setCandidate, candidateRepository, "Candidate");
        Helper.setEntityById(
                request::getContractId, entity::setContract, contractRepository, "Contract");

        return entity;
    }

    public Compensation newEntity() {
        return new Compensation();
    }

    public CompensationShortResponseDTO toShortDTO(Compensation entity) {
        return CompensationShortResponseDTO.builder()
                .id(entity.getId())
                .salary(entity.getSalary())
                .paymentDate(entity.getPaymentDate())
                .build();
    }
}
