package com.myhrcrmproject.service;

import com.myhrcrmproject.domain.Vacancy;
import com.myhrcrmproject.repository.EmployeeRepository;
import com.myhrcrmproject.repository.VacancyRepository;
import com.myhrcrmproject.service.utills.Helper;
import com.myhrcrmproject.service.utills.VacancyConverter;
import com.myhrcrmproject.service.validation.NotFoundException;
import com.myhrcrmproject.domain.enums.VacancyStatus;
import com.myhrcrmproject.dto.vacancyDTO.VacancyRequestDTO;
import com.myhrcrmproject.dto.vacancyDTO.VacancyResponseDTO;
import com.myhrcrmproject.dto.vacancyDTO.VacancyShortResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class VacancyService implements CommonService<VacancyRequestDTO, VacancyResponseDTO> {
    private final VacancyRepository repository;
    private final VacancyConverter converter;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<VacancyResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VacancyResponseDTO findById(Integer id) {
        Vacancy entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacancy with id " + id + " not found!"));
        return converter.toDTO(entity);
    }

    @Override
    public VacancyResponseDTO create(VacancyRequestDTO requestDTO) {
        Vacancy entity = converter.fromDTO(converter.newEntity(), requestDTO);

        entityAfterCreateProcedures(entity, requestDTO);
        return converter.toDTO(repository.save(entity));
    }

    @Override
    public VacancyResponseDTO update(Integer id, VacancyRequestDTO requestDTO) {
        Vacancy existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacancy with id: " + id + " not found!"));

        // filled in existing fields with new dates
        converter.fromDTO(existingEntity, requestDTO);

        repository.save(existingEntity);

        return converter.toDTO(existingEntity);
    }

    @Override
    public void delete(Integer id) {
        Vacancy entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacancy with id: " + id + " not found!"));
        repository.delete(entity);
    }

    // Extra method for create and update

    private void entityAfterCreateProcedures(Vacancy entity, VacancyRequestDTO requestDTO) {
        if (requestDTO.getStatus() == null) {
            entity.setStatus(VacancyStatus.OPEN);
        }
    }

    public List<VacancyResponseDTO> findAllByStatusId(Integer id) {
        return Helper.findAllByEnumId(
                id,
                VacancyStatus.class,
                repository::findByStatus,
                converter::toDTO
        );
    }

    public List<VacancyShortResponseDTO> findAllByEmployeeId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                employeeRepository,
                repository::findByEmployee,
                converter::toShortDTO
        );
    }
}
