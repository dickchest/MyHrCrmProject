package com.myhrcrmproject.service.utills;

import com.myhrcrmproject.domain.Interview;
import com.myhrcrmproject.dto.interviewDTO.InterviewRequestDTO;
import com.myhrcrmproject.dto.interviewDTO.InterviewResponseDTO;
import com.myhrcrmproject.dto.interviewDTO.InterviewShortResponseDTO;
import com.myhrcrmproject.repository.CandidateRepository;
import com.myhrcrmproject.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class InterviewConverter {
    private final CandidateConverter candidateConverter;
    private final EmployeeConverter employeeConverter;
    private final CandidateRepository candidateRepository;
    private final EmployeeRepository employeeRepository;

    public InterviewResponseDTO toDTO(Interview entity) {
        return InterviewResponseDTO.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .time(entity.getTime())
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
        Optional.ofNullable(request.getDate()).ifPresent(entity::setDate);
        Optional.ofNullable(request.getTime()).ifPresent(entity::setTime);
        Optional.ofNullable(request.getLocation()).ifPresent(entity::setLocation);
        Optional.ofNullable(request.getComments()).ifPresent(entity::setComments);
        Helper.setEntityById(
                request::getCandidateId, entity::setCandidate, candidateRepository, "Candidate");
        Helper.setEntityById(
                request::getEmployeeId, entity::setEmployee, employeeRepository, "Employee");
        Optional.ofNullable(request.getStatus()).ifPresent(entity::setStatus);
        return entity;
    }

    public Interview newEntity() {
        return new Interview();
    }

    public InterviewShortResponseDTO toShortDTO(Interview entity) {
        return InterviewShortResponseDTO.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .time(entity.getTime())
                .status(entity.getStatus())
                .comments(entity.getComments())
                .candidate(
                        entity.getCandidate() != null ?
                                candidateConverter.toShortDTO(entity.getCandidate()) :
                                null
                )
                .build();
    }
}
