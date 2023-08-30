package crm.myhrcrmproject.dto.contractDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractShortResponseDTO {
    private Integer id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double salary;
}
