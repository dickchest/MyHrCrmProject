package crm.myhrcrmproject.service.utills;

import crm.myhrcrmproject.domain.AddressDetails;
import crm.myhrcrmproject.domain.Candidate;
import crm.myhrcrmproject.domain.ContactDetails;
import crm.myhrcrmproject.dto.candidateDTO.CandidateRequestDTO;
import crm.myhrcrmproject.dto.candidateDTO.CandidateResponseDTO;
import crm.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import crm.myhrcrmproject.dto.vacancyDTO.VacancyShortResponseDTO;
import crm.myhrcrmproject.repository.VacancyRepository;
import crm.myhrcrmproject.service.validation.NotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CandidateConverter {

    private final VacancyRepository vacancyRepository;
    private final InterviewConverter interviewConverter;
    private final ContactDetailsConverter contactDetailsConverter;
    private final AddressDetailsConverter addressDetailsConverter;


    public CandidateConverter(VacancyRepository vacancyRepository, @Lazy InterviewConverter interviewConverter, ContactDetailsConverter contactDetailsConverter, AddressDetailsConverter addressDetailsConverter) {
        this.vacancyRepository = vacancyRepository;
        this.interviewConverter = interviewConverter;
        this.contactDetailsConverter = contactDetailsConverter;
        this.addressDetailsConverter = addressDetailsConverter;
    }

    public CandidateResponseDTO toDTO(Candidate entity) {
        CandidateResponseDTO dto = new CandidateResponseDTO();

        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setStatus(entity.getStatus());

        if (entity.getVacancy() != null) {
            VacancyShortResponseDTO vacanciesShortResponseDTO = new VacancyShortResponseDTO(
                    entity.getVacancy().getId(),
                    entity.getVacancy().getJobTitle(),
                    entity.getVacancy().getSalary()
            );
            dto.setVacancy(vacanciesShortResponseDTO);
        }

        if (entity.getContactDetails() != null) {
            dto.setContactDetails(contactDetailsConverter.toDTO(entity.getContactDetails()));
        }

        if (entity.getAddressDetails() != null) {
            dto.setAddressDetails(addressDetailsConverter.toDTO(entity.getAddressDetails()));
        }

        Optional.ofNullable(entity.getInterviewList()).ifPresent(
                (list) -> list.stream()
                        .map(interviewConverter::toShortDTO)
                        .collect(Collectors.toList()));
        return dto;
    }


    public Candidate fromDTO(Candidate entity, CandidateRequestDTO request) {
        Optional.ofNullable(request.getFirstName()).ifPresent(entity::setFirstName);
        Optional.ofNullable(request.getLastName()).ifPresent(entity::setLastName);
        Optional.ofNullable(request.getDateOfBirth()).ifPresent(entity::setDateOfBirth);

        if (request.getContactDetails() != null) {
            ContactDetails contactDetailsEntity = entity.getContactDetails();
            if (contactDetailsEntity == null) {
                contactDetailsEntity = new ContactDetails();
            }
            entity.setContactDetails(contactDetailsConverter.fromDTO(
                    contactDetailsEntity,
                    request.getContactDetails()));
        }

        if (request.getAddressDetails() != null) {
            AddressDetails addressDetailsEntity = entity.getAddressDetails();
            if (addressDetailsEntity == null) {
                addressDetailsEntity = new AddressDetails();
            }
            entity.setAddressDetails(addressDetailsConverter.fromDTO(
                    addressDetailsEntity,
                    request.getAddressDetails()));
        }

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
                .email(entity.getContactDetails().getEmail())
                .phone(entity.getContactDetails().getMobilePhone())
                .status(entity.getStatus())
                .build();
    }
}
