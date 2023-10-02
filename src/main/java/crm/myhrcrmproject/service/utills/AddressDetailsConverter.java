package crm.myhrcrmproject.service.utills;

import crm.myhrcrmproject.domain.AddressDetails;
import crm.myhrcrmproject.dto.addressDetailsDTO.AddressDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressDetailsConverter {
    public AddressDetailsDTO toDTO(AddressDetails entity) {
        return AddressDetailsDTO.builder()
                .address1(entity.getAddress1())
                .address2(entity.getAddress2())
                .city(entity.getCity())
                .state(entity.getState())
                .zip(entity.getZip())
                .country(entity.getCountry())
                .build();
    }


    public AddressDetails fromDTO(AddressDetails entity, AddressDetailsDTO request) {
        Optional.ofNullable(request.getAddress1()).ifPresent(entity::setAddress1);
        Optional.ofNullable(request.getAddress2()).ifPresent(entity::setAddress2);
        Optional.ofNullable(request.getCity()).ifPresent(entity::setCity);
        Optional.ofNullable(request.getState()).ifPresent(entity::setState);
        Optional.ofNullable(request.getZip()).ifPresent(entity::setZip);
        Optional.ofNullable(request.getCountry()).ifPresent(entity::setCountry);
        return entity;
    }

    public AddressDetails newEntity() {
        return new AddressDetails();
    }
}
