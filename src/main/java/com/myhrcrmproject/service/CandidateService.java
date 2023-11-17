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

@Service
@AllArgsConstructor
public class CandidateService implements CommonService<CandidateRequestDTO, CandidateResponseDTO> {
    private final CandidateRepository repository;
    private final CandidateConverter converter;
    private final VacancyRepository vacancyRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(CandidateService.class);

    public List<CandidateResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    public CandidateResponseDTO findById(Integer id) {
//        LOGGER.info("Запрошен кандидат с идентификатором {}.", id);
        Candidate entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidate with id: " + id + " not found!"));
        return converter.toDTO(entity);
    }

    public CandidateResponseDTO create(CandidateRequestDTO requestDTO) {
        Candidate entity = converter.fromDTO(converter.newEntity(), requestDTO);

        entityAfterCreateProcedures(entity);
        return converter.toDTO(repository.save(entity));
    }

    public void delete(Integer id) {
        Candidate entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidate with id: " + id + " not found!"));
        repository.delete(entity);
    }

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

    // find All by Status(status)
    public List<CandidateShortResponseDTO> findAllByStatusId(Integer id) {
        return Helper.findAllByEnumId(
                id,
                CandidateStatus.class,
                repository::findByStatus,
                converter::toShortDTO
        );
    }

    // find All by Vacancy id
    public List<CandidateShortResponseDTO> findAllByVacancyId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                vacancyRepository,
                repository::findByVacancy,
                converter::toShortDTO
        );
    }

}
