package com.myhrcrmproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhrcrmproject.domain.enums.TaskStatus;
import com.myhrcrmproject.dto.interviewDTO.InterviewDateRequestDTO;
import com.myhrcrmproject.dto.taskDTO.TaskDateRequestDTO;
import com.myhrcrmproject.dto.taskDTO.TaskRequestDTO;
import com.myhrcrmproject.service.TaskService;
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
class TaskControllerIntegrationTest {

    static TaskRequestDTO requestDTO;

    @Value("/api/tasks")
    private String basePath;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskService service;

    @MockBean
    private SecurityHelper helper;

    @BeforeAll
    public static void setUp() {
        requestDTO = new TaskRequestDTO();
        requestDTO.setTitle("Task Title");
        requestDTO.setDescription("Task Description");
        requestDTO.setStartDate(LocalDate.of(2023, 11, 15));
        requestDTO.setEndDate(LocalDate.of(2023, 11, 20));
        requestDTO.setStatus(TaskStatus.IN_PROCESS);
        requestDTO.setCandidateId(1);
        requestDTO.setEmployeeId(2);
        requestDTO.setVacancyId(3);
    }

    @Test
    void unAuthorisedUser_findAll_shouldReturnStatus403() throws Exception {
        mockMvc.perform(get(basePath))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void authenticatedUser_findAll_shouldReturnStatus200() throws Exception {
        mockMvc.perform(get(basePath))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @WithMockUser
    void unAuthenticatedUser_findById_shouldReturnStatus403() throws Exception {
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
                .andExpect(jsonPath("$.status").value("IN_PROCESS"));
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
        // check if user has access to the entry
        Mockito.when(helper.isAuthUserEqualsEmployee(Mockito.any())).thenReturn(true);

        // save new Entity in repository
        String existingEntityId = String.valueOf(service.create(requestDTO).getId());

        // create request for update
        var requestDTO = new TaskRequestDTO();
        requestDTO.setStatus(TaskStatus.DONE);

        mockMvc.perform(put(basePath + "/" + existingEntityId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(existingEntityId))
                .andExpect(jsonPath("$.status").value("DONE"));
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
    void unAuthenticatedUser_delete_shouldReturn403() throws Exception {
        // save new entity in repository
        String existingEntityId = String.valueOf(service.create(requestDTO).getId());

        mockMvc.perform(delete(basePath + "/" + existingEntityId))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void authenticatedUser_delete_shouldReturn204() throws Exception {
        // check if user has access to the entry
        Mockito.when(helper.isAuthUserEqualsEmployee(Mockito.any())).thenReturn(true);

        // save new entity in repository
        String existingEntityId = String.valueOf(service.create(requestDTO).getId());

        mockMvc.perform(delete(basePath + "/" + existingEntityId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void unAuthenticatedUser_findAllByStatusId() throws Exception {
        service.create(requestDTO);

        mockMvc.perform(get(basePath + "/findAllByTaskStatusId/1"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void authenticatedUser_findAllByStatusId() throws Exception {
        // check if user has access to the entry
        Mockito.when(helper.isAuthUserEqualsEmployee(Mockito.any())).thenReturn(true);

        service.create(requestDTO);

        mockMvc.perform(get(basePath + "/findAllByTaskStatusId/1"))
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
    @WithMockUser(roles = "MANAGER")
    void authenticatedUser_findAllByCandidateId() throws Exception {
        service.create(requestDTO);

        mockMvc.perform(get(basePath + "/findAllByCandidate/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void authenticatedUser_findAllByVacancyId() throws Exception {
        service.create(requestDTO);

        mockMvc.perform(get(basePath + "/findAllByVacancy/3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void authenticatedUser_findAllByStartDate() throws Exception {
        service.create(requestDTO);

        TaskDateRequestDTO interviewDateRequestDTO = new TaskDateRequestDTO(LocalDate.of(2023, 11, 15));

        mockMvc.perform(put(basePath + "/findAllByStartDate")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(interviewDateRequestDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @WithMockUser
    void authenticatedUser_findAllByStartDateAndEmployeed() throws Exception {
        // check if user has access to the entry
        Mockito.when(helper.isAuthUserEqualsEmployee(Mockito.any())).thenReturn(true);

        service.create(requestDTO);

        InterviewDateRequestDTO interviewDateRequestDTO = new InterviewDateRequestDTO(LocalDate.of(2023, 11, 15));

        mockMvc.perform(put(basePath + "/findAllByStartDateAndEmployee/2")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(interviewDateRequestDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @WithMockUser
    void authenticatedUser_findAllByCandidateAndEmployee() throws Exception {
        // check if user has access to the entry
        Mockito.when(helper.isAuthUserEqualsEmployee(Mockito.any())).thenReturn(true);

        service.create(requestDTO);

        mockMvc.perform(get(basePath + "/findAllByCandidateAndEmployee/1/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @WithMockUser
    void authenticatedUser_findAllByStatusAndEmployee() throws Exception {
        // check if user has access to the entry
        Mockito.when(helper.isAuthUserEqualsEmployee(Mockito.any())).thenReturn(true);

        service.create(requestDTO);

        mockMvc.perform(get(basePath + "/findAllByStatusAndEmployee/1/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @WithMockUser
    void authenticatedUser_findAllByVacancyAndEmployee() throws Exception {
        // check if user has access to the entry
        Mockito.when(helper.isAuthUserEqualsEmployee(Mockito.any())).thenReturn(true);

        service.create(requestDTO);

        mockMvc.perform(get(basePath + "/findAllByVacancyAndEmployee/3/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

}