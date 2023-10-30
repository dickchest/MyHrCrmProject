package com.myhrcrmproject.dto.addressDetailsDTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDetailsDTO {

    private String street;

    private String city;

    private String state;

    private String zip;

    private String country;
}
