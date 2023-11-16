package com.myhrcrmproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhrcrmproject.dto.compensationDTO.CompensationRequestDTO;
import com.myhrcrmproject.service.CompensationService;
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
class CompensationControllerIntegrationTest {
    static CompensationRequestDTO requestDTO;

    @Value("/api/compensations")
    private String basePath;

    @Autowired
    private CompensationService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public static void setUp() {
        requestDTO = new CompensationRequestDTO();
        requestDTO.setSalary(2000.00);
        requestDTO.setPaymentDate(LocalDate.of(2023, 10, 15));
        requestDTO.setComments("Test comments");
        requestDTO.setCandidateId(1);
        requestDTO.setContractId(2);
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
                .andExpect(jsonPath("$.comments").value("Test comments"));
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
        var requestDTO = new CompensationRequestDTO();
        requestDTO.setComments("New Comments");

        mockMvc.perform(put(basePath + "/" + existingEntityId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(existingEntityId))
                .andExpect(jsonPath("$.comments").value("New Comments"));
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
    void authorisedUser_update_withoutBody_shouldReturnStatus400() throws Exception {
        mockMvc.perform(put(basePath + "/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void authorisedUser_deleteCandidate_shouldReturn204() throws Exception {
        // save new Candidate in repository
        String existingEntityId = String.valueOf(service.create(requestDTO).getId());

        mockMvc.perform(delete(basePath + "/" + existingEntityId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void unAuthorisedUser_deleteCandidate_shouldReturn403() throws Exception {
        // save new Candidate in repository
        String existingEntityId = String.valueOf(service.create(requestDTO).getId());

        mockMvc.perform(delete(basePath + "/" + existingEntityId))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void authorisedUser_findAllByCandidateId() throws Exception {
        service.create(requestDTO);

        mockMvc.perform(get(basePath + "/findAllByCandidate/1"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void authorisedUser_findAllByContractId_shouldReturn200() throws Exception {
        service.create(requestDTO);

        mockMvc.perform(get(basePath + "/findAllByContract/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }
}