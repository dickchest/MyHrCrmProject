package crm.myhrcrmproject.service;

import crm.myhrcrmproject.controller.VacancyController;
import crm.myhrcrmproject.domain.Candidate;
import crm.myhrcrmproject.domain.Vacancy;
import crm.myhrcrmproject.domain.enums.VacancyStatus;
import crm.myhrcrmproject.dto.vacanciesDTO.VacanciesRequestDTO;
import crm.myhrcrmproject.dto.vacanciesDTO.VacanciesResponseDTO;
import crm.myhrcrmproject.repository.CandidatesRepository;
import crm.myhrcrmproject.repository.VacanciesRepository;
import crm.myhrcrmproject.service.utills.CandidatesConverter;
import crm.myhrcrmproject.service.utills.VacanciesConverter;
import crm.myhrcrmproject.service.validation.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Setter
@Getter
public class VacancyService implements CommonService<Vacancy, VacanciesRequestDTO, VacanciesResponseDTO> {
    private final VacanciesRepository repository;
    private final VacanciesConverter converter;

    @Override
    public List<VacanciesResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VacanciesResponseDTO findById(Integer id) {
        Vacancy entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacancy with id " + id + " not found!"));
        return converter.toDTO(entity);
    }

    @Override
    public VacanciesResponseDTO create(VacanciesRequestDTO requestDTO) {
        Vacancy entity = converter.fromDTO(converter.newEntity(), requestDTO);

        entityAfterCreateProcedures(entity, requestDTO);
        return converter.toDTO(repository.save(entity));
    }

    @Override
    public VacanciesResponseDTO update(Integer id, VacanciesRequestDTO requestDTO) {
        Vacancy existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidate with id: " + id + " not found!"));

        // filled in existing fields with new dates
        converter.fromDTO(existingEntity, requestDTO);
        // do extra procedures
        entityAfterUpdateProcedures(existingEntity);

        getRepository().save(existingEntity);

        return getConverter().toDTO(existingEntity);
    }

    @Override
    public void delete(Integer id) {
        Vacancy entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidate with id: " + id + " not found!"));
        repository.delete(entity);
    }

    // Extra method for create and update

    private Vacancy entityAfterCreateProcedures(Vacancy entity, VacanciesRequestDTO requestDTO) {
        if (requestDTO.getStatus() == null) {
            entity.setStatus(VacancyStatus.OPEN);
        }
        return entity;
    }

    private Vacancy entityAfterUpdateProcedures(Vacancy entity) {
        return entity;
    }

    public List<VacanciesResponseDTO> findAllByStatus(Integer id) {
        VacancyStatus status = Optional.of(VacancyStatus.values()[id])
                .orElseThrow(() -> new NotFoundException("No status found with id: " + id));
        List<Vacancy> vacanciesList = repository.findByStatus(status);
        if (vacanciesList.isEmpty()) {
            throw new NotFoundException("No vacancy found with status: " + status);
        }
        return vacanciesList.stream()
                .map(getConverter()::toDTO)
                .toList();
    }
}
