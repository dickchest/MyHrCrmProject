package crm.myhrcrmproject.dto.clientContactDTO;

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
