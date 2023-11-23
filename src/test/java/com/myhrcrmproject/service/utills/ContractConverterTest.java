package com.myhrcrmproject.service.utills;

import com.myhrcrmproject.domain.Contract;
import com.myhrcrmproject.dto.contractDTO.ContractResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ContractConverterTest {
    @InjectMocks
    private ContractConverter converter;

    @Test
    void testToDto_noCandidateEmployeeClient() {
        Contract contract = new Contract();
        contract.setId(1);
        contract.setSalary(5000.00);

        ContractResponseDTO responseDTO = converter.toDTO(contract);

        assertNotNull(responseDTO);
    }
}