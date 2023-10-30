package com.myhrcrmproject.dto.contactDetailsDTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ContactDetailsDTO {

    private String email;

    private String homePhone;

    private String mobilePhone;
}
