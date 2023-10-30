package com.myhrcrmproject.service.utills;

import com.myhrcrmproject.domain.AddressDetails;
import com.myhrcrmproject.dto.addressDetailsDTO.AddressDetailsDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressDetailsConverter {
    public AddressDetailsDTO toDTO(AddressDetails entity) {
        return AddressDetailsDTO.builder()
                .street(entity.getStreet())
                .city(entity.getCity())
                .state(entity.getState())
                .zip(entity.getZip())
                .country(entity.getCountry())
                .build();
    }

    public AddressDetails fromDTO(AddressDetails entity, AddressDetailsDTO request) {
        Optional.ofNullable(request.getStreet()).ifPresent(entity::setStreet);
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
