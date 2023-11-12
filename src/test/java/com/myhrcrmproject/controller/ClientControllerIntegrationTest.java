package com.myhrcrmproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhrcrmproject.dto.clientDTO.ClientRequestDTO;
import com.myhrcrmproject.service.ClientService;
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
class ClientControllerIntegrationTest {
    static ClientRequestDTO clientRequestDTO;

    @Value("/api/clients")
    private String basePath;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientService service;

    @BeforeAll
    public static void setUp() {
        clientRequestDTO = new ClientRequestDTO();
        clientRequestDTO.setCompanyName("TestName");
        clientRequestDTO.setDescription("TestDescription");
    }

    @Test
    void notAuthorisedUser_findAll_shouldReturnStatus403() throws Exception {
        mockMvc.perform(get(basePath))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void authorisedUser_findAll_shouldReturnStatus200() throws Exception {
        service.create(clientRequestDTO);

        mockMvc.perform(get(basePath))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @WithMockUser
    void authorisedUser_findById_shouldReturnStatus200() throws Exception {
        String id = String.valueOf(service.create(clientRequestDTO).getId());

        mockMvc.perform(get(basePath + "/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.companyName").value("TestName"));
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
                        .content(objectMapper.writeValueAsString(clientRequestDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @WithMockUser
    void authorisedUser_update_shouldReturnStatus202() throws Exception {
        // save new Candidate in repository
        String existingClientId = String.valueOf(service.create(clientRequestDTO).getId());
        // create request for update
        ClientRequestDTO requestDTO = new ClientRequestDTO();
        requestDTO.setCompanyName("ChangedTestName");

        mockMvc.perform(put(basePath + "/" + existingClientId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(existingClientId))
                .andExpect(jsonPath("$.companyName").value("ChangedTestName"));
    }

    @Test
    @WithMockUser
    void authorisedUser_update_shouldReturnStatus404() throws Exception {
        mockMvc.perform(put(basePath + "/0")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRequestDTO)))
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
        String existingClientId = String.valueOf(service.create(clientRequestDTO).getId());

        mockMvc.perform(delete(basePath + "/" + existingClientId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void authorisedUser_deleteCandidate_shouldReturn403() throws Exception {
        // save new Candidate in repository
        String existingId = String.valueOf(service.create(clientRequestDTO).getId());

        mockMvc.perform(delete(basePath + "/" + existingId))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}