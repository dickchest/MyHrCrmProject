package crm.myhrcrmproject.domain;

import crm.myhrcrmproject.domain.enums.CommunicationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Communication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Communication Date & Time should be not blank")
    private LocalDateTime communicationDateTime;

    @NotBlank(message = "Communication type should be not blank")
    private CommunicationType communicationType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "responsible_employee_id")
    private Employee employee;
}
