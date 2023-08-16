package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepository extends JpaRepository<Clients, Integer> {
}
