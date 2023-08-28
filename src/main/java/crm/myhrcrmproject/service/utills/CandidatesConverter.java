package crm.myhrcrmproject.service.utills;

import crm.myhrcrmproject.domain.Candidate;
import crm.myhrcrmproject.domain.Vacancy;
import crm.myhrcrmproject.domain.enums.CandidateStatus;
import crm.myhrcrmproject.dto.candidatesDTO.CandidatesRequestDTO;
import crm.myhrcrmproject.dto.candidatesDTO.CandidatesResponseDTO;
import crm.myhrcrmproject.dto.candidatesDTO.CandidatesShortResponseDTO;
import crm.myhrcrmproject.repository.VacanciesRepository;
import crm.myhrcrmproject.service.validation.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class CandidatesConverter implements Converter<Candidate, CandidatesRequestDTO, CandidatesResponseDTO> {
    private VacanciesConverter vacanciesConverter;

    public CandidatesResponseDTO toDTO(Candidate candidate) {
        CandidatesResponseDTO dto = new CandidatesResponseDTO();

        dto.setId(candidate.getId());
        dto.setFirstName(candidate.getFirstName());
        dto.setLastName(candidate.getLastName());
        dto.setDateOfBirth(candidate.getDateOfBirth());
        dto.setEmail(candidate.getEmail());
        dto.setPhone(candidate.getPhone());
        dto.setAddress(candidate.getAddress());
        dto.setStatus(candidate.getCandidateStatus());
        if (candidate.getVacancy() != null) {
            dto.setVacanciesShortResponseDTO(vacanciesConverter.toShortDTO(candidate.getVacancy()));
        }

        return dto;
    }


    public Candidate fromDTO(Candidate candidate, CandidatesRequestDTO request) {
        if (request.getFirstName() != null) candidate.setFirstName(request.getFirstName());
        if (request.getLastName() != null) candidate.setLastName(request.getLastName());
        if (request.getDateOfBirth() != null) candidate.setDateOfBirth(request.getDateOfBirth());
        if (request.getEmail() != null) candidate.setEmail(request.getEmail());
        if (request.getPhone() != null) candidate.setPhone(request.getPhone());
        if (request.getAddress() != null) candidate.setAddress(request.getAddress());
        if (request.getStatus() != null) candidate.setCandidateStatus(request.getStatus());

        return candidate;
    }

    @Override
    public Candidate newEntity() {

        return new Candidate();
    }

    public Candidate fromRequestVacancyDTO(Candidate candidate, CandidatesRequestDTO request, VacanciesRepository vacanciesRepository) {

        if (request.getVacancyId() != null) {
            Vacancy vacancy = vacanciesRepository.findById(request.getVacancyId())
                    .orElseThrow(() -> new NotFoundException("Vacancy with id: " + request.getVacancyId() + " not found!"));
            candidate.setVacancy(vacancy);
            candidate.setCandidateStatus(CandidateStatus.IN_PROCESS);
        }
        return candidate;
    }

    public CandidatesShortResponseDTO toShortDTO(Candidate entity) {
        return CandidatesShortResponseDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .status(entity.getCandidateStatus())
                .build();
    }
}
