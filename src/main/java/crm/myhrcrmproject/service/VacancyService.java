package crm.myhrcrmproject.service;

import crm.myhrcrmproject.domain.Employee;
import crm.myhrcrmproject.domain.Vacancy;
import crm.myhrcrmproject.domain.enums.VacancyStatus;
import crm.myhrcrmproject.dto.vacancyDTO.VacancyRequestDTO;
import crm.myhrcrmproject.dto.vacancyDTO.VacancyResponseDTO;
import crm.myhrcrmproject.repository.EmployeeRepository;
import crm.myhrcrmproject.repository.VacancyRepository;
import crm.myhrcrmproject.service.utills.VacancyConverter;
import crm.myhrcrmproject.service.validation.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class VacancyService implements CommonService<Vacancy, VacancyRequestDTO, VacancyResponseDTO> {
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
        // do extra procedures
        entityAfterUpdateProcedures(existingEntity);

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

    private Vacancy entityAfterCreateProcedures(Vacancy entity, VacancyRequestDTO requestDTO) {
        if (requestDTO.getStatus() == null) {
            entity.setStatus(VacancyStatus.OPEN);
        }
        return entity;
    }

    private Vacancy entityAfterUpdateProcedures(Vacancy entity) {
        return entity;
    }

    public List<VacancyResponseDTO> findAllByStatusId(Integer id) {
        VacancyStatus status = Optional.of(VacancyStatus.values()[id])
                .orElseThrow(() -> new NotFoundException("No status found with id: " + id));
        List<Vacancy> list = repository.findByStatus(status).get();
        return list.stream()
                .map(converter::toDTO)
                .toList();
    }

    public List<VacancyResponseDTO> findAllByEmployeeId(Integer id) {
        Employee entity = employeeRepository.findById(id).orElseThrow(() -> new NotFoundException("Entity with id " + id + " not found!"));
        List<Vacancy> list = repository.findByEmployee(entity).get();
        return list.stream()
                .map(converter::toDTO)
                .toList();
    }
}
