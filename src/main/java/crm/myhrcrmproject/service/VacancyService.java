package crm.myhrcrmproject.service;

import crm.myhrcrmproject.domain.Vacancy;
import crm.myhrcrmproject.domain.enums.VacancyStatus;
import crm.myhrcrmproject.dto.vacanciesDTO.VacanciesRequestDTO;
import crm.myhrcrmproject.dto.vacanciesDTO.VacanciesResponseDTO;
import crm.myhrcrmproject.repository.VacanciesRepository;
import crm.myhrcrmproject.service.utills.VacanciesConverter;
import crm.myhrcrmproject.service.validation.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Getter
@AllArgsConstructor
public class VacancyService extends GenericService<Vacancy, VacanciesRequestDTO, VacanciesResponseDTO> {
    private final VacanciesRepository repository;
    private final VacanciesConverter converter;

    @Override
    protected Vacancy entityAfterCreateProcedures(Vacancy entity, VacanciesRequestDTO requestDTO) {
        if (requestDTO.getStatus() == null) {
            entity.setStatus(VacancyStatus.OPEN);
        }
        return entity;
    }

    @Override
    protected Vacancy entityAfterUpdateProcedures(Vacancy entity, VacanciesRequestDTO requestDTO) {
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
