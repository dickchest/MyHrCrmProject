package crm.myhrcrmproject.service;

import crm.myhrcrmproject.domain.Candidate;
import crm.myhrcrmproject.domain.enums.CandidateStatus;
import crm.myhrcrmproject.dto.candidatesDTO.CandidatesRequestDTO;
import crm.myhrcrmproject.dto.candidatesDTO.CandidatesResponseDTO;
import crm.myhrcrmproject.repository.CandidatesRepository;
import crm.myhrcrmproject.repository.VacanciesRepository;
import crm.myhrcrmproject.service.utills.CandidatesConverter;
import crm.myhrcrmproject.service.validation.AlreadyExistsException;
import crm.myhrcrmproject.service.validation.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CandidateService {
    private final CandidatesRepository candidatesRepository;
    private final CandidatesConverter candidatesConverter;
    private final VacanciesRepository vacanciesRepository;

    // find All candidates
    public List<CandidatesResponseDTO> findAll() {
        return candidatesRepository.findAll().stream()
                .map(candidatesConverter::toResponseDTO)
                .toList();
    }

    // find All by Status(status)
    public List<CandidatesResponseDTO> findAllByStatus(CandidateStatus status) {
        return candidatesRepository.findByCandidateStatus(status).stream()
                .map(candidatesConverter::toResponseDTO)
                .toList();
    }

    // find By Id
    public CandidatesResponseDTO findById(Integer id) {
        Candidate candidate = candidatesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidate with id: " + id + " not found!"));
        return candidatesConverter.toResponseDTO(candidate);
    }

    // create Candidate
    public CandidatesResponseDTO createCandidate(CandidatesRequestDTO requestDTO) {
        Candidate candidate = candidatesConverter.fromRequestDTO(new Candidate(), requestDTO);

        if (candidatesRepository.findByEmail(requestDTO.getEmail()).isEmpty()) {

            // add date
            candidate.setCreatingDate(LocalDateTime.now());
            candidate.setUpdatedDate(LocalDateTime.now());

            // set status
            candidate.setCandidateStatus(CandidateStatus.ACTIVE);

            // if vacancy exists, set vacancy
            candidatesConverter.fromRequestVacancyDTO(candidate, requestDTO, vacanciesRepository);

        } else {
            throw new AlreadyExistsException("Candidate with same email " + requestDTO.getEmail() + " already exists");
        }
        return candidatesConverter.toResponseDTO(candidatesRepository.save(candidate));
    }

    public CandidatesResponseDTO updateCandidate(Integer id, CandidatesRequestDTO requestDTO) {
        Candidate existingCandidate = candidatesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidate with id: " + id + " not found!"));

        // filled in existing fields with new dates
        candidatesConverter.fromRequestDTO(existingCandidate, requestDTO);
        candidatesConverter.fromRequestVacancyDTO(existingCandidate, requestDTO, vacanciesRepository);

        candidatesRepository.save(existingCandidate);

        return candidatesConverter.toResponseDTO(existingCandidate);
    }

    public void deleteCandidate(Integer id) {
        Candidate candidate = candidatesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidate with id: " + id + " not found!"));

        candidatesRepository.delete(candidate);
    }
}
