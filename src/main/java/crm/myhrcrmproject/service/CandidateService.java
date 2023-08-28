package crm.myhrcrmproject.service;

import crm.myhrcrmproject.domain.Candidate;
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

@Service
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
        candidate.setCandidateStatus(CandidateStatus.ACTIVE);

        // if vacancy exists, set vacancy
        converter.fromRequestVacancyDTO(candidate, requestDTO, vacanciesRepository);
        return candidate;
    }

    @Override
    protected Candidate entityAfterUpdateProcedures(Candidate entity, CandidatesRequestDTO requestDTO) {

        converter.fromRequestVacancyDTO(entity, requestDTO, vacanciesRepository);
        return entity;
    }

    // find All by Status(status)
    public List<CandidatesResponseDTO> findAllByStatus(CandidateStatus status) {
        List<Candidate> candidateList = repository.findByCandidateStatus(status);
        if (candidateList.isEmpty()) {
            throw new NotFoundException("No candidates found with status: " + status);
        }
        return candidateList.stream()
                .map(getConverter()::toDTO)
                .toList();
    }
}
