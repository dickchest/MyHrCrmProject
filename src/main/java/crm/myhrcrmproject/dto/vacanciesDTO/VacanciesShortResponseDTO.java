package crm.myhrcrmproject.dto.vacanciesDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VacanciesShortResponseDTO {
    private Integer id;
    private String jobTitle;
    private Double salary;
}
