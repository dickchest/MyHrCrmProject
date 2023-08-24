package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacanciesRepository extends JpaRepository<Vacancy, Integer> {
}
