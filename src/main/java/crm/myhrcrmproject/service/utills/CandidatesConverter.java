package crm.myhrcrmproject.service.utills;

import crm.myhrcrmproject.domain.Candidate;
import crm.myhrcrmproject.dto.candidatesDTO.CandidatesRequestDTO;
import crm.myhrcrmproject.dto.candidatesDTO.CandidatesResponseDTO;
import crm.myhrcrmproject.dto.candidatesDTO.CandidatesShortResponseDTO;
import crm.myhrcrmproject.dto.vacanciesDTO.VacanciesShortResponseDTO;
import crm.myhrcrmproject.repository.VacanciesRepository;
import crm.myhrcrmproject.service.validation.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CandidatesConverter implements Converter<Candidate, CandidatesRequestDTO, CandidatesResponseDTO> {

    private final VacanciesRepository vacanciesRepository;

    public CandidatesResponseDTO toDTO(Candidate entity) {
        CandidatesResponseDTO dto = new CandidatesResponseDTO();

        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setAddress(entity.getAddress());
        dto.setStatus(entity.getStatus());
        if (entity.getVacancy() != null) {
            VacanciesShortResponseDTO vacanciesShortResponseDTO = new VacanciesShortResponseDTO(
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


    public Candidate fromDTO(Candidate entity, CandidatesRequestDTO request) {
        Optional.ofNullable(request.getFirstName()).ifPresent(entity::setFirstName);
        Optional.ofNullable(request.getLastName()).ifPresent(entity::setLastName);
        Optional.ofNullable(request.getDateOfBirth()).ifPresent(entity::setDateOfBirth);
        Optional.ofNullable(request.getEmail()).ifPresent(entity::setEmail);
        Optional.ofNullable(request.getPhone()).ifPresent(entity::setPhone);
        Optional.ofNullable(request.getAddress()).ifPresent(entity::setAddress);
        Optional.ofNullable(request.getStatus()).ifPresent(entity::setStatus);
        Optional.ofNullable(request.getVacancyId()).ifPresent(
                id -> entity.setVacancy(vacanciesRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException
                                ("Vacancy with id " + request.getVacancyId() + " not found"))));

        return entity;
    }

    @Override
    public Candidate newEntity() {

        return new Candidate();
    }

    public CandidatesShortResponseDTO toShortDTO(Candidate entity) {
        return CandidatesShortResponseDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .status(entity.getStatus())
                .build();
    }
}
