package crm.myhrcrmproject.service.utills;

import crm.myhrcrmproject.domain.ContactDetails;
import crm.myhrcrmproject.domain.Employee;
import crm.myhrcrmproject.dto.employeeDTO.EmployeeRequestDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeeResponseDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeeShortResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeConverter {
    ContactDetailsConverter contactDetailsConverter;

    public EmployeeResponseDTO toDTO(Employee entity) {
        return EmployeeResponseDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .position(entity.getPosition())
                .contactDetails(
                        entity.getContactDetails() != null ?
                                contactDetailsConverter.toDTO(entity.getContactDetails()) :
                                null
                )
                .build();
    }


    public Employee fromDTO(Employee entity, EmployeeRequestDTO request) {
        Optional.ofNullable(request.getFirstName()).ifPresent(entity::setFirstName);
        Optional.ofNullable(request.getLastName()).ifPresent(entity::setLastName);
        Optional.ofNullable(request.getPosition()).ifPresent(entity::setPosition);

        if (request.getContactDetails() != null) {
            ContactDetails contactDetailsEntity = entity.getContactDetails();
            if (contactDetailsEntity == null) {
                contactDetailsEntity = new ContactDetails();
            }
            entity.setContactDetails(contactDetailsConverter.fromDTO(
                            contactDetailsEntity,
                            request.getContactDetails()));
        }
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
