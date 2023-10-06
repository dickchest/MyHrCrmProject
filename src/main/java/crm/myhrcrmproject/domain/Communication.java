package crm.myhrcrmproject.domain;

import crm.myhrcrmproject.domain.enums.CommunicationType;
import crm.myhrcrmproject.domain.enums.ContractType;
import crm.myhrcrmproject.service.validation.NotFoundException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Communication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Communication Date & Time should be not blank")
    private LocalDateTime communicationDateTime = LocalDateTime.now();

    @NotNull(message = "Communication type should be not blank")
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

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public void setCommunicationType(String s) {
        try {
            this.communicationType = CommunicationType.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("Invalid CommunicationType: " + s);
        }
    }
}
