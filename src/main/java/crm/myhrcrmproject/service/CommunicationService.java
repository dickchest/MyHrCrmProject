package crm.myhrcrmproject.service;

import crm.myhrcrmproject.domain.*;
import crm.myhrcrmproject.domain.enums.CommunicationType;
import crm.myhrcrmproject.domain.enums.ContractType;
import crm.myhrcrmproject.dto.communicationDTO.CommunicationRequestDTO;
import crm.myhrcrmproject.dto.communicationDTO.CommunicationResponseDTO;
import crm.myhrcrmproject.dto.contractDTO.ContractResponseDTO;
import crm.myhrcrmproject.repository.*;
import crm.myhrcrmproject.service.utills.CommunicationConverter;
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
public class CommunicationService implements CommonService<CommunicationRequestDTO, CommunicationResponseDTO> {
    private final CommunicationRepository repository;
    private final CommunicationConverter converter;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;
    private final CandidateRepository candidateRepository;
    private final VacancyRepository vacancyRepository;

    public List<CommunicationResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    public CommunicationResponseDTO findById(Integer id) {
        Communication entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Communication with id " + id + " not found!"));
        return converter.toDTO(entity);
    }

    public CommunicationResponseDTO create(CommunicationRequestDTO requestDTO) {
        Communication newEntity = converter.newEntity();

//        // if date&time haven't been adjusted, set current date&time
//        if (requestDTO.getCommunicationDateTime() == null) {
//            newEntity.setCommunicationDateTime(LocalDateTime.now());
//        }

        Communication entity = converter.fromDTO(converter.newEntity(), requestDTO);

        // extra methods
        // todo доделать, что б автоматически заносился employee кто меняет эту запись
        entity.setCreateDate(LocalDateTime.now());
        entity.setUpdateDate(LocalDateTime.now());

        return converter.toDTO(repository.save(entity));
    }

    public void delete(Integer id) {
        Communication entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Communication with id: " + id + " not found!"));
        repository.delete(entity);
    }

    public CommunicationResponseDTO update(Integer id, CommunicationRequestDTO requestDTO) {
        Communication existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Communication with id: " + id + " not found!"));

        // filled in existing fields with new dates
        converter.fromDTO(existingEntity, requestDTO);
        // do extra procedures
        // ....
        existingEntity.setUpdateDate(LocalDateTime.now());

        repository.save(existingEntity);

        return converter.toDTO(existingEntity);
    }

    // find All by CommunicationTypeId
    public List<CommunicationResponseDTO> findAllByCommunicationTypeId(Integer id) {
        CommunicationType type = Optional.ofNullable(CommunicationType.values()[id])
                .orElseThrow(() -> new NotFoundException("No communication type found with id: " + id));
        List<Communication> list = repository.findAllByCommunicationType(type);
        return list.stream()
                .map(converter::toDTO)
                .toList();
    }
    
    // find All by Employee id
    public List<CommunicationResponseDTO> findAllByEmployeeId(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entity with id " + id + " not found!"));
        List<Communication> list = repository.findByEmployee(employee);
        return list.stream()
                .map(converter::toDTO)
                .toList();
    }

    // find All by Client id
    public List<CommunicationResponseDTO> findAllByClientId(Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entity with id " + id + " not found!"));
        List<Communication> list = repository.findByClient(client);
        return list.stream()
                .map(converter::toDTO)
                .toList();
    }

    // find All by Candidate id
    public List<CommunicationResponseDTO> findAllByCandidateId(Integer id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entity with id " + id + " not found!"));
        List<Communication> list = repository.findByCandidate(candidate);
        return list.stream()
                .map(converter::toDTO)
                .toList();
    }

    public List<CommunicationResponseDTO> findAllByVacancyId(Integer id) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entity with id " + id + " not found!"));
        List<Communication> list = repository.findByVacancy(vacancy);
        return list.stream()
                .map(converter::toDTO)
                .toList();
    }

}
