package crm.myhrcrmproject.dto.userDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsRequestDTO {

    private String userName;
    private String password;
    private String email;
}
