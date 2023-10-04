package crm.myhrcrmproject.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Company name must be not blank")
    @Size(min = 1, max = 255, message = "Company name length must be between 1 and 255")
    private String companyName;

    private String description;

    @ManyToMany
    @JoinTable(
            name = "client_candidate_relation",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "candidate_id")
    )
    private List<Candidate> candidateList;
}
