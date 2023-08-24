package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.ClientContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientContactsRepository extends JpaRepository<ClientContact, Integer> {
}
