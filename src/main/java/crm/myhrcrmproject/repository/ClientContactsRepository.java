package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.ClientContacts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientContactsRepository extends JpaRepository<ClientContacts, Integer> {
}
