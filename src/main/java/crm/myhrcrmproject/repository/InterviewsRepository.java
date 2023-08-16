package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.Candidates;
import crm.myhrcrmproject.domain.Interviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewsRepository extends JpaRepository<Interviews, Integer> {
}
