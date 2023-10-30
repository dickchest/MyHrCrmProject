package com.myhrcrmproject.service;

import com.myhrcrmproject.domain.Compensation;
import com.myhrcrmproject.repository.CandidateRepository;
import com.myhrcrmproject.repository.CompensationRepository;
import com.myhrcrmproject.repository.ContractRepository;
import com.myhrcrmproject.service.utills.CompensationConverter;
import com.myhrcrmproject.service.utills.Helper;
import com.myhrcrmproject.service.validation.NotFoundException;
import com.myhrcrmproject.dto.compensationDTO.CompensationRequestDTO;
import com.myhrcrmproject.dto.compensationDTO.CompensationResponseDTO;
import com.myhrcrmproject.dto.compensationDTO.CompensationShortResponseDTO;
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
        return Helper.findAllByEntityId(
                id,
                candidateRepository,
                repository::findByCandidate,
                converter::toShortDTO
        );
    }

    // find All by Contract id
    public List<CompensationShortResponseDTO> findAllByContractId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                contractRepository,
                repository::findByContract,
                converter::toShortDTO
        );
    }
}
