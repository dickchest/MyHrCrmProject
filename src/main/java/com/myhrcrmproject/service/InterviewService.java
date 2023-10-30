package com.myhrcrmproject.service;

import com.myhrcrmproject.domain.Employee;
import com.myhrcrmproject.domain.Interview;
import com.myhrcrmproject.dto.interviewDTO.InterviewDateRequestDTO;
import com.myhrcrmproject.dto.interviewDTO.InterviewRequestDTO;
import com.myhrcrmproject.dto.interviewDTO.InterviewResponseDTO;
import com.myhrcrmproject.dto.interviewDTO.InterviewShortResponseDTO;
import com.myhrcrmproject.repository.EmployeeRepository;
import com.myhrcrmproject.repository.InterviewRepository;
import com.myhrcrmproject.service.utills.Helper;
import com.myhrcrmproject.service.utills.InterviewConverter;
import com.myhrcrmproject.service.validation.NotFoundException;
import com.myhrcrmproject.domain.enums.InterviewStatus;
import com.myhrcrmproject.service.auth.SecurityHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.NotAcceptableStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InterviewService implements CommonService<InterviewRequestDTO, InterviewResponseDTO> {
    private final InterviewRepository repository;
    private final InterviewConverter converter;
    private final EmployeeRepository employeeRepository;
    private final SecurityHelper securityHelper;

    public List<InterviewResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    public InterviewResponseDTO findById(Integer id) {
        Interview entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Interview with id " + id + " not found!"));
        // check if user has access to this entity
        if (!securityHelper.isAuthUserEqualsEmployee(entity.getEmployee())) {
            throw new NotAcceptableStatusException("You have not permission to access this entity");
        }

        return converter.toDTO(entity);
    }

    public InterviewResponseDTO create(InterviewRequestDTO requestDTO) {
        Interview entity = converter.fromDTO(converter.newEntity(), requestDTO);

        // extra methods
        // set status
        if (Optional.ofNullable(requestDTO.getStatus()).isEmpty()) {
            entity.setStatus(InterviewStatus.SCHEDULED);
        }
        entity.setCreateDate(LocalDateTime.now());
        entity.setUpdateDate(LocalDateTime.now());
        repository.save(entity);
        return converter.toDTO(entity);
    }

    public void delete(Integer id) {
        Interview entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Interview with id: " + id + " not found!"));
        repository.delete(entity);
    }

    public InterviewResponseDTO update(Integer id, InterviewRequestDTO requestDTO) {
        Interview existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Interview with id: " + id + " not found!"));

        // filled in existing fields with new dates
        converter.fromDTO(existingEntity, requestDTO);
        // do extra procedures
        existingEntity.setUpdateDate(LocalDateTime.now());

        repository.save(existingEntity);

        return converter.toDTO(existingEntity);
    }

    // find All by Status(status)
    public List<InterviewShortResponseDTO> findAllByStatusId(Integer id) {
        return Helper.findAllByEnumId(
                id,
                InterviewStatus.class,
                repository::findByStatus,
                converter::toShortDTO
        );
    }

    // find All by Employee id
    public List<InterviewShortResponseDTO> findAllByEmployeeId(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entity with id " + id + " not found!"));

        // check if user has access to this entity
        if (!securityHelper.isAuthUserEqualsEmployee(employee)) {
            throw new NotAcceptableStatusException("You have not permission to access this entity");
        }

        return repository.findByEmployee(employee).stream()
                .map(converter::toShortDTO)
                .toList();
    }

    // find All by Date and Employee id
    public List<InterviewShortResponseDTO> findAllByDateAndEmployeeId(InterviewDateRequestDTO requestDTO, Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entity with id " + id + " not found!"));

        // check if user has access to this entity
        if (!securityHelper.isAuthUserEqualsEmployee(employee)) {
            throw new NotAcceptableStatusException("You have not permission to access this entity");
        }

        LocalDate date = requestDTO.getDate();
        List<Interview> list = repository.findByDateAndEmployee(date, employee);
        return list.stream()
                .map(converter::toShortDTO)
                .toList();
    }

    public List<InterviewShortResponseDTO> findAllByDate(InterviewDateRequestDTO requestDTO) {
        LocalDate date = requestDTO.getDate();
        List<Interview> list = repository.findByDate(date);
        return list.stream()
                .map(converter::toShortDTO)
                .toList();
    }
}
