package com.myhrcrmproject.service;

import com.myhrcrmproject.domain.Candidate;
import com.myhrcrmproject.dto.candidateDTO.CandidateRequestDTO;
import com.myhrcrmproject.dto.candidateDTO.CandidateResponseDTO;
import com.myhrcrmproject.repository.CandidateRepository;
import com.myhrcrmproject.repository.VacancyRepository;
import com.myhrcrmproject.service.utills.CandidateConverter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class CandidateServiceTest {

    @Mock
    private CandidateRepository repository;
    @Mock
    private CandidateConverter converter;
    @Mock
    private VacancyRepository vacancyRepository;

    @InjectMocks
    private CandidateService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllCandidates() {
        //given
        Candidate candidate1 = new Candidate();
        candidate1.setId(1);
        candidate1.setFirstName("testName1");

        Candidate candidate2 = new Candidate();
        candidate2.setId(2);
        candidate2.setFirstName("testName2");

        List<Candidate> candidates = List.of(candidate1, candidate2);

        CandidateResponseDTO candidateResponseDTO1 = new CandidateResponseDTO();
        candidateResponseDTO1.setId(1);
        candidateResponseDTO1.setFirstName("testName1");

        CandidateResponseDTO candidateResponseDTO2 = new CandidateResponseDTO();
        candidateResponseDTO2.setId(2);
        candidateResponseDTO2.setFirstName("testName2");

        List<CandidateResponseDTO> expectedResponseDTO = List.of(candidateResponseDTO1, candidateResponseDTO2);
        //when
        when(repository.findAll()).thenReturn(candidates);
        when(converter.toDTO(candidate1)).thenReturn(candidateResponseDTO1);
        when(converter.toDTO(candidate2)).thenReturn(candidateResponseDTO2);
        //then
        List<CandidateResponseDTO> result = service.findAll();
        verify(repository, times(1)).findAll();
        assertEquals(expectedResponseDTO, result);
    }

    @Test
    void testFindByIdCandidate() {
        //given
        Candidate candidate1 = new Candidate();
        candidate1.setId(1);
        candidate1.setFirstName("testName1");

        CandidateResponseDTO candidateResponseDTO1 = new CandidateResponseDTO();
        candidateResponseDTO1.setId(1);
        candidateResponseDTO1.setFirstName("testName1");

        //when
        when(repository.findById(1)).thenReturn(Optional.of(candidate1));
        when(converter.toDTO(candidate1)).thenReturn(candidateResponseDTO1);

        // when: perform the actual test
        CandidateResponseDTO result = service.findById(1);

        //then
        verify(repository, times(1)).findById(anyInt());
        assertEquals(candidateResponseDTO1, result);
    }

    @Test
    void testCreateCandidate() {
        // given
        CandidateRequestDTO requestDTO = new CandidateRequestDTO();
        requestDTO.setFirstName("testFirstName");
        requestDTO.setLastName("testLastName");

        Candidate candidate = new Candidate();
        candidate.setId(1);
        candidate.setFirstName("testFirstName");
        candidate.setLastName("testLastName");

        CandidateResponseDTO responseDTO = new CandidateResponseDTO();
        responseDTO.setId(1);
        responseDTO.setFirstName("testFirstName");
        responseDTO.setLastName("testLastName");

        // when
        when(converter.newEntity()).thenReturn(new Candidate());
        when(converter.fromDTO(new Candidate(),requestDTO)).thenReturn(candidate);
        when(repository.save(candidate)).thenReturn(candidate);
        when(converter.toDTO(candidate)).thenReturn(responseDTO);

        // when: perform the actual test
        CandidateResponseDTO result = service.create(requestDTO);

        // then
        verify(repository).save(candidate);
        assertEquals(responseDTO, result);
    }

    @Test
    void testDeleteCandidate() {
        // given
        // when
        when(repository.findById(1)).thenReturn(Optional.of(new Candidate()));
        // when: perform the actual test
        service.delete(1);
        // then
        verify(repository, times(1)).delete(any(Candidate.class));
    }

    @Test
    void testUpdateCandidate() {
        // given
        Integer candidateId = 1;

        CandidateRequestDTO requestDTO = new CandidateRequestDTO();
        requestDTO.setLastName("UpdatedTestName");

        Candidate candidate = new Candidate();
        candidate.setId(1);
        candidate.setLastName("TestName");

        Candidate updatedCandidate = new Candidate();
        candidate.setId(1);
        candidate.setLastName("UpdatedTestName");

        CandidateResponseDTO responseDTO = new CandidateResponseDTO();
        responseDTO.setId(1);
        responseDTO.setLastName("UpdatedTestName");

        // when
        when(repository.findById(candidateId)).thenReturn(Optional.of(candidate));
        when(converter.fromDTO(candidate, requestDTO)).thenReturn(updatedCandidate);
        when(repository.save(any(Candidate.class))).thenReturn(updatedCandidate);
        when(converter.toDTO(any(Candidate.class))).thenReturn(responseDTO);

        CandidateResponseDTO result = service.update(candidateId, requestDTO);
        // then
        verify(repository).save(any(Candidate.class));
        verify(repository).findById(candidateId);
        assertEquals(responseDTO, result);

    }

    @Test
    void testFindAllCandidatesByStatusId() {

    }

    @Test
    void testFindAllCandidatesByVacancyId() {

    }
}