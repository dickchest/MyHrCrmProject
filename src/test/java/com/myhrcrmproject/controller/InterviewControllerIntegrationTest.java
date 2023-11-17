package com.myhrcrmproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhrcrmproject.domain.enums.InterviewStatus;
import com.myhrcrmproject.dto.interviewDTO.InterviewDateRequestDTO;
import com.myhrcrmproject.dto.interviewDTO.InterviewRequestDTO;
import com.myhrcrmproject.service.InterviewService;
import com.myhrcrmproject.service.auth.SecurityHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;

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
class InterviewControllerIntegrationTest {
    static InterviewRequestDTO requestDTO;

    @Value("/api/interviews")
    private String basePath;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InterviewService service;

    @MockBean
    private SecurityHelper helper;

    @BeforeAll
    public static void setUp() {
        requestDTO = new InterviewRequestDTO();
        requestDTO.setDate(LocalDate.of(2023, 11, 15));
        requestDTO.setTime(LocalTime.of(12, 0));
        requestDTO.setLocation("Conference Room");
        requestDTO.setComments("Interview comments");
        requestDTO.setCandidateId(1);
        requestDTO.setEmployeeId(2);
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
    void unAuthenticatedUser_findById_shouldReturnStatus406() throws Exception {
        String id = String.valueOf(service.create(requestDTO).getId());

        mockMvc.perform(get(basePath + "/" + id))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void authenticatedUser_findById_shouldReturnStatus200() throws Exception {
        // check if user has access to the entry
        Mockito.when(helper.isAuthUserEqualsEmployee(Mockito.any())).thenReturn(true);

        String id = String.valueOf(service.create(requestDTO).getId());

        mockMvc.perform(get(basePath + "/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.status").value("SCHEDULED"));
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
    void authenticatedUser_createNew_shouldReturnStatus201() throws Exception {
        mockMvc.perform(post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @WithMockUser
    void authenticatedUser_update_shouldReturnStatus202() throws Exception {
        // save new Entity in repository
        String existingEntityId = String.valueOf(service.create(requestDTO).getId());

        // create request for update
        var requestDTO = new InterviewRequestDTO();
        requestDTO.setStatus(InterviewStatus.NO_SHOW);

        mockMvc.perform(put(basePath + "/" + existingEntityId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(existingEntityId))
                .andExpect(jsonPath("$.status").value("NO_SHOW"));
    }

    @Test
    @WithMockUser
    void authenticatedUser_update_shouldReturnStatus404() throws Exception {
        mockMvc.perform(put(basePath + "/0")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void authenticatedUser_update_withoutBody_shouldReturnStatus400() throws Exception {
        mockMvc.perform(put(basePath + "/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void authenticatedUser_deleteCandidate_shouldReturn204() throws Exception {
        // save new entity in repository
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
        // check if user has access to the entry
        Mockito.when(helper.isAuthUserEqualsEmployee(Mockito.any())).thenReturn(true);

        service.create(requestDTO);

        mockMvc.perform(get(basePath + "/findAllByEmployee/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @WithMockUser
    void authenticatedUser_findAllByDateAndEmployeeId() throws Exception {
        // check if user has access to the entry
        Mockito.when(helper.isAuthUserEqualsEmployee(Mockito.any())).thenReturn(true);

        service.create(requestDTO);

        InterviewDateRequestDTO interviewDateRequestDTO = new InterviewDateRequestDTO(LocalDate.of(2023, 11, 15));

        mockMvc.perform(put(basePath + "/findAllByDateAndEmployee/2")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(interviewDateRequestDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @WithMockUser
    void authenticatedUser_findAllByDate() throws Exception {
        // check if user has access to the entry
        Mockito.when(helper.isAuthUserEqualsEmployee(Mockito.any())).thenReturn(true);

        service.create(requestDTO);

        InterviewDateRequestDTO interviewDateRequestDTO = new InterviewDateRequestDTO(LocalDate.of(2023, 11, 15));

        mockMvc.perform(put(basePath + "/findAllByDate")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(interviewDateRequestDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }
}