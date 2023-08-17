package crm.myhrcrmproject.dto.clientContactsDTO;

import crm.myhrcrmproject.dto.clientsDTO.ClientsResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientContactsShortResponseDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String position;
}
