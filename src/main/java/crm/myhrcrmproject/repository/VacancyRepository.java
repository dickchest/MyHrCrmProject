package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.Employee;
import crm.myhrcrmproject.domain.Vacancy;
import crm.myhrcrmproject.domain.enums.VacancyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {
    List<Vacancy> findByStatus(VacancyStatus status);
    List<Vacancy> findByEmployeeId(Integer id);
}
