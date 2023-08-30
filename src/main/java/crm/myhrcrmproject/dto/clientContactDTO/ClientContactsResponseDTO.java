package crm.myhrcrmproject.dto.clientContactDTO;

import crm.myhrcrmproject.dto.clientDTO.ClientsResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientContactsResponseDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String position;
    private String phone;
    private String email;
    private ClientsResponseDTO client;
}
