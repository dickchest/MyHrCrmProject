package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.Candidates;
import crm.myhrcrmproject.domain.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Integer> {
}
