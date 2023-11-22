package com.myhrcrmproject.service;

import com.myhrcrmproject.domain.Employee;
import com.myhrcrmproject.domain.Task;
import com.myhrcrmproject.domain.Vacancy;
import com.myhrcrmproject.domain.enums.VacancyStatus;
import com.myhrcrmproject.dto.taskDTO.TaskRequestDTO;
import com.myhrcrmproject.dto.taskDTO.TaskResponseDTO;
import com.myhrcrmproject.dto.vacancyDTO.VacancyRequestDTO;
import com.myhrcrmproject.dto.vacancyDTO.VacancyResponseDTO;
import com.myhrcrmproject.repository.VacancyRepository;
import com.myhrcrmproject.service.utills.VacancyConverter;
import com.myhrcrmproject.service.validation.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class VacancyServiceTest {
    @Mock
    private VacancyRepository repository;
    @Mock
    private VacancyConverter converter;
    @Mock
    private VacancyRequestDTO requestDTO;
    @InjectMocks
    private VacancyService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDelete_throwNotFoundException() {
        // Arrange
        int id = 1;

        // Mock reposting to return an empty optional when findById is called
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act and assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.delete(id));
        assertEquals("Vacancy with id: " + id + " not found!", exception.getMessage());
    }

    @Test
    void testCreate_success() {
        // Arrange
        VacancyRequestDTO requestDTO = new VacancyRequestDTO();
        requestDTO.setStatus(VacancyStatus.CLOSED);

        Vacancy entity = new Vacancy();
        entity.setStatus(VacancyStatus.CLOSED);

        // Mock
        when(converter.fromDTO(any(), eq(requestDTO))).thenReturn(entity);
        when(repository.save(any())).thenReturn(entity);

        // Act
        VacancyResponseDTO responseDTO = service.create(requestDTO);

        // Assert
        verify(converter).fromDTO(any(), eq(requestDTO));
        verify(repository, times(1)).save(any(Vacancy.class));

        // Ensure that the default status is set
        assertEquals(VacancyStatus.CLOSED, entity.getStatus());
    }
}