package crm.myhrcrmproject.service;

import crm.myhrcrmproject.domain.Employee;
import crm.myhrcrmproject.domain.Interview;
import crm.myhrcrmproject.domain.enums.InterviewStatus;
import crm.myhrcrmproject.dto.interviewDTO.InterviewDateRequestDTO;
import crm.myhrcrmproject.dto.interviewDTO.InterviewRequestDTO;
import crm.myhrcrmproject.dto.interviewDTO.InterviewResponseDTO;
import crm.myhrcrmproject.repository.EmployeeRepository;
import crm.myhrcrmproject.repository.InterviewRepository;
import crm.myhrcrmproject.service.utills.InterviewConverter;
import crm.myhrcrmproject.service.validation.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<InterviewResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    public InterviewResponseDTO findById(Integer id) {
        Interview entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Interview with id " + id + " not found!"));
        return converter.toDTO(entity);
    }

    public InterviewResponseDTO create(InterviewRequestDTO requestDTO) {
        Interview entity = converter.fromDTO(converter.newEntity(), requestDTO);

        // extra methods
        // set status
        if (Optional.ofNullable(requestDTO.getStatus()).isEmpty()) {
            entity.setStatus(InterviewStatus.SCHEDULED);
        }

        return converter.toDTO(repository.save(entity));
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
        // ....

        repository.save(existingEntity);

        return converter.toDTO(existingEntity);
    }

    // find All by Status(status)
    public List<InterviewResponseDTO> findAllByStatusId(Integer id) {
        InterviewStatus status = Optional.of(InterviewStatus.values()[id])
                .orElseThrow(() -> new NotFoundException("No status found with id: " + id));
        List<Interview> list = repository.findByStatus(status);
        return list.stream()
                .map(converter::toDTO)
                .toList();
    }

    // find All by Employee id
    public List<InterviewResponseDTO> findAllByEmployeeId(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entity with id " + id + " not found!"));
        List<Interview> list = repository.findByEmployee(employee);
        return list.stream()
                .map(converter::toDTO)
                .toList();
    }

    // find All by Date and Employee id
    public List<InterviewResponseDTO> findAllByDateAndEmployeeId(InterviewDateRequestDTO requestDTO, Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entity with id " + id + " not found!"));
        LocalDateTime dateTime = requestDTO.getDateTime();
        List<Interview> list = repository.findByDateTimeAndEmployee(dateTime, employee);
        return list.stream()
                .map(converter::toDTO)
                .toList();
    }
}