package crm.myhrcrmproject.service;

import crm.myhrcrmproject.domain.*;
import crm.myhrcrmproject.domain.enums.CandidateStatus;
import crm.myhrcrmproject.domain.enums.TaskStatus;
import crm.myhrcrmproject.dto.taskDTO.TaskDateRequestDTO;
import crm.myhrcrmproject.dto.taskDTO.TaskRequestDTO;
import crm.myhrcrmproject.dto.taskDTO.TaskResponseDTO;
import crm.myhrcrmproject.dto.taskDTO.TaskShortResponseDTO;
import crm.myhrcrmproject.repository.*;
import crm.myhrcrmproject.service.auth.SecurityHelper;
import crm.myhrcrmproject.service.utills.Helper;
import crm.myhrcrmproject.service.utills.TaskConverter;
import crm.myhrcrmproject.service.validation.NotFoundException;
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
public class TaskService implements CommonService<TaskRequestDTO, TaskResponseDTO> {
    private final TaskRepository repository;
    private final TaskConverter converter;
    private final EmployeeRepository employeeRepository;
    private final CandidateRepository candidateRepository;
    private final VacancyRepository vacancyRepository;
    private final SecurityHelper securityHelper;

    public List<TaskResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    public TaskResponseDTO findById(Integer id) {
        Task entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id " + id + " not found!"));

        // check if user has access to this entity
        if (!securityHelper.isAuthUserEqualsEmployee(entity.getEmployee())) {
            throw new NotAcceptableStatusException("You have not permission to access this entity");
        }

        return converter.toDTO(entity);
    }

    public TaskResponseDTO create(TaskRequestDTO requestDTO) {

        Task entity = converter.fromDTO(converter.newEntity(), requestDTO);

        // extra methods
        // todo доделать, что б автоматически заносился employee кто меняет эту запись
        if (entity.getEmployee() == null) {
            Optional<Employee> employee = securityHelper.getCurrentAuthEmployeeId();
            if (employee.isPresent()) {
                entity.setEmployee(employee.get());
            }
        }
        entity.setCreateDate(LocalDateTime.now());
        entity.setUpdateDate(LocalDateTime.now());

        return converter.toDTO(repository.save(entity));
    }

    public void delete(Integer id) {
        Task entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id: " + id + " not found!"));

        // check if user has access to this entity
        if (!securityHelper.isAuthUserEqualsEmployee(entity.getEmployee())) {
            throw new NotAcceptableStatusException("You have not permission to access this entity");
        }
        repository.delete(entity);
    }

    public TaskResponseDTO update(Integer id, TaskRequestDTO requestDTO) {
        Task existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id: " + id + " not found!"));

        // check if user has access to this entity
        if (!securityHelper.isAuthUserEqualsEmployee(existingEntity.getEmployee())) {
            throw new NotAcceptableStatusException("You have not permission to access this entity");
        }

        // filled in existing fields with new dates
        converter.fromDTO(existingEntity, requestDTO);
        // do extra procedures
        // ....
        existingEntity.setUpdateDate(LocalDateTime.now());

        repository.save(existingEntity);

        return converter.toDTO(existingEntity);
    }

    // find All by TaskStatusId
    public List<TaskResponseDTO> findAllByTaskStatusId(Integer id) {
        return Helper.findAllByEnumId(
                id,
                TaskStatus.class,
                repository::findAllByStatus,
                converter::toDTO
        );
    }

    // find All by Employee id
    public List<TaskResponseDTO> findAllByEmployeeId(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee with id " + id + " not found!"));

        // check if user has access to this entity
        if (!securityHelper.isAuthUserEqualsEmployee(employee)) {
            throw new NotAcceptableStatusException("You have not permission to access this entity");
        }

        return repository.findAllByEmployee(employee).stream()
                .map(converter::toDTO)
                .toList();
    }

    // find All by Candidate id
    public List<TaskResponseDTO> findAllByCandidateId(Integer id) {
        return Helper.findAllByEntityId(
                id, candidateRepository,
                repository::findAllByCandidate,
                converter::toDTO
        );
    }

    // find All by Vacancy id
    public List<TaskResponseDTO> findAllByVacancyId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                vacancyRepository,
                repository::findAllByVacancy,
                converter::toDTO
        );
    }

    // find All by Date
    public List<TaskResponseDTO> findAllByStartDate(TaskDateRequestDTO requestDTO) {
        LocalDate date = requestDTO.getDate();
        List<Task> list = repository.findAllByStartDate(date);
        return list.stream()
                .map(converter::toDTO)
                .toList();
    }

    // find All By Date And Employee Id
    public List<TaskShortResponseDTO> findAllByDateAndEmployeeId(Integer id, TaskDateRequestDTO requestDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee with id " + id + " not found!"));

        // check if user has access to this entity
        if (!securityHelper.isAuthUserEqualsEmployee(employee)) {
            throw new NotAcceptableStatusException("You have not permission to access this entity");
        }
        LocalDate date = requestDTO.getDate();
        List<Task> list = repository.findAllByStartDateAndEmployee(date, employee);
        return list.stream()
                .map(converter::toShortDTO)
                .toList();
    }

    // find All By Candidate Id And Employee Id
    public List<TaskShortResponseDTO> findAllByCandidateIdAndEmployeeId(Integer candidateId, Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee with id " + employeeId + " not found!"));

        // check if user has access to this entity
        if (!securityHelper.isAuthUserEqualsEmployee(employee)) {
            throw new NotAcceptableStatusException("You have not permission to access this entity");
        }

        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new NotFoundException("Candidate with id " + candidateId + " not found!"));
        List<Task> list = repository.findAllByCandidateAndEmployee(candidate, employee);
        return list.stream()
                .map(converter::toShortDTO)
                .toList();
    }

    // find All By Status Id and Employee Id
    public List<TaskShortResponseDTO> findAllByStatusIdAndEmployeeId(Integer statusId, Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee with id " + employeeId + " not found!"));

        // check if user has access to this entity
        if (!securityHelper.isAuthUserEqualsEmployee(employee)) {
            throw new NotAcceptableStatusException("You have not permission to access this entity");
        }

        CandidateStatus status = Optional.ofNullable(CandidateStatus.values()[statusId]).
                orElseThrow(() -> new NotFoundException("No enum found with id: " + statusId));
        List<Task> list = repository.findAllByStatusAndEmployee(status, employee);
        return list.stream()
                .map(converter::toShortDTO)
                .toList();
    }

    // find All By Vacancy Id And Employee Id
    public List<TaskShortResponseDTO> findAllByVacancyIdAndEmployeeId(Integer vacancyId, Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee with id " + employeeId + " not found!"));

        // check if user has access to this entity
        if (!securityHelper.isAuthUserEqualsEmployee(employee)) {
            throw new NotAcceptableStatusException("You have not permission to access this entity");
        }

        Vacancy vacancy = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new NotFoundException("Vacancy with id " + vacancyId + " not found!"));
        List<Task> list = repository.findAllByVacancyAndEmployee(vacancy, employee);
        return list.stream()
                .map(converter::toShortDTO)
                .toList();
    }
}
