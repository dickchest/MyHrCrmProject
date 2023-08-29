package crm.myhrcrmproject.controller;

import crm.myhrcrmproject.domain.Vacancy;
import crm.myhrcrmproject.domain.enums.VacancyStatus;
import crm.myhrcrmproject.dto.vacanciesDTO.VacanciesRequestDTO;
import crm.myhrcrmproject.dto.vacanciesDTO.VacanciesResponseDTO;
import crm.myhrcrmproject.service.GenericService;
import crm.myhrcrmproject.service.VacancyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/vacancies")
public class VacancyController extends GenericController<Vacancy, VacanciesRequestDTO, VacanciesResponseDTO> {
    private final VacancyService vacancyService;

    protected VacancyController(GenericService<Vacancy, VacanciesRequestDTO, VacanciesResponseDTO> service, VacancyService vacancyService) {
        super(service);
        this.vacancyService = vacancyService;
    }

    @GetMapping("/findAllByStatus/{status}")
    public ResponseEntity<List<VacanciesResponseDTO>> findAllByStatus(@PathVariable("status") Integer statusID) {
        return new ResponseEntity<>(vacancyService.findAllByStatus(statusID), HttpStatus.OK);
    }
}
