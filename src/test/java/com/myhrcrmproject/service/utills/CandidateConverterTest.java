package com.myhrcrmproject.service.utills;

import com.myhrcrmproject.domain.AddressDetails;
import com.myhrcrmproject.domain.Candidate;
import com.myhrcrmproject.domain.ContactDetails;
import com.myhrcrmproject.domain.Vacancy;
import com.myhrcrmproject.domain.enums.CandidateStatus;
import com.myhrcrmproject.dto.addressDetailsDTO.AddressDetailsDTO;
import com.myhrcrmproject.dto.candidateDTO.CandidateRequestDTO;
import com.myhrcrmproject.dto.contactDetailsDTO.ContactDetailsDTO;
import com.myhrcrmproject.repository.VacancyRepository;
import com.myhrcrmproject.service.validation.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class CandidateConverterTest {
    @Mock
    private ContactDetailsConverter contactDetailsConverter;
    @Mock
    private AddressDetailsConverter addressDetailsConverter;
    @Mock
    private VacancyRepository vacancyRepository;
    @InjectMocks
    private CandidateConverter converter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFromDTO() {
        // Mock data for the test

        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setId(1);
        contactDetails.setEmail("test@email.com");

        AddressDetails addressDetails = new AddressDetails();
        addressDetails.setCountry("Test Country");

        Vacancy vacancy = new Vacancy();
        vacancy.setId(1);
        vacancy.setJobTitle("Test JobTitle");
        vacancy.setSalary(50000.00);

        CandidateRequestDTO requestDTO = CandidateRequestDTO.builder()
                .firstName("TestName")
                .lastName("LastName")
                .dateOfBirth(LocalDate.of(2015, 5, 15))
                .contactDetails(new ContactDetailsDTO())
                .addressDetails(new AddressDetailsDTO())
                .vacancyId(1)
                .status(CandidateStatus.ACTIVE)
                .build();

        Candidate candidate = new Candidate();
        candidate.setFirstName("TestName");
        candidate.setContactDetails(contactDetails);

        // Mocking behavior of the repository and converters
        when(contactDetailsConverter.fromDTO(any(), any())).thenReturn(contactDetails);
        when(addressDetailsConverter.fromDTO(any(), any())).thenReturn(addressDetails);
        when(vacancyRepository.findById(eq(1))).thenReturn(Optional.of(vacancy));

        // Testing the method
        Candidate result = converter.fromDTO(candidate, requestDTO);

        // Assertions
        assertEquals("TestName", result.getFirstName());
        assertEquals("LastName", result.getLastName());
        assertEquals(LocalDate.of(2015, 5, 15), result.getDateOfBirth());
        assertEquals(contactDetails, result.getContactDetails());
        assertEquals(addressDetails, result.getAddressDetails());
        assertEquals(vacancy, result.getVacancy());
        assertEquals(CandidateStatus.ACTIVE, result.getStatus());
    }

    @Test
    void testFromDTO_existAdressDetails() {
        // Mock data for the test

        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setId(1);
        contactDetails.setEmail("test@email.com");

        AddressDetails addressDetails = new AddressDetails();
        addressDetails.setCountry("Test Country");

        Vacancy vacancy = new Vacancy();
        vacancy.setId(1);
        vacancy.setJobTitle("Test JobTitle");

        CandidateRequestDTO requestDTO = CandidateRequestDTO.builder()
                .firstName("TestName")
                .lastName("LastName")
                .dateOfBirth(LocalDate.of(2015, 5, 15))
                .contactDetails(new ContactDetailsDTO())
                .addressDetails(new AddressDetailsDTO())
                .vacancyId(1)
                .status(CandidateStatus.ACTIVE)
                .build();

        Candidate candidate = new Candidate();
        candidate.setFirstName("TestName");
        candidate.setContactDetails(contactDetails);
        candidate.setAddressDetails(addressDetails);

        // Mocking behavior of the repository and converters
        when(contactDetailsConverter.fromDTO(any(), any())).thenReturn(contactDetails);
        when(addressDetailsConverter.fromDTO(any(), any())).thenReturn(addressDetails);
        when(vacancyRepository.findById(eq(1))).thenReturn(Optional.of(vacancy));

        // Testing the method
        Candidate result = converter.fromDTO(candidate, requestDTO);

        // Assertions
        assertEquals("TestName", result.getFirstName());
        assertEquals("LastName", result.getLastName());
        assertEquals(LocalDate.of(2015, 5, 15), result.getDateOfBirth());
        assertEquals(contactDetails, result.getContactDetails());
        assertEquals(addressDetails, result.getAddressDetails());
        assertEquals(vacancy, result.getVacancy());
        assertEquals(CandidateStatus.ACTIVE, result.getStatus());
    }

    @Test
    void testFromDTO_noContactDetails_noAddressDetails() {
        // Mock data for the test

        CandidateRequestDTO requestDTO = CandidateRequestDTO.builder()
                .firstName("TestName")
                .lastName("LastName")
                .dateOfBirth(LocalDate.of(2015, 5, 15))
                .status(CandidateStatus.ACTIVE)
                .build();

        // Mocking behavior of the repository and converters

        // Testing the method
        Candidate result = converter.fromDTO(converter.newEntity(), requestDTO);

        // Assertions
        assertNotNull(result);
        assertEquals("TestName", result.getFirstName());
        assertEquals("LastName", result.getLastName());
        assertEquals(LocalDate.of(2015, 5, 15), result.getDateOfBirth());
        assertEquals(CandidateStatus.ACTIVE, result.getStatus());
    }

    @Test
    void testFromDTO_noVacancy_throwNotFoundException() {
        // Mock data for the test
        Vacancy vacancy = new Vacancy();
        vacancy.setId(1);
        vacancy.setJobTitle("Test JobTitle");

        CandidateRequestDTO requestDTO = CandidateRequestDTO.builder()
                .firstName("TestName")
                .lastName("LastName")
                .dateOfBirth(LocalDate.of(2015, 5, 15))
                .vacancyId(2)
                .status(CandidateStatus.ACTIVE)
                .build();

        // Mocking behavior of the repository and converters
        when(vacancyRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Assertions
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> converter.fromDTO(new Candidate(), requestDTO));
        assertEquals("Vacancy with id " + requestDTO.getVacancyId() + " not found", exception.getMessage());
    }
}