package crm.myhrcrmproject.dto.contractDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractRequestDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private Double salary;
    private String contractType;
    private Integer candidateId;
    private Integer clientId;
}
