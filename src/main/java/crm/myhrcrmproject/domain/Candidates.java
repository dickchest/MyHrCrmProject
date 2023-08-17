package crm.myhrcrmproject.domain;

import crm.myhrcrmproject.domain.enums.CandidateStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@Entity
public class Candidates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name must be not blank")
    @Column(name = "FirstName")
    @Size(min = 3, max = 20, message = "Name length must be between 3 and 15")
    private String firstName;

    @NotBlank(message = "Lastname must be not blank")
    @Column(name = "LastName")
    @Size(min = 1, max = 50, message = "Lastname length must be between 1 and 50")
    private String lastName;

    @NotBlank(message = "Birthdate must be not blank")
    private LocalDate dateOfBirth;

    private String address;

    private String phone;

    @Email
    private String email;

    @OneToOne
    @JoinColumn(name = "vacancy_ID")
    private Vacancies vacancy;

    private LocalDateTime interviewDate;

    private String interviewResult;

    private String comments;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CandidateStatus candidateStatus;

    private LocalDateTime creatingDate;

    private LocalDateTime updatedDate;
}
