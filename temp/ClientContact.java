package crm.myhrcrmproject.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class ClientContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name must be not blank")
    @Size(min = 1, max = 255, message = "Name length must be between 1 and 255")
    private String firstName;

    @NotBlank(message = "Lastname must be not blank")
    @Size(min = 1, max = 255, message = "Lastname length must be between 1 and 255")
    private String lastName;

    @Size(min = 1, max = 255, message = "Position length must be less than 255")
    private String position;

    @Size(min = 1, max = 20, message = "Phone length must be less than 20")
    private String phone;

    @Email
    @Size(min = 1, max = 255, message = "Email length must be less than 255")
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

}
