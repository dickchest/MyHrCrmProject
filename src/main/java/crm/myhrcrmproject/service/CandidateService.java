package crm.myhrcrmproject.service;

import crm.myhrcrmproject.domain.Candidate;
import crm.myhrcrmproject.domain.Vacancy;
import crm.myhrcrmproject.domain.enums.CandidateStatus;
import crm.myhrcrmproject.dto.candidateDTO.CandidateRequestDTO;
import crm.myhrcrmproject.dto.candidateDTO.CandidateResponseDTO;
import crm.myhrcrmproject.repository.CandidateRepository;
import crm.myhrcrmproject.repository.VacancyRepository;
import crm.myhrcrmproject.service.utills.CandidateConverter;
import crm.myhrcrmproject.service.validation.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CandidateService implements CommonService<Candidate, CandidateRequestDTO, CandidateResponseDTO>{
    private final CandidateRepository repository;
    private final CandidateConverter converter;
    private final VacancyRepository vacancyRepository;

    public List<CandidateResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    public CandidateResponseDTO findById(Integer id) {
        Candidate entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidate with id " + id + " not found!"));
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

        // filled in existing fields with new dates
        converter.fromDTO(existingEntity, requestDTO);
        // do extra procedures
        entityAfterUpdateProcedures(existingEntity);

        repository.save(existingEntity);

        return converter.toDTO(existingEntity);
    }

    // Extra method for create and update
    private Candidate entityAfterCreateProcedures(Candidate candidate) {
        // add date
        candidate.setCreatingDate(LocalDateTime.now());
        candidate.setUpdatedDate(LocalDateTime.now());

        // set status
        candidate.setStatus(CandidateStatus.ACTIVE);

        return candidate;
    }
    protected Candidate entityAfterUpdateProcedures(Candidate entity) {
        entity.setUpdatedDate(LocalDateTime.now());
        return entity;
    }

    // find All by Status(status)
    public List<CandidateResponseDTO> findAllByStatusId(Integer id) {
        CandidateStatus status = Optional.of(CandidateStatus.values()[id])
                .orElseThrow(() -> new NotFoundException("No status found with id: " + id));
        List<Candidate> list = repository.findByStatus(status).orElse(Collections.emptyList());
        return  list.stream()
                .map(converter::toDTO)
                .toList();
    }

    // find All by Vacancy id
    public List<CandidateResponseDTO> findAllByVacancyId(Integer id) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entity with id " + id + " not found!"));
        List<Candidate> list = repository.findByVacancy(vacancy).orElse(Collections.emptyList());
        return  list.stream()
                .map(converter::toDTO)
                .toList();
    }
}
