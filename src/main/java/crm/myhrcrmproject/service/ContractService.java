package crm.myhrcrmproject.service;

import crm.myhrcrmproject.domain.Candidate;
import crm.myhrcrmproject.domain.Client;
import crm.myhrcrmproject.domain.Employee;
import crm.myhrcrmproject.domain.Contract;
import crm.myhrcrmproject.domain.enums.CandidateStatus;
import crm.myhrcrmproject.domain.enums.ContractType;
import crm.myhrcrmproject.dto.contractDTO.ContractRequestDTO;
import crm.myhrcrmproject.dto.contractDTO.ContractResponseDTO;
import crm.myhrcrmproject.repository.CandidateRepository;
import crm.myhrcrmproject.repository.ClientRepository;
import crm.myhrcrmproject.repository.EmployeeRepository;
import crm.myhrcrmproject.repository.ContractRepository;
import crm.myhrcrmproject.service.auth.SecurityHelper;
import crm.myhrcrmproject.service.utills.ContractConverter;
import crm.myhrcrmproject.service.utills.Helper;
import crm.myhrcrmproject.service.validation.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContractService implements CommonService<ContractRequestDTO, ContractResponseDTO> {
    private final ContractRepository repository;
    private final ContractConverter converter;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;
    private final CandidateRepository candidateRepository;
    private final SecurityHelper securityHelper;

    public List<ContractResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    public ContractResponseDTO findById(Integer id) {
        Contract entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contract with id " + id + " not found!"));
        return converter.toDTO(entity);
    }

    public ContractResponseDTO create(ContractRequestDTO requestDTO) {
        Contract entity = converter.fromDTO(converter.newEntity(), requestDTO);

        // extra methods
        // set current auth user as employee
        if (entity.getEmployee() == null) {
            Optional<Employee> employee = securityHelper.getCurrentAuthEmployeeId();
            employee.ifPresent(entity::setEmployee);
        }


        entity.setCreateDate(LocalDateTime.now());
        entity.setUpdateDate(LocalDateTime.now());

        return converter.toDTO(repository.save(entity));
    }

    public void delete(Integer id) {
        Contract entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contract with id: " + id + " not found!"));
        repository.delete(entity);
    }

    public ContractResponseDTO update(Integer id, ContractRequestDTO requestDTO) {
        Contract existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contract with id: " + id + " not found!"));

        // filled in existing fields with new dates
        converter.fromDTO(existingEntity, requestDTO);
        // do extra procedures
        // remember employee who updated the entry

        Optional<Employee> employee = securityHelper.getCurrentAuthEmployeeId();
        employee.ifPresent(existingEntity::setEmployee);

        existingEntity.setUpdateDate(LocalDateTime.now());

        repository.save(existingEntity);

        return converter.toDTO(existingEntity);
    }

    // find All by Status(status)
    public List<ContractResponseDTO> findAllByContractTypeId(Integer id) {
        return Helper.findAllByEnumId(
                id,
                ContractType.class,
                repository::findAllByContractType,
                converter::toDTO
        );
    }

    // find All by Employee id
    public List<ContractResponseDTO> findAllByEmployeeId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                employeeRepository,
                repository::findByEmployee,
                converter::toDTO
        );
    }

    // find All by Client id
    public List<ContractResponseDTO> findAllByClientId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                clientRepository,
                repository::findByClient,
                converter::toDTO
        );
    }

    // find All by Candidate id
    public List<ContractResponseDTO> findAllByCandidateId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                candidateRepository,
                repository::findByCandidate,
                converter::toDTO
        );
    }

    // find All active contracts
    public List<ContractResponseDTO> findAllActiveContracts() {
        LocalDate date = LocalDate.now();
        List<Contract> list = repository.findByStartDateBeforeAndEndDateAfter(
                date.plusDays(1), date.minusDays(1));
        return list.stream()
                .map(converter::toDTO)
                .toList();
    }

    // todo: добавить методы для поиска заканчивающихся контрактов
}
