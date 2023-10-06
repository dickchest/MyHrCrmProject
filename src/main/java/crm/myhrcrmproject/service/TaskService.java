package crm.myhrcrmproject.service;

import crm.myhrcrmproject.domain.*;
import crm.myhrcrmproject.domain.enums.TaskStatus;
import crm.myhrcrmproject.dto.taskDTO.TaskRequestDTO;
import crm.myhrcrmproject.dto.taskDTO.TaskResponseDTO;
import crm.myhrcrmproject.repository.*;
import crm.myhrcrmproject.service.utills.Helper;
import crm.myhrcrmproject.service.utills.TaskConverter;
import crm.myhrcrmproject.service.validation.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<TaskResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    public TaskResponseDTO findById(Integer id) {
        Task entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id " + id + " not found!"));
        return converter.toDTO(entity);
    }

    public TaskResponseDTO create(TaskRequestDTO requestDTO) {
        Task newEntity = converter.newEntity();

        Task entity = converter.fromDTO(converter.newEntity(), requestDTO);

        // extra methods
        // todo доделать, что б автоматически заносился employee кто меняет эту запись
        entity.setCreateDate(LocalDateTime.now());
        entity.setUpdateDate(LocalDateTime.now());

        return converter.toDTO(repository.save(entity));
    }

    public void delete(Integer id) {
        Task entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id: " + id + " not found!"));
        repository.delete(entity);
    }

    public TaskResponseDTO update(Integer id, TaskRequestDTO requestDTO) {
        Task existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id: " + id + " not found!"));

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
        TaskStatus status = Optional.of(TaskStatus.values()[id])
                .orElseThrow(() -> new NotFoundException("No task status found with id: " + id));
        List<Task> list = repository.findAllByStatus(status);
        return list.stream()
                .map(converter::toDTO)
                .toList();
    }

    // find All by Employee id
    public List<TaskResponseDTO> findAllByEmployeeId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                employeeRepository,
                repository::findByEmployee,
                converter::toDTO
        );
    }

    // find All by Candidate id
    public List<TaskResponseDTO> findAllByCandidateId(Integer id) {
        return Helper.findAllByEntityId(
                id, candidateRepository,
                repository::findByCandidate,
                converter::toDTO
        );
    }

    public List<TaskResponseDTO> findAllByVacancyId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                vacancyRepository,
                repository::findByVacancy,
                converter::toDTO
        );
    }

}
