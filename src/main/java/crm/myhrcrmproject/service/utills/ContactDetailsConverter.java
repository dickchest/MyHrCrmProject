package crm.myhrcrmproject.service.utills;

import crm.myhrcrmproject.domain.AddressDetails;
import crm.myhrcrmproject.domain.ContactDetails;
import crm.myhrcrmproject.dto.addressDetailsDTO.AddressDetailsDTO;
import crm.myhrcrmproject.dto.contactDetailsDTO.ContactDetailsDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ContactDetailsConverter {
    public ContactDetailsDTO toDTO(ContactDetails entity) {
        return ContactDetailsDTO.builder()
                .email(entity.getEmail())
                .homePhone(entity.getHomePhone())
                .mobilePhone(entity.getMobilePhone())
                .build();
    }


    public ContactDetails fromDTO(ContactDetails entity, ContactDetailsDTO request) {
        Optional.ofNullable(request.getEmail()).ifPresent(entity::setEmail);
        Optional.ofNullable(request.getHomePhone()).ifPresent(entity::setHomePhone);
        Optional.ofNullable(request.getMobilePhone()).ifPresent(entity::setMobilePhone);
        return entity;
    }

    public AddressDetails newEntity() {
        return new AddressDetails();
    }
}
