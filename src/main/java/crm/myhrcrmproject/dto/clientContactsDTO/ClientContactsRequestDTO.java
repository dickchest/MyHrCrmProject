package crm.myhrcrmproject.dto.clientContactsDTO;

import crm.myhrcrmproject.dto.clientsDTO.ClientsResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientContactsRequestDTO {
    private String firstName;
    private String lastName;
    private String position;
    private String phone;
    private String email;
    private Integer clientId;
}
