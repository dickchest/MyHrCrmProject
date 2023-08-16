package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.Vacancies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacanciesRepository extends JpaRepository<Vacancies, Integer> {
}
