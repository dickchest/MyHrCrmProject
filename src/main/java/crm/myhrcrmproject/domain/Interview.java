package crm.myhrcrmproject.domain;

import crm.myhrcrmproject.domain.enums.InterviewStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Data blank")
    private LocalDate date;

    @NotBlank(message = "Time must be not blank")
    private LocalTime time;

    @NotBlank(message = "Location must not be blank")
    @Size(min = 1, max = 255, message = "Location's length must be between 1 and 255")
    private String location;

    private String comments;

    @NotBlank(message = "Candidate should not be blank")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @OneToOne
    @JoinColumn(name = "employee_ID")
    private Employee employee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InterviewStatus status;
}
