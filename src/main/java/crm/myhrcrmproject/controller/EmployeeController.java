package crm.myhrcrmproject.controller;

import crm.myhrcrmproject.dto.employeeDTO.EmployeeRequestDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeeResponseDTO;
import crm.myhrcrmproject.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/employees")
@AllArgsConstructor
@Getter
public class EmployeeController extends GenericController<EmployeeRequestDTO, EmployeeResponseDTO> {
    private final EmployeeService service;

}
