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

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Getter
public class CandidateService extends GenericService<Candidate, CandidatesRequestDTO, CandidatesResponseDTO> {
    private final CandidatesRepository repository;
    private final CandidatesConverter converter;
    private final VacanciesRepository vacanciesRepository;

    @Override
    protected Candidate entityAfterCreateProcedures(Candidate candidate, CandidatesRequestDTO requestDTO) {
        // add date
        candidate.setCreatingDate(LocalDateTime.now());
        candidate.setUpdatedDate(LocalDateTime.now());

        // set status
        candidate.setStatus(CandidateStatus.ACTIVE);

        return candidate;
    }

    @Override
    protected Candidate entityAfterUpdateProcedures(Candidate entity, CandidatesRequestDTO requestDTO) {
        entity.setUpdatedDate(LocalDateTime.now());
        return entity;
    }

    // find All by Status(status)
    public List<CandidatesResponseDTO> findAllByStatus(Integer id) {
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
                .orElseThrow(() -> new NotFoundException("Entity with id " + id + " not found!"));
        List<Candidate> candidateList = repository.findByVacancy(vacancy);
        if (candidateList.isEmpty()) {
            throw new NotFoundException("No candidates found with VacancyId: " + id);
        }
        return candidateList.stream()
                .map(getConverter()::toDTO)
                .toList();
    }
}
