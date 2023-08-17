package crm.myhrcrmproject.dto.clientsDTO;

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
