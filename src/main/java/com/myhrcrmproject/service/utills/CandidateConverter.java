package com.myhrcrmproject.service.utills;

import com.myhrcrmproject.domain.AddressDetails;
import com.myhrcrmproject.domain.Candidate;
import com.myhrcrmproject.domain.ContactDetails;
import com.myhrcrmproject.domain.Vacancy;
import com.myhrcrmproject.repository.VacancyRepository;
import com.myhrcrmproject.service.validation.NotFoundException;
import com.myhrcrmproject.domain.enums.CandidateStatus;
import com.myhrcrmproject.dto.candidateDTO.CandidateRequestDTO;
import com.myhrcrmproject.dto.candidateDTO.CandidateResponseDTO;
import com.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import com.myhrcrmproject.dto.vacancyDTO.VacancyShortResponseDTO;
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

        // if candidate just have been applied for vacancy set status IN_PROGRESS
        if (request.getVacancyId() != null) {
            Vacancy vacancy = vacancyRepository.findById(request.getVacancyId())
                    .orElseThrow(() -> new NotFoundException
                            ("Vacancy with id " + request.getVacancyId() + " not found"));
            entity.setVacancy(vacancy);
            entity.setStatus(CandidateStatus.IN_PROCESS);
            System.out.println(entity.getStatus());
        }

        // if status was provided, set status
        Optional.ofNullable(request.getStatus()).
                ifPresent(entity::setStatus);
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
