package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactsRepository extends JpaRepository<Contacts, Integer> {
}
