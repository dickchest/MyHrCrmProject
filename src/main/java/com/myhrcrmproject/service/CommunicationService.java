package com.myhrcrmproject.service;

import com.myhrcrmproject.domain.Communication;
import com.myhrcrmproject.domain.enums.CommunicationType;
import com.myhrcrmproject.dto.communicationDTO.CommunicationRequestDTO;
import com.myhrcrmproject.dto.communicationDTO.CommunicationResponseDTO;
import com.myhrcrmproject.repository.*;
import com.myhrcrmproject.service.utills.CommunicationConverter;
import com.myhrcrmproject.service.utills.Helper;
import com.myhrcrmproject.service.validation.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
        return Helper.findAllByEnumId(
                id,
                CommunicationType.class,
                repository::findAllByCommunicationType,
                converter::toDTO
        );
    }
    
    // find All by Employee id
    public List<CommunicationResponseDTO> findAllByEmployeeId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                employeeRepository,
                repository::findByEmployee,
                converter::toDTO
        );
    }

    // find All by Client id
    public List<CommunicationResponseDTO> findAllByClientId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                clientRepository,
                repository::findByClient,
                converter::toDTO
        );
    }

    // find All by Candidate id
    public List<CommunicationResponseDTO> findAllByCandidateId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                candidateRepository,
                repository::findByCandidate,
                converter::toDTO
        );
    }

    public List<CommunicationResponseDTO> findAllByVacancyId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                vacancyRepository,
                repository::findByVacancy,
                converter::toDTO
        );
    }

}
