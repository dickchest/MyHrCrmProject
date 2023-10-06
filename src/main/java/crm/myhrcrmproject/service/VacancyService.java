package crm.myhrcrmproject.service;

import crm.myhrcrmproject.domain.Employee;
import crm.myhrcrmproject.domain.Vacancy;
import crm.myhrcrmproject.domain.enums.CandidateStatus;
import crm.myhrcrmproject.domain.enums.VacancyStatus;
import crm.myhrcrmproject.dto.vacancyDTO.VacancyRequestDTO;
import crm.myhrcrmproject.dto.vacancyDTO.VacancyResponseDTO;
import crm.myhrcrmproject.dto.vacancyDTO.VacancyShortResponseDTO;
import crm.myhrcrmproject.repository.EmployeeRepository;
import crm.myhrcrmproject.repository.VacancyRepository;
import crm.myhrcrmproject.service.utills.Helper;
import crm.myhrcrmproject.service.utills.VacancyConverter;
import crm.myhrcrmproject.service.validation.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
