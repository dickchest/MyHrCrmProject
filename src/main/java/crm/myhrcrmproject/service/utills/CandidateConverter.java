package crm.myhrcrmproject.service.utills;

import crm.myhrcrmproject.domain.Candidate;
import crm.myhrcrmproject.dto.candidateDTO.CandidateRequestDTO;
import crm.myhrcrmproject.dto.candidateDTO.CandidateResponseDTO;
import crm.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import crm.myhrcrmproject.dto.vacancyDTO.VacancyShortResponseDTO;
import crm.myhrcrmproject.repository.VacancyRepository;
import crm.myhrcrmproject.service.validation.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CandidateConverter {

    private final VacancyRepository vacancyRepository;

    public CandidateResponseDTO toDTO(Candidate entity) {
        CandidateResponseDTO dto = new CandidateResponseDTO();

        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setAddress(entity.getAddress());
        dto.setStatus(entity.getStatus());
        if (entity.getVacancy() != null) {
            VacancyShortResponseDTO vacanciesShortResponseDTO = new VacancyShortResponseDTO(
                    entity.getVacancy().getId(),
                    entity.getVacancy().getJobTitle(),
                    entity.getVacancy().getSalary()
            );
            dto.setVacancy(vacanciesShortResponseDTO);
        }
        Optional.ofNullable(entity.getInterviewList()).ifPresent(
                (list) -> list.stream()
                        .map((item) -> item) // todo добавить метод в интервью конвертере
                        .collect(Collectors.toList()));
        return dto;
    }


    public Candidate fromDTO(Candidate entity, CandidateRequestDTO request) {
        Optional.ofNullable(request.getFirstName()).ifPresent(entity::setFirstName);
        Optional.ofNullable(request.getLastName()).ifPresent(entity::setLastName);
        Optional.ofNullable(request.getDateOfBirth()).ifPresent(entity::setDateOfBirth);
        Optional.ofNullable(request.getEmail()).ifPresent(entity::setEmail);
        Optional.ofNullable(request.getPhone()).ifPresent(entity::setPhone);
        Optional.ofNullable(request.getAddress()).ifPresent(entity::setAddress);
        Optional.ofNullable(request.getStatus()).ifPresent(entity::setStatus);
        Optional.ofNullable(request.getVacancyId()).ifPresent(
                id -> entity.setVacancy(vacancyRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException
                                ("Vacancy with id " + request.getVacancyId() + " not found"))));

        return entity;
    }


    public Candidate newEntity() {

        return new Candidate();
    }

    public CandidateShortResponseDTO toShortDTO(Candidate entity) {
        return CandidateShortResponseDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .status(entity.getStatus())
                .build();
    }
}
