package com.myhrcrmproject.service;

import com.myhrcrmproject.domain.Candidate;
import com.myhrcrmproject.repository.CandidateRepository;
import com.myhrcrmproject.repository.VacancyRepository;
import com.myhrcrmproject.service.utills.CandidateConverter;
import com.myhrcrmproject.service.utills.Helper;
import com.myhrcrmproject.service.validation.NotFoundException;
import com.myhrcrmproject.domain.enums.CandidateStatus;
import com.myhrcrmproject.dto.candidateDTO.CandidateRequestDTO;
import com.myhrcrmproject.dto.candidateDTO.CandidateResponseDTO;
import com.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Service class for managing candidate-related operations in the HR CRM system.
 *
 * <p>This service provides functionality for creating, updating, retrieving, and deleting candidate records.
 * It interacts with the {@code CandidateRepository} for data access and the {@code CandidateConverter} for
 * converting between entity and DTO objects.
 *
 * <p>Additionally, the service includes methods for finding candidates by their status and by the associated
 * vacancy. The {@code entityAfterCreateProcedures} method is a helper function for setting default values during
 * candidate creation.
 *
 * <p>The class is annotated with {@code @Service} to indicate its role as a Spring service bean.
 *
 * @author Denys Chaykovskyy
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class CandidateService implements CommonService<CandidateRequestDTO, CandidateResponseDTO> {
    private final CandidateRepository repository;
    private final CandidateConverter converter;
    private final VacancyRepository vacancyRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(CandidateService.class);

    /**
     * Retrieves a list of all candidates in the system.
     *
     * @return A list of {@code CandidateResponseDTO} representing all candidates.
     */
    public List<CandidateResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a candidate by their unique identifier.
     *
     * @param id The identifier of the candidate to retrieve.
     * @return The {@code CandidateResponseDTO} representing the retrieved candidate.
     * @throws NotFoundException if the candidate with the specified ID is not found.
     */
    public CandidateResponseDTO findById(Integer id) {
//        LOGGER.info("Запрошен кандидат с идентификатором {}.", id);
        Candidate entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidate with id: " + id + " not found!"));
        return converter.toDTO(entity);
    }

    /**
     * Creates a new candidate record based on the provided data.
     *
     * @param requestDTO The {@code CandidateRequestDTO} containing data for the new candidate.
     * @return The {@code CandidateResponseDTO} representing the newly created candidate.
     */
    public CandidateResponseDTO create(CandidateRequestDTO requestDTO) {
        Candidate entity = converter.fromDTO(converter.newEntity(), requestDTO);

        entityAfterCreateProcedures(entity);
        return converter.toDTO(repository.save(entity));
    }

    /**
     * Deletes a candidate by their unique identifier.
     *
     * @param id The identifier of the candidate to delete.
     * @throws NotFoundException if the candidate with the specified ID is not found.
     */
    public void delete(Integer id) {
        Candidate entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidate with id: " + id + " not found!"));
        repository.delete(entity);
    }

    /**
     * Updates an existing candidate record with new data.
     *
     * @param id         The identifier of the candidate to update.
     * @param requestDTO The {@code CandidateRequestDTO} containing updated data for the candidate.
     * @return The {@code CandidateResponseDTO} representing the updated candidate.
     * @throws NotFoundException if the candidate with the specified ID is not found.
     */
    public CandidateResponseDTO update(Integer id, CandidateRequestDTO requestDTO) {
        Candidate existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidate with id: " + id + " not found!"));

        converter.fromDTO(existingEntity, requestDTO);

        // filled in existing fields with new dates
        existingEntity.setUpdateDate(LocalDateTime.now());

        return converter.toDTO(repository.save(existingEntity));
    }

    // Extra method for create and update
    private void entityAfterCreateProcedures(Candidate candidate) {
        // add date
        candidate.setCreateDate(LocalDateTime.now());
        candidate.setUpdateDate(LocalDateTime.now());

        // set status
        candidate.setStatus(CandidateStatus.ACTIVE);
    }

    /**
     * Retrieves a list of candidates with a specific status.
     *
     * @param id The identifier of the status to filter by.
     * @return A list of {@code CandidateShortResponseDTO} representing candidates with the specified status.
     */
    public List<CandidateShortResponseDTO> findAllByStatusId(Integer id) {
        return Helper.findAllByEnumId(
                id,
                CandidateStatus.class,
                repository::findByStatus,
                converter::toShortDTO
        );
    }

    /**
     * Retrieves a list of candidates associated with a specific vacancy.
     *
     * @param id The identifier of the vacancy to filter by.
     * @return A list of {@code CandidateShortResponseDTO} representing candidates associated with the specified vacancy.
     */
    public List<CandidateShortResponseDTO> findAllByVacancyId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                vacancyRepository,
                repository::findByVacancy,
                converter::toShortDTO
        );
    }
}
