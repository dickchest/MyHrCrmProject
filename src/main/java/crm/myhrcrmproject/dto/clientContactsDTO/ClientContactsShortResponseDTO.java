package crm.myhrcrmproject.dto.clientContactsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientContactsShortResponseDTO {
    private Integer clientContactId;
    private String firstName;
    private String lastName;
    private String position;
}
