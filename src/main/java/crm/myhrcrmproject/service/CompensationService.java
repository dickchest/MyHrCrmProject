package crm.myhrcrmproject.service;

import crm.myhrcrmproject.domain.Candidate;
import crm.myhrcrmproject.domain.Contract;
import crm.myhrcrmproject.domain.Compensation;
import crm.myhrcrmproject.dto.compensationDTO.CompensationRequestDTO;
import crm.myhrcrmproject.dto.compensationDTO.CompensationResponseDTO;
import crm.myhrcrmproject.dto.compensationDTO.CompensationShortResponseDTO;
import crm.myhrcrmproject.repository.CandidateRepository;
import crm.myhrcrmproject.repository.ContractRepository;
import crm.myhrcrmproject.repository.CompensationRepository;
import crm.myhrcrmproject.service.utills.CompensationConverter;
import crm.myhrcrmproject.service.validation.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompensationService implements CommonService<CompensationRequestDTO, CompensationResponseDTO> {
    private final CompensationRepository repository;
    private final CompensationConverter converter;
    private final CandidateRepository candidateRepository;
    private final ContractRepository contractRepository;

    public List<CompensationResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    public CompensationResponseDTO findById(Integer id) {
        Compensation entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Compensation with id " + id + " not found!"));
        return converter.toDTO(entity);
    }

    public CompensationResponseDTO create(CompensationRequestDTO requestDTO) {
        Compensation entity = converter.fromDTO(converter.newEntity(), requestDTO);

        // extra methods
        entity.setCreateDate(LocalDateTime.now());
        entity.setUpdateDate(LocalDateTime.now());
        repository.save(entity);
        return converter.toDTO(entity);
    }

    public void delete(Integer id) {
        Compensation entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Compensation with id: " + id + " not found!"));
        repository.delete(entity);
    }

    public CompensationResponseDTO update(Integer id, CompensationRequestDTO requestDTO) {
        Compensation existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Compensation with id: " + id + " not found!"));

        // filled in existing fields with new dates
        converter.fromDTO(existingEntity, requestDTO);
        // do extra procedures
        existingEntity.setUpdateDate(LocalDateTime.now());

        repository.save(existingEntity);

        return converter.toDTO(existingEntity);
    }

    // find All by Candidate id
    public List<CompensationShortResponseDTO> findAllByCandidateId(Integer id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidate with id " + id + " not found!"));
        List<Compensation> list = repository.findByCandidate(candidate);
        return list.stream()
                .map(converter::toShortDTO)
                .toList();
    }

    // find All by Contract id
    public List<CompensationShortResponseDTO> findAllByContractId(Integer id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contract with id " + id + " not found!"));
        List<Compensation> list = repository.findByContract(contract);
        return list.stream()
                .map(converter::toShortDTO)
                .toList();
    }
}
