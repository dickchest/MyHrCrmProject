package crm.myhrcrmproject.domain;

import crm.myhrcrmproject.domain.enums.CandidateStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer candidateId;

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

    @ManyToOne(fetch = FetchType.EAGER)  // todo сделать many to one (множество кандидатов могут аплаиться на одну вакансию)
    @JoinColumn(name = "vacancy_ID")
    private Vacancy vacancy;

    private LocalDateTime interviewDate;

    private String interviewResult;

    private String comments;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CandidateStatus candidateStatus;

    private LocalDateTime creatingDate;

    private LocalDateTime updatedDate;

}
