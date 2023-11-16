package com.myhrcrmproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhrcrmproject.dto.contactDetailsDTO.ContactDetailsDTO;
import com.myhrcrmproject.dto.employeeDTO.EmployeeRequestDTO;
import com.myhrcrmproject.service.EmployeeService;
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
class EmployeeControllerIntegrationTest {

    static EmployeeRequestDTO requestDTO;

    @Value("/api/employees")
    private String basePath;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeService service;

    @MockBean
    private SecurityHelper helper;

    @BeforeAll
    public static void setUp() {
        requestDTO = new EmployeeRequestDTO();
        requestDTO.setFirstName("Alex");
        requestDTO.setLastName("Smith");
        requestDTO.setPosition("Software Engineer");

        // Инициализация вложенного объекта ContactDetailsDTO
        ContactDetailsDTO contactDetails = new ContactDetailsDTO();
        contactDetails.setEmail("alex.smith.example@example.com");
        contactDetails.setMobilePhone("+123456789");

        requestDTO.setContactDetails(contactDetails);

    }

    @Test
    void notAuthorisedUser_findAll_shouldReturnStatus403() throws Exception {
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
                .andExpect(jsonPath("$.firstName").value("Alex"));
    }

    @Test
    @WithMockUser
    void authenticatedUser_findById_shouldReturnStatus404() throws Exception {
        mockMvc.perform(get(basePath + "/0"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
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
    void unAuthenticatedUser_createNew_shouldReturnStatus403() throws Exception {
        mockMvc.perform(post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void unAuthenticatedUser_update_shouldReturnStatus403() throws Exception {
        // save new Candidate in repository
        String existingEntityId = String.valueOf(service.create(requestDTO).getId());

        // create request for update
        var requestDTO = new EmployeeRequestDTO();
        requestDTO.setFirstName("John");

        mockMvc.perform(put(basePath + "/" + existingEntityId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void authenticatedUser_update_shouldReturnStatus403() throws Exception {
        // check if user has access to the entry
        Mockito.when(helper.isAuthUserEqualsEmployee(Mockito.any())).thenReturn(true);

        // save new Candidate in repository
        String existingEntityId = String.valueOf(service.create(requestDTO).getId());
        // create request for update
        var requestDTO = new EmployeeRequestDTO();
        requestDTO.setFirstName("John");

        mockMvc.perform(put(basePath + "/" + existingEntityId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isAccepted());
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
    @WithMockUser(roles = "ADMIN")
    void authenticatedUser_deleteCandidate_shouldReturn204() throws Exception {
        // save new Candidate in repository
        String existingEntityId = String.valueOf(service.create(requestDTO).getId());

        mockMvc.perform(delete(basePath + "/" + existingEntityId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}