package crm.myhrcrmproject.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name must be not blank")
    @Size(min = 3, max = 20, message = "Name length must be between 3 and 15")
    private String firstName;

    @NotBlank(message = "Lastname must be not blank")
    @Size(min = 1, max = 50, message = "Lastname length must be between 1 and 50")
    private String lastName;

    private String position;

    private String phone;

    @NotBlank(message = "Email must be not blank")
    @Email
    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Vacancy> vacancyList;
}
