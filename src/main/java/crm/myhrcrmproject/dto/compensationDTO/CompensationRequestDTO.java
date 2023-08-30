package crm.myhrcrmproject.dto.compensationDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompensationRequestDTO {
    private Double salary;
    private LocalDate paymentDate;
    private String comments;
    private Integer candidateId;
    private Integer contractId;
}
