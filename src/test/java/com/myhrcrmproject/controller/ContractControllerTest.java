package com.myhrcrmproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhrcrmproject.domain.Employee;
import com.myhrcrmproject.domain.enums.ContractType;
import com.myhrcrmproject.dto.contractDTO.ContractRequestDTO;
import com.myhrcrmproject.repository.EmployeeRepository;
import com.myhrcrmproject.service.ContractService;
import com.myhrcrmproject.service.auth.SecurityHelper;
import com.myhrcrmproject.service.validation.NotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class ContractControllerTest {
    private static ContractRequestDTO requestDTO;

    @Value("/api/contracts")
    private String basePath;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecurityHelper helper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ContractService service;

    @Autowired
    private EmployeeRepository employeeRepository;


    @BeforeAll
    public static void setUp() {
        requestDTO = new ContractRequestDTO();
        requestDTO.setStartDate(LocalDate.of(2023, 11, 1));
        requestDTO.setEndDate(LocalDate.of(2024, 10, 31));
        requestDTO.setSalary(2500.00);
        requestDTO.setContractType(ContractType.FULL_TIME);
        requestDTO.setCandidateId(1);
        requestDTO.setClientId(2);
    }

    @BeforeEach
    public void beforeEach() {
        Employee currentEmployee = employeeRepository.findById(1)
                .orElseThrow(() -> new NotFoundException("Compensation with id: 1 not found!"));
        when(helper.getCurrentAuthEmployee()).thenReturn(Optional.of(currentEmployee));
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
                .andExpect(jsonPath("$.candidate.id").value(1));
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
        var requestDTO = new ContractRequestDTO();
        requestDTO.setContractType(ContractType.SEASONAL);

        mockMvc.perform(put(basePath + "/" + existingEntityId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(existingEntityId))
                .andExpect(jsonPath("$.contractType").value("SEASONAL"));
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
    @WithMockUser
    void unAuthorisedUser_deleteCandidate_shouldReturn403() throws Exception {
        // save new Candidate in repository
        String existingEntityId = String.valueOf(service.create(requestDTO).getId());

        mockMvc.perform(delete(basePath + "/" + existingEntityId))
                .andDo(print())
                .andExpect(status().isForbidden());
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
    void authorisedUser_findAllByContractTypeId_shouldReturnStatus200() throws Exception {
        service.create(requestDTO);

        mockMvc.perform(get(basePath + "/findAllByContractTypeId/0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @WithMockUser
    void authorisedUser_findAllByEmployeeId() throws Exception {
        service.create(requestDTO);

        mockMvc.perform(get(basePath + "/findAllByEmployee/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @WithMockUser
    void authorisedUser_findAllByClientId() throws Exception {
        service.create(requestDTO);

        mockMvc.perform(get(basePath + "/findAllByClient/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @WithMockUser
    void authorisedUser_findAllByCandidateId() throws Exception {
        service.create(requestDTO);

        mockMvc.perform(get(basePath + "/findAllByCandidate/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @WithMockUser
    void authorisedUser_findAllActiveContracts() throws Exception {
        LocalDate localDate = LocalDate.now();
        // set up a valid contract
        requestDTO.setStartDate(localDate.minusMonths(1));
        requestDTO.setEndDate(localDate);

        service.create(requestDTO);

        mockMvc.perform(get(basePath + "/findAllByCandidate/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }


}