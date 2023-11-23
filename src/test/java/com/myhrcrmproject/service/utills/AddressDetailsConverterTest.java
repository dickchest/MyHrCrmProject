package com.myhrcrmproject.service.utills;

import com.myhrcrmproject.domain.AddressDetails;
import com.myhrcrmproject.dto.addressDetailsDTO.AddressDetailsDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class AddressDetailsConverterTest {
    @InjectMocks
    private AddressDetailsConverter converter;

    @Test
    void testConvertToDtoMethod() {
        AddressDetails entity = new AddressDetails();
        entity.setId(1);
        entity.setStreet("Test Street");
        entity.setCity("Test City");
        entity.setState("Test State");
        entity.setZip("Test Zip");
        entity.setCountry("Test Country");

        AddressDetailsDTO result = converter.toDTO(entity);

        assertNotNull(result);
        assertEquals(entity.getZip(), result.getZip());
    }

    @Test
    void testConvertFromDtoMethod() {
        AddressDetailsDTO request = new AddressDetailsDTO();
        request.setStreet("Test Street");
        request.setCity("Test City");
        request.setState("Test State");
        request.setZip("Test Zip");
        request.setCountry("Test Country");

        AddressDetails result = converter.fromDTO(
                converter.newEntity(), request);

        assertNotNull(result);
        assertEquals(request.getZip(), result.getZip());
    }
}