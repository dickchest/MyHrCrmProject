package crm.myhrcrmproject.service.utills;

import crm.myhrcrmproject.domain.Communication;
import crm.myhrcrmproject.dto.communicationDTO.CommunicationResponseDTO;
import crm.myhrcrmproject.dto.communicationDTO.CommunicationRequestDTO;
import crm.myhrcrmproject.dto.communicationDTO.CommunicationShortResponseDTO;
import crm.myhrcrmproject.repository.CandidateRepository;
import crm.myhrcrmproject.repository.ClientRepository;
import crm.myhrcrmproject.repository.EmployeeRepository;
import crm.myhrcrmproject.repository.VacancyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CommunicationConverter {
    private final CandidateConverter candidateConverter;
    private final EmployeeConverter employeeConverter;
    private final CandidateRepository candidateRepository;
    private final EmployeeRepository employeeRepository;
    private final ClientConverter clientConverter;
    private final ClientRepository clientRepository;
    private final VacancyConverter vacancyConverter;
    private final VacancyRepository vacancyRepository;

    public CommunicationResponseDTO toDTO(Communication entity) {
        return CommunicationResponseDTO.builder()
                .id(entity.getId())
                .communicationDateTime(entity.getCommunicationDateTime())
                .communicationType(entity.getCommunicationType())
                .createDate(entity.getCreateDate())
                .updateDate(entity.getUpdateDate())
                .candidate(entity.getCandidate() != null ?
                        candidateConverter.toShortDTO(entity.getCandidate()) : null)
                .employee(entity.getEmployee() != null ?
                        employeeConverter.toShortDTO(entity.getEmployee()) : null)
                .client(entity.getClient() != null ?
                        clientConverter.toShortDTO(entity.getClient()) : null)
                .vacancy(entity.getVacancy() != null ?
                        vacancyConverter.toShortDTO(entity.getVacancy()) : null)
                .build();
    }

    public Communication fromDTO(Communication entity, CommunicationRequestDTO request) {
        Optional.ofNullable(request.getCommunicationDateTime()).ifPresent(entity::setCommunicationDateTime);
        Optional.ofNullable(request.getCommunicationType()).ifPresent(entity::setCommunicationType);
        Helper.setEntityById(
                request::getClientId, entity::setClient, clientRepository, "Client");
        Helper.setEntityById(
                request::getCandidateId, entity::setCandidate, candidateRepository, "Candidate");
        Helper.setEntityById(
                request::getVacancyId, entity::setVacancy, vacancyRepository, "Vacancy");
        Helper.setEntityById(
                request::getEmployeeId, entity::setEmployee, employeeRepository, "Employee");
        return entity;
    }

    public Communication newEntity() {
        return new Communication();
    }

    public CommunicationShortResponseDTO toShortDTO(Communication entity) {
        return CommunicationShortResponseDTO.builder()
                .id(entity.getId())
                .communicationDateTime(entity.getCommunicationDateTime())
                .communicationType(entity.getCommunicationType())
                .candidate(entity.getCandidate() != null ?
                        candidateConverter.toShortDTO(entity.getCandidate()) : null)
                .employee(entity.getEmployee() != null ?
                        employeeConverter.toShortDTO(entity.getEmployee()) : null)
                .client(entity.getClient() != null ?
                        clientConverter.toShortDTO(entity.getClient()) : null)
                .build();
    }
}