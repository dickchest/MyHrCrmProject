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


@Service
@Getter
@AllArgsConstructor
public class VacancyService extends GenericService<Vacancy, VacanciesRequestDTO, VacanciesResponseDTO> {
    private final VacanciesRepository repository;
    private final VacanciesConverter converter;

    @Override
    protected Vacancy entityAfterCreateProcedures(Vacancy entity, VacanciesRequestDTO requestDTO) {
        if (requestDTO.getStatus() == null) {
            entity.setVacancyStatus(VacancyStatus.OPEN);
        }
        return entity;
    }

    @Override
    protected Vacancy entityAfterUpdateProcedures(Vacancy entity, VacanciesRequestDTO requestDTO) {
        return entity;
    }

    public List<VacanciesResponseDTO> findAllByStatus(VacancyStatus status) {
        List<Vacancy> vacanciesList = repository.findByVacancyStatus(status);
        if (vacanciesList.isEmpty()) {
            throw new NotFoundException("No vacancy found with status: " + status);
        }
        return vacanciesList.stream()
                .map(getConverter()::toDTO)
                .toList();
    }


}
