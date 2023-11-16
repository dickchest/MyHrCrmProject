package com.myhrcrmproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhrcrmproject.domain.enums.CandidateStatus;
import com.myhrcrmproject.dto.candidateDTO.CandidateRequestDTO;
import com.myhrcrmproject.dto.contactDetailsDTO.ContactDetailsDTO;
import com.myhrcrmproject.service.CandidateService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class CandidateControllerIntegrationTest {

    static CandidateRequestDTO requestDTO;

    @Value("/api/candidates")
    private String basePath;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CandidateService service;

    @BeforeAll
    public static void setUp() {
        requestDTO = new CandidateRequestDTO();
        requestDTO.setFirstName("TestName");
        requestDTO.setLastName("TestLastName");
        requestDTO.setDateOfBirth(LocalDate.of(1990, 10, 1));
    }

    @Test
    void notAuthorisedUser_findAll_shouldReturnStatus403() throws Exception {
        mockMvc.perform(get(basePath))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void authorisedUser_findAll_shouldReturnStatus200() throws Exception {
        mockMvc.perform(get(basePath))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @WithMockUser
    void authorisedUser_findById_shouldReturnStatus200() throws Exception {
        String id = String.valueOf(service.create(requestDTO).getId());

        mockMvc.perform(get(basePath + "/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.firstName").value("TestName"));
    }

    @Test
    @WithMockUser
    void authorisedUser_findById_shouldReturnStatus404() throws Exception {
        mockMvc.perform(get(basePath + "/0"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void authorisedUser_createNew_shouldReturnStatus201() throws Exception {
        mockMvc.perform(post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @WithMockUser
    void authorisedUser_update_shouldReturnStatus202() throws Exception {
        // save new Candidate in repository
        String existingEntityId = String.valueOf(service.create(requestDTO).getId());
        // create request for update
        var requestDTO = new CandidateRequestDTO();
        requestDTO.setFirstName("ChangedTestName");

        mockMvc.perform(put(basePath + "/" + existingEntityId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(existingEntityId))
                .andExpect(jsonPath("$.firstName").value("ChangedTestName"));
    }

    @Test
    @WithMockUser
    void authorisedUser_update_shouldReturnStatus404() throws Exception {
        mockMvc.perform(put(basePath + "/0")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void authorisedUser_update_shouldReturnStatus400() throws Exception {
        mockMvc.perform(put(basePath + "/0")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void authorisedAdmin_deleteCandidate_shouldReturn204() throws Exception {
        // save new Candidate in repository
        String existingEntityId = String.valueOf(service.create(requestDTO).getId());

        mockMvc.perform(delete(basePath + "/" + existingEntityId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void authorisedUser_deleteCandidate_shouldReturn403() throws Exception {
        // save new Candidate in repository
        String existingCandidateId = String.valueOf(service.create(requestDTO).getId());

        mockMvc.perform(delete(basePath + "/" + existingCandidateId))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void authorisedUser_findAllByVacancyId() throws Exception {
        service.create(requestDTO);

        mockMvc.perform(get(basePath + "/findAllByVacancy/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void authorisedUser_findAllByStatusId() throws Exception {
        //    save new Candidate in repository with status ACTIVE
        requestDTO.setStatus(CandidateStatus.NOT_ACTIVE);
        ContactDetailsDTO contactDetails = new ContactDetailsDTO();
        contactDetails.setEmail("000testemail000@example.com");
        requestDTO.setContactDetails(contactDetails);

        service.create(requestDTO);

        mockMvc.perform(get(basePath + "/findAllByStatus/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }
}