package crm.myhrcrmproject.service.utills;

import crm.myhrcrmproject.domain.Interview;
import crm.myhrcrmproject.dto.interviewDTO.InterviewResponseDTO;
import crm.myhrcrmproject.dto.interviewDTO.InterviewRequestDTO;
import crm.myhrcrmproject.dto.interviewDTO.InterviewShortResponseDTO;
import crm.myhrcrmproject.repository.CandidateRepository;
import crm.myhrcrmproject.service.validation.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class InterviewConverter {
    private final CandidateConverter candidateConverter;
    private final EmployeeConverter employeeConverter;
    private final CandidateRepository candidateRepository;

    public InterviewResponseDTO toDTO(Interview entity) {
        return InterviewResponseDTO.builder()
                .id(entity.getId())
                .dateTime(entity.getDateTime())
                .location(entity.getLocation())
                .comments(entity.getComments())
                .status(entity.getStatus())
                .candidate(
                        entity.getCandidate() != null ?
                                candidateConverter.toShortDTO(entity.getCandidate()) :
                                null
                )
                .employee(
                        entity.getEmployee() != null ?
                                employeeConverter.toShortDTO(entity.getEmployee()) :
                                null
                )
                .build();
    }


    public Interview fromDTO(Interview entity, InterviewRequestDTO request) {
        Optional.ofNullable(request.getDateTime()).ifPresent(entity::setDateTime);
        Optional.ofNullable(request.getLocation()).ifPresent(entity::setLocation);
        Optional.ofNullable(request.getComments()).ifPresent(entity::setComments);
        Optional.ofNullable(request.getCandidateId()).ifPresent(
                id -> entity.setCandidate(candidateRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException
                                ("Candidate with id " + request.getCandidateId() + " not found"))));
        return entity;
    }

    public Interview newEntity() {
        return new Interview();
    }

    public InterviewShortResponseDTO toShortDTO(Interview entity) {
        return InterviewShortResponseDTO.builder()
                .id(entity.getId())
                .dateTime(entity.getDateTime())
                .status(entity.getStatus())
                .comments(entity.getComments())
                .employee(
                        entity.getEmployee() != null ?
                                employeeConverter.toShortDTO(entity.getEmployee()) :
                                null
                )
                .build();
    }
}
