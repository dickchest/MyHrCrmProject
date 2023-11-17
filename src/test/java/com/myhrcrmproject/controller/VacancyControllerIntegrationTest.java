package com.myhrcrmproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhrcrmproject.domain.enums.VacancyStatus;
import com.myhrcrmproject.dto.vacancyDTO.VacancyRequestDTO;
import com.myhrcrmproject.service.VacancyService;
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
class VacancyControllerIntegrationTest {

    static VacancyRequestDTO requestDTO;

    @Value("/api/vacancies")
    private String basePath;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VacancyService service;

    @BeforeAll
    public static void setUp() {
        requestDTO = new VacancyRequestDTO();
        requestDTO.setJobTitle("Test Title");
        requestDTO.setDescription("Test Description");
        requestDTO.setSalary(80000.0);
        requestDTO.setStartDate(LocalDate.of(2023, 11, 15));
        requestDTO.setEndDate(LocalDate.of(2023, 12, 15));
        requestDTO.setResponsibleEmployeeId(2);
    }

    @Test
    void unAuthorisedUser_findAll_shouldReturnStatus403() throws Exception {
        mockMvc.perform(get(basePath))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void authenticatedUser_findAll_shouldReturnStatus200() throws Exception {
        mockMvc.perform(get(basePath))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @WithMockUser
    void authenticatedUser_findById_shouldReturnStatus200() throws Exception {
        String id = String.valueOf(service.create(requestDTO).getId());

        mockMvc.perform(get(basePath + "/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.status").value("OPEN"));
    }

    @Test
    @WithMockUser
    void authenticatedUser_findById_shouldReturnStatus404() throws Exception {
        mockMvc.perform(get(basePath + "/0"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void unAuthenticatedUser_createNew_shouldReturnStatus404() throws Exception {
        mockMvc.perform(post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void authenticatedUser_createNew_shouldReturnStatus201() throws Exception {
        mockMvc.perform(post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void authenticatedUser_update_shouldReturnStatus202() throws Exception {
        // save new Entity in repository
        String existingEntityId = String.valueOf(service.create(requestDTO).getId());
        // create request for update
        var requestDTO = new VacancyRequestDTO();
        requestDTO.setStatus(VacancyStatus.CLOSED);

        mockMvc.perform(put(basePath + "/" + existingEntityId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(existingEntityId))
                .andExpect(jsonPath("$.status").value("CLOSED"));
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void authenticatedUser_update_shouldReturnStatus404() throws Exception {
        mockMvc.perform(put(basePath + "/0")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void authenticatedUser_update_withoutBody_shouldReturnStatus400() throws Exception {
        mockMvc.perform(put(basePath + "/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void authenticatedUser_delete_shouldReturn204() throws Exception {
        // save new Entity in repository
        String existingEntityId = String.valueOf(service.create(requestDTO).getId());

        mockMvc.perform(delete(basePath + "/" + existingEntityId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
    @Test
    @WithMockUser
    void authenticatedUser_findAllByStatusId() throws Exception {
        service.create(requestDTO);

        mockMvc.perform(get(basePath + "/findAllByStatus/0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @WithMockUser
    void authenticatedUser_findAllByEmployeeId() throws Exception {
        service.create(requestDTO);

        mockMvc.perform(get(basePath + "/findAllByEmployee/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }
}