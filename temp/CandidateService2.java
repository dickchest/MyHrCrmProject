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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CandidateService2 {
    private final CandidatesRepository candidatesRepository;
    private final CandidatesConverter candidatesConverter;
    private final VacanciesRepository vacanciesRepository;
    private final CommonServiceUtils<Candidate, Integer, CandidatesRequestDTO, CandidatesResponseDTO> service;

    // find All candidates

    public List<CandidatesResponseDTO> findAll() {
        return service.findAll();
    }

    public CandidatesResponseDTO findById(Integer id){
        return service.findById(id);
    }

    public CandidatesResponseDTO updateCandidate(Integer id, CandidatesRequestDTO requestDTO) {

        Candidate candidate = service.updateCandidate(id, requestDTO);
        candidatesConverter.fromRequestVacancyDTO(candidate, requestDTO, vacanciesRepository);
        candidatesRepository.save(candidate);

        return candidatesConverter.toDTO(candidate);
    }




    public List<CandidatesResponseDTO> findAll1() {
        List<Candidate> candidateList = candidatesRepository.findAll();
        if (candidateList.isEmpty()) {throw new NotFoundException("No candidates found");}
        return candidateList.stream()
                .map(candidatesConverter::toDTO)
                .collect(Collectors.toList());
    }

    // find By Id
    public CandidatesResponseDTO findById2(Integer id) {
        Candidate candidate = candidatesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidate with id " + id + " not found!"));
        return candidatesConverter.toDTO(candidate);
    }

    // create Candidate
    public CandidatesResponseDTO createCandidate(CandidatesRequestDTO requestDTO) {
        Candidate candidate = candidatesConverter.fromDTO(new Candidate(), requestDTO);

        if (candidatesRepository.findByEmail(requestDTO.getEmail()).isEmpty()) {

            // add date
            candidate.setCreatingDate(LocalDateTime.now());
            candidate.setUpdatedDate(LocalDateTime.now());

            // set status
            candidate.setCandidateStatus(CandidateStatus.ACTIVE);

            // if vacancy exists, set vacancy
            candidatesConverter.fromRequestVacancyDTO(candidate, requestDTO, vacanciesRepository);

        } else {
            throw new AlreadyExistsException("Candidate with same email (" + requestDTO.getEmail() + ") already exists");
        }
        return candidatesConverter.toDTO(candidatesRepository.save(candidate));
    }

    public CandidatesResponseDTO updateCandidate2(Integer id, CandidatesRequestDTO requestDTO) {
        Candidate existingCandidate = candidatesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidate with id: " + id + " not found!"));

        // filled in existing fields with new dates
        candidatesConverter.fromDTO(existingCandidate, requestDTO);
        candidatesConverter.fromRequestVacancyDTO(existingCandidate, requestDTO, vacanciesRepository);

        candidatesRepository.save(existingCandidate);

        return candidatesConverter.toDTO(existingCandidate);
    }

    public void deleteCandidate(Integer id) {
        Candidate candidate = candidatesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidate with id: " + id + " not found!"));

        candidatesRepository.delete(candidate);
    }

    // find All by Status(status)
    public List<CandidatesResponseDTO> findAllByStatus(CandidateStatus status) {
        List<Candidate> candidateList = candidatesRepository.findByCandidateStatus(status);
        if (candidateList.isEmpty()) {
            throw new NotFoundException("No candidates found with status: " + status);
        }
        return candidateList.stream()
                .map(candidatesConverter::toDTO)
                .toList();
    }
}
