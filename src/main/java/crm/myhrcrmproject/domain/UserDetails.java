package crm.myhrcrmproject.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    @NotBlank(message = "User name must be not blank")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Manager name can contain only latin letters and numbers")
    private String userName;
    @NotBlank(message = "Password must be not blank")
    @Pattern(regexp = "^[A-Za-z0-9!@#$%^&*()]+$", message = "Password can contain only latin letters and numbers and some special characters")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
    @OneToOne(cascade = CascadeType.ALL)
    private Employee employee; // таблица user связана с employee один к одному

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
