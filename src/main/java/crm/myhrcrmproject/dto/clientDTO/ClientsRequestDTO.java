package crm.myhrcrmproject.dto.clientDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientsRequestDTO {
    private String companyName;
    private String description;
}
