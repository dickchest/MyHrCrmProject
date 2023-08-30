package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.Communication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunicationRepository extends JpaRepository<Communication, Integer> {
}
