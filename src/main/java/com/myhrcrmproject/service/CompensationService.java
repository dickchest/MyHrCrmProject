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

/**
 * Service class that handles CRUD (Create, Read, Update, Delete) operations for compensation entities.
 *
 * <p>This service provides methods to perform operations on compensation entities, such as retrieving,
 * creating, updating, and deleting communication records. It also includes additional methods for finding
 * compensation records based on different criteria.
 *
 * @author Denys Chaykovskyy
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class CompensationService implements CommonService<CompensationRequestDTO, CompensationResponseDTO> {
    private final CompensationRepository repository;
    private final CompensationConverter converter;
    private final CandidateRepository candidateRepository;
    private final ContractRepository contractRepository;

    /**
     * Retrieves a list of all compensation records.
     *
     * @return A list of response DTOs representing all compensation records.
     */
    public List<CompensationResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a compensation record by its unique identifier.
     *
     * @param id The identifier of the compensation record to retrieve.
     * @return The response DTO representing the retrieved compensation record.
     * @throws NotFoundException if the compensation record with the specified id is not found.
     */
    public CompensationResponseDTO findById(Integer id) {
        Compensation entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Compensation with id " + id + " not found!"));
        return converter.toDTO(entity);
    }

    /**
     * Creates a new compensation record based on the provided data.
     *
     * @param requestDTO The request DTO containing data for the new compensation record.
     * @return The response DTO representing the newly created compensation record.
     */
    public CompensationResponseDTO create(CompensationRequestDTO requestDTO) {
        Compensation entity = converter.fromDTO(converter.newEntity(), requestDTO);

        // extra methods
        entity.setCreateDate(LocalDateTime.now());
        entity.setUpdateDate(LocalDateTime.now());
        repository.save(entity);
        return converter.toDTO(entity);
    }

    /**
     * Updates an existing compensation record with new data.
     *
     * @param id         The identifier of the compensation record to update.
     * @param requestDTO The request DTO containing updated data for the compensation record.
     * @return The response DTO representing the updated compensation record.
     * @throws NotFoundException if the compensation record with the specified id is not found.
     */
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

    /**
     * Deletes a compensation record by its unique identifier.
     *
     * @param id The identifier of the compensation record to delete.
     * @throws NotFoundException if the compensation record with the specified id is not found.
     */
    public void delete(Integer id) {
        Compensation entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Compensation with id: " + id + " not found!"));
        repository.delete(entity);
    }

    /**
     * Retrieves a list of compensation records based on the candidate's unique identifier.
     *
     * @param id The identifier of the candidate.
     * @return A list of {@code CommunicationShortResponseDTO}  representing compensation records associated with the candidate.
     */
    public List<CompensationShortResponseDTO> findAllByCandidateId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                candidateRepository,
                repository::findByCandidate,
                converter::toShortDTO
        );
    }

    /**
     * Retrieves a list of compensation records based on the contract's unique identifier.
     *
     * @param id The identifier of the contract.
     * @return A list of {@code CompensationShortResponseDTO} representing compensation records associated with the contract.
     */
    public List<CompensationShortResponseDTO> findAllByContractId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                contractRepository,
                repository::findByContract,
                converter::toShortDTO
        );
    }
}
