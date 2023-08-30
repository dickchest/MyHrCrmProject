package crm.myhrcrmproject.service.utills;

import crm.myhrcrmproject.domain.Employee;
import crm.myhrcrmproject.dto.employeeDTO.EmployeeRequestDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeeResponseDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeeShortResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeConverter {

    public EmployeeResponseDTO toDTO(Employee entity) {
        return EmployeeResponseDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .position(entity.getPosition())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .build();
    }


    public Employee fromDTO(Employee entity, EmployeeRequestDTO request) {
        Optional.ofNullable(request.getFirstName()).ifPresent(entity::setFirstName);
        Optional.ofNullable(request.getLastName()).ifPresent(entity::setLastName);
        Optional.ofNullable(request.getPosition()).ifPresent(entity::setPosition);
        Optional.ofNullable(request.getEmail()).ifPresent(entity::setEmail);
        Optional.ofNullable(request.getPhone()).ifPresent(entity::setPhone);
        return entity;
    }

    public Employee newEntity() {
        return new Employee();
    }

    public EmployeeShortResponseDTO toShortDTO(Employee entity) {
        return EmployeeShortResponseDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .position(entity.getPosition())
                .build();
    }
}
