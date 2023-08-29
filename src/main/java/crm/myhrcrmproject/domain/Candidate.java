package crm.myhrcrmproject.domain;

import crm.myhrcrmproject.domain.enums.CandidateStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name must be not blank")
    @Size(min = 3, max = 20, message = "Name length must be between 3 and 15")
    private String firstName;

    @NotBlank(message = "Lastname must be not blank")
    @Size(min = 1, max = 50, message = "Lastname length must be between 1 and 50")
    private String lastName;

    private LocalDate dateOfBirth;

    private String address;

    private String phone;

    @NotBlank(message = "Email must be not blank")
    @Email
    @Column(unique = true)
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    private List<Interview> interviewList;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CandidateStatus status;

    private LocalDateTime creatingDate;

    private LocalDateTime updatedDate;

}
