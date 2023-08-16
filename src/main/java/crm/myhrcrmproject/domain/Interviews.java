package crm.myhrcrmproject.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Interviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Data & time must be not blank")
    private LocalDateTime dateTime;

    @NotBlank(message = "Location must not be blank")
    @Size(min = 1, max = 255, message = "Location's length must be between 1 and 255")
    private String location;

    private String comments;

    @NotBlank
    @OneToOne
    @JoinColumn(name = "candidate_ID")
    private Candidates candidate;

    @NotBlank
    @OneToOne
    @JoinColumn(name = "employee_ID")
    private Employees employees;
}
