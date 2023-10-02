package crm.myhrcrmproject.dto.addressDetailsDTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDetailsDTO {

    private String address1;

    private String address2;

    private String city;

    private String state;

    private String zip;

    private String country;
}
