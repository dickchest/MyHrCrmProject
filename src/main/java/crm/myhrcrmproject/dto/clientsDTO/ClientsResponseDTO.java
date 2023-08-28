package crm.myhrcrmproject.dto.clientsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientsResponseDTO {
    private Integer id;
    private String companyName;
    private String description;
}
