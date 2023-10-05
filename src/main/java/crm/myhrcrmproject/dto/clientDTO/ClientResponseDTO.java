package crm.myhrcrmproject.dto.clientDTO;

import crm.myhrcrmproject.domain.Candidate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientResponseDTO {
    private Integer id;
    private String companyName;
    private String description;
}
