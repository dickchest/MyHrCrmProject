package crm.myhrcrmproject.service.utills;

import crm.myhrcrmproject.domain.Contract;
import crm.myhrcrmproject.domain.enums.ContractType;
import crm.myhrcrmproject.dto.contractDTO.ContractRequestDTO;
import crm.myhrcrmproject.dto.contractDTO.ContractResponseDTO;
import crm.myhrcrmproject.dto.contractDTO.ContractShortResponseDTO;
import crm.myhrcrmproject.repository.CandidateRepository;
import crm.myhrcrmproject.repository.ClientRepository;
import crm.myhrcrmproject.service.validation.NotFoundException;
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
                .contractType(entity.getContractType().toString())
                .candidate(
                        entity.getCandidate() != null ?
                                candidateConverter.toShortDTO(entity.getCandidate()) :
                                null
                )
                .employee(
                        entity.getEmployee() != null ?
                                employeeConverter.toShortDTO(entity.getEmployee()) :
                                null
                )
                .client(
                        entity.getClient() != null ?
                                clientConverter.toShortDTO(entity.getClient()) :
                                null
                )
                .build();
    }

    public Contract fromDTO(Contract entity, ContractRequestDTO request) {
        Optional.ofNullable(request.getStartDate()).ifPresent(entity::setStartDate);
        Optional.ofNullable(request.getEndDate()).ifPresent(entity::setEndDate);
        Optional.ofNullable(request.getSalary()).ifPresent(entity::setSalary);
        Optional.ofNullable(request.getContractType()).ifPresent(entity::setContractType);
        Optional.ofNullable(request.getCandidateId()).ifPresent(
                id -> entity.setCandidate(candidateRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException
                                ("Candidate with id " + request.getCandidateId() + " not found"))));
        Optional.ofNullable(request.getClientId()).ifPresent(
                id -> entity.setClient(clientRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException
                                ("Client with id " + request.getCandidateId() + " not found"))));
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
