package com.myhrcrmproject.dto.contactDetailsDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Schema(description = "DTO for contact details")
public class ContactDetailsDTO {

    @Schema(description = "Email address", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Home phone number", example = "+1234567890")
    private String homePhone;

    @Schema(description = "Mobile phone number", example = "+9876543210")
    private String mobilePhone;
}