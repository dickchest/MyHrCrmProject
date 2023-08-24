package crm.myhrcrmproject.dto.vacanciesDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacanciesShortResponseDTO {
    private Integer vacancyId;
    private String jobTitle;
    private Double salary;
}
