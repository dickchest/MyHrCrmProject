package crm.myhrcrmproject.controller;

import crm.myhrcrmproject.domain.Vacancy;
import crm.myhrcrmproject.dto.vacanciesDTO.VacanciesRequestDTO;
import crm.myhrcrmproject.dto.vacanciesDTO.VacanciesResponseDTO;
import crm.myhrcrmproject.service.VacancyService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/vacancies")
@AllArgsConstructor
@Getter
public class VacancyController extends GenericController<Vacancy, VacanciesRequestDTO, VacanciesResponseDTO> {
    private final VacancyService service;

    @GetMapping("/findAllByStatus/{statusId}")
    public ResponseEntity<List<VacanciesResponseDTO>> findAllByStatus(@PathVariable("statusId") Integer statusId) {
        return new ResponseEntity<>(service.findAllByStatus(statusId), HttpStatus.OK);
    }
}
