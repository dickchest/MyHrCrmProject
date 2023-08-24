package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.Communication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunicationsRepository extends JpaRepository<Communication, Integer> {
}
