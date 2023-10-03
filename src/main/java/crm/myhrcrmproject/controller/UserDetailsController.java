package crm.myhrcrmproject.controller;

import crm.myhrcrmproject.dto.vacancyDTO.VacancyRequestDTO;
import crm.myhrcrmproject.dto.vacancyDTO.VacancyResponseDTO;
import crm.myhrcrmproject.service.UserDetailsService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
@Getter
public class UserDetailsController extends GenericController<VacancyRequestDTO, VacancyResponseDTO>{
    private final UserDetailsService service;
}
