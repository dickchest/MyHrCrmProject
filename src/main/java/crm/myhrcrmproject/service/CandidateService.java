package crm.myhrcrmproject.service;

import crm.myhrcrmproject.domain.Candidate;
import crm.myhrcrmproject.domain.Vacancy;
import crm.myhrcrmproject.domain.enums.CandidateStatus;
import crm.myhrcrmproject.dto.candidatesDTO.CandidatesRequestDTO;
import crm.myhrcrmproject.dto.candidatesDTO.CandidatesResponseDTO;
import crm.myhrcrmproject.repository.CandidatesRepository;
import crm.myhrcrmproject.repository.VacanciesRepository;
import crm.myhrcrmproject.service.utills.CandidatesConverter;
import crm.myhrcrmproject.service.validation.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Setter
@Getter
public class CandidateService implements CommonService<Candidate, CandidatesRequestDTO, CandidatesResponseDTO>{
    private final CandidatesRepository repository;
    private final CandidatesConverter converter;
    private final VacanciesRepository vacanciesRepository;

    public List<CandidatesResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    public CandidatesResponseDTO findById(Integer id) {
        Candidate entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidate with id " + id + " not found!"));
        return converter.toDTO(entity);
    }

    public CandidatesResponseDTO create(CandidatesRequestDTO requestDTO) {
        Candidate entity = converter.fromDTO(converter.newEntity(), requestDTO);

        entityAfterCreateProcedures(entity);
        return converter.toDTO(repository.save(entity));
    }

    public void delete(Integer id) {
        Candidate entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidate with id: " + id + " not found!"));
        repository.delete(entity);
    }

    public CandidatesResponseDTO update(Integer id, CandidatesRequestDTO requestDTO) {
        Candidate existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidate with id: " + id + " not found!"));

        // filled in existing fields with new dates
        converter.fromDTO(existingEntity, requestDTO);
        // do extra procedures
        entityAfterUpdateProcedures(existingEntity);

        getRepository().save(existingEntity);

        return getConverter().toDTO(existingEntity);
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
    public List<CandidatesResponseDTO> findAllByStatusId(Integer id) {
        CandidateStatus status = Optional.of(CandidateStatus.values()[id])
                .orElseThrow(() -> new NotFoundException("No status found with id: " + id));
        List<Candidate> candidateList = repository.findByStatus(status);
        if (candidateList.isEmpty()) {
            throw new NotFoundException("No candidates found with status: " + status);
        }
        return candidateList.stream()
                .map(getConverter()::toDTO)
                .toList();
    }

    public List<CandidatesResponseDTO> findAllByVacancyId(Integer id) {
        Vacancy vacancy = vacanciesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacancy with id " + id + " not found!"));
        List<Candidate> candidateList = repository.findByVacancy(vacancy);
        if (candidateList.isEmpty()) {
            throw new NotFoundException("No candidates found with VacancyId: " + id);
        }
        return candidateList.stream()
                .map(getConverter()::toDTO)
                .toList();
    }
}
