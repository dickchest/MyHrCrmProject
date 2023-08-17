package crm.myhrcrmproject.dto.clientContactsDTO;

import crm.myhrcrmproject.domain.Candidates;
import crm.myhrcrmproject.domain.Clients;
import crm.myhrcrmproject.dto.candidatesDTO.CandidatesForInterviewsResponseDTO;
import crm.myhrcrmproject.dto.clientsDTO.ClientsResponseDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientContactsResponseDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String position;
    private String phone;
    private String email;
    private ClientsResponseDTO client;
}
