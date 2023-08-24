package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewsRepository extends JpaRepository<Interview, Integer> {
}
