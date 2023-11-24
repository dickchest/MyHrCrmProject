package com.myhrcrmproject.service.utills;

import com.myhrcrmproject.domain.Vacancy;
import com.myhrcrmproject.dto.vacancyDTO.VacancyResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class VacancyConverterTest {
    @InjectMocks
    private VacancyConverter vacancyConverter;

    @Test
    void testToDto_employeeIsNull() {
        Vacancy entity = new Vacancy();
        entity.setId(1);
        entity.setJobTitle("Test title");

        VacancyResponseDTO responseDTO = vacancyConverter.toDTO(entity);

        assertNotNull(responseDTO);
    }
}