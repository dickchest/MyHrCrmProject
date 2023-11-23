package com.myhrcrmproject.service.utills;

import com.myhrcrmproject.domain.ContactDetails;
import com.myhrcrmproject.domain.Employee;
import com.myhrcrmproject.dto.contactDetailsDTO.ContactDetailsDTO;
import com.myhrcrmproject.dto.employeeDTO.EmployeeRequestDTO;
import com.myhrcrmproject.dto.employeeDTO.EmployeeResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeConverterTest {
    @Mock
    private ContactDetailsConverter contactDetailsConverter;
    @InjectMocks
    private EmployeeConverter converter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToDto_withContactDetails() {
        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setId(1);
        contactDetails.setEmail("test@example.com");

        ContactDetailsDTO contactDetailsDTO = new ContactDetailsDTO();
        contactDetailsDTO.setEmail("test@example.com");

        Employee entity = new Employee();
        entity.setId(1);
        entity.setFirstName("Test Name");
        entity.setLastName("Test Lastname");
        entity.setPosition("Test Position");
        entity.setContactDetails(contactDetails);

        when(contactDetailsConverter.toDTO(any()))
                .thenReturn(contactDetailsDTO);

        EmployeeResponseDTO result = converter.toDTO(entity);

        assertNotNull(result);
        assertEquals("Test Name", result.getFirstName());
        assertEquals("Test Lastname", result.getLastName());
        assertEquals("Test Position", result.getPosition());
        assertEquals("test@example.com", result.getContactDetails().getEmail());

        verify(contactDetailsConverter).toDTO(entity.getContactDetails());
    }

    @Test
    void testToDto_withNoContactDetails() {
        Employee entity = new Employee();
        entity.setId(1);
        entity.setFirstName("Test Name");
        entity.setLastName("Test Lastname");
        entity.setPosition("Test Position");

        EmployeeResponseDTO result = converter.toDTO(entity);

        assertNotNull(result);
        assertEquals("Test Name", result.getFirstName());
        assertEquals("Test Lastname", result.getLastName());
        assertEquals("Test Position", result.getPosition());
    }

    @Test
    void testFromDto_withNo() {
        ContactDetailsDTO contactDetailsDTO = new ContactDetailsDTO();
        contactDetailsDTO.setEmail("example@email.com");

        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setId(1);
        contactDetails.setEmail("example@email.com");

        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO();
        requestDTO.setFirstName("Test Name");
        requestDTO.setLastName("Test Lastname");
        requestDTO.setPosition("Test Position");
        requestDTO.setContactDetails(contactDetailsDTO);

        Employee employee = new Employee();
        employee.setFirstName("Test Name");
        employee.setLastName("Test Lastname");
        employee.setPosition("Test Position");
        employee.setContactDetails(contactDetails);

        when(contactDetailsConverter.fromDTO(any(), any())).thenReturn(contactDetails);

        Employee result = converter.fromDTO(employee, requestDTO);

        assertNotNull(result);
        assertEquals("Test Name", result.getFirstName());
        assertEquals("Test Lastname", result.getLastName());
        assertEquals("Test Position", result.getPosition());

        verify(contactDetailsConverter, times(1)).fromDTO(contactDetails, contactDetailsDTO);
    }

}