package crm.myhrcrmproject.service.utills;

import crm.myhrcrmproject.domain.Compensation;
import crm.myhrcrmproject.dto.compensationDTO.CompensationRequestDTO;
import crm.myhrcrmproject.dto.compensationDTO.CompensationResponseDTO;
import crm.myhrcrmproject.dto.compensationDTO.CompensationShortResponseDTO;
import crm.myhrcrmproject.repository.CandidateRepository;
import crm.myhrcrmproject.repository.ContractRepository;
import crm.myhrcrmproject.service.validation.NotFoundException;
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
        Optional.ofNullable(request.getCandidateId()).ifPresent(
                id -> entity.setCandidate(candidateRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException
                                ("Candidate with id " + request.getCandidateId() + " not found"))));
        Optional.ofNullable(request.getContractId()).ifPresent(
                id -> entity.setContract(contractRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException
                                ("Contract with id " + request.getCandidateId() + " not found"))));
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
