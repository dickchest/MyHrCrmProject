package com.myhrcrmproject.service.utills;

import com.myhrcrmproject.domain.Interview;
import com.myhrcrmproject.domain.Task;
import com.myhrcrmproject.dto.interviewDTO.InterviewResponseDTO;
import com.myhrcrmproject.dto.interviewDTO.InterviewShortResponseDTO;
import com.myhrcrmproject.dto.taskDTO.TaskResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class TaskConverterTest {
    @InjectMocks
    private TaskConverter converter;

    @Test
    void testToDTO_noCandidateEmployeeVacancy() {
        Task entity = new Task();
        entity.setId(1);
        entity.setTitle("test entity");

        TaskResponseDTO response = converter.toDTO(entity);

        assertNotNull(response);
    }
}