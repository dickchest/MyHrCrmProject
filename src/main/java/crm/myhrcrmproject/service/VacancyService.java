package crm.myhrcrmproject.service;

import crm.myhrcrmproject.domain.Vacancy;
import crm.myhrcrmproject.dto.vacanciesDTO.VacanciesRequestDTO;
import crm.myhrcrmproject.dto.vacanciesDTO.VacanciesResponseDTO;
import crm.myhrcrmproject.repository.VacanciesRepository;
import crm.myhrcrmproject.service.utills.VacanciesConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;


@Service
@Getter
@AllArgsConstructor
public class VacancyService extends GenericService<Vacancy, VacanciesRequestDTO, VacanciesResponseDTO> {
    private final VacanciesRepository repository;
    private final VacanciesConverter converter;

    @Override
    protected Vacancy entityAfterCreateProcedures(Vacancy entity, VacanciesRequestDTO requestDTO) {
        return entity;
    }

    @Override
    protected Vacancy entityAfterUpdateProcedures(Vacancy entity, VacanciesRequestDTO requestDTO) {
        return entity;
    }
}
