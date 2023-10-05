package crm.myhrcrmproject.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    @NotBlank(message = "User name must be not blank")
    @Length(min = 3, max = 80, message = "User name should be between 3 and 80 symbols")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "User name could contain only latin letters and numbers")
    private String userName;

    @NotBlank(message = "Password must be not blank")
    @Length(min = 6, message = "Password should be more then 6 symbols")
    @Pattern(regexp = "^[A-Za-z0-9!@#$%^&*()]+$", message = "Password could contain only latin letters and numbers and some special characters")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    private Employee employee; // таблица user связана с employee один к одному

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
