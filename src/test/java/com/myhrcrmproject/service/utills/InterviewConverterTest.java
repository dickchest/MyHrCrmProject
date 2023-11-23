package com.myhrcrmproject.service.utills;

import com.myhrcrmproject.domain.Interview;
import com.myhrcrmproject.dto.interviewDTO.InterviewResponseDTO;
import com.myhrcrmproject.dto.interviewDTO.InterviewShortResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class InterviewConverterTest {
    @InjectMocks
    private InterviewConverter converter;

    @Test
    void testToDTO_noCandidateEmployee() {
        Interview interview = new Interview();
        interview.setId(1);
        interview.setLocation("test location");

        InterviewResponseDTO response = converter.toDTO(interview);

        assertNotNull(response);
    }

    @Test
    void testToShortDTO_noCandidate() {
        Interview interview = new Interview();
        interview.setId(1);
        interview.setLocation("test location");

        InterviewShortResponseDTO response = converter.toShortDTO(interview);

        assertNotNull(response);
    }
}