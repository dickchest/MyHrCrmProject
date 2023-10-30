package com.myhrcrmproject.service;

import com.myhrcrmproject.domain.Employee;
import com.myhrcrmproject.repository.EmployeeRepository;
import com.myhrcrmproject.service.utills.EmployeeConverter;
import com.myhrcrmproject.service.validation.NotFoundException;
import com.myhrcrmproject.dto.employeeDTO.EmployeeRequestDTO;
import com.myhrcrmproject.dto.employeeDTO.EmployeeResponseDTO;
import com.myhrcrmproject.service.auth.SecurityHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.NotAcceptableStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class EmployeeService implements CommonService<EmployeeRequestDTO, EmployeeResponseDTO> {
    private final EmployeeRepository repository;
    private final EmployeeConverter converter;
    private final SecurityHelper securityHelper;

    @Override
    public List<EmployeeResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponseDTO findById(Integer id) {
        Employee entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee with id " + id + " not found!"));

        return converter.toDTO(entity);
    }

    @Override
    public EmployeeResponseDTO create(EmployeeRequestDTO requestDTO) {
        Employee entity = converter.fromDTO(converter.newEntity(), requestDTO);

        return converter.toDTO(repository.save(entity));
    }

    @Override
    public EmployeeResponseDTO update(Integer id, EmployeeRequestDTO requestDTO) {
        Employee existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee with id: " + id + " not found!"));

        // check if user has access to this entity
        if (!securityHelper.isAuthUserEqualsEmployee(existingEntity)) {
            throw new NotAcceptableStatusException("You have not permission to access this entity");
        }

        // filled in existing fields with new dates
        converter.fromDTO(existingEntity, requestDTO);
        repository.save(existingEntity);

        return converter.toDTO(existingEntity);
    }

    @Override
    public void delete(Integer id) {
        Employee entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee with id: " + id + " not found!"));
        repository.delete(entity);
    }
}
