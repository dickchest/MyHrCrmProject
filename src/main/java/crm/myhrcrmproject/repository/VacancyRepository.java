package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.Employee;
import crm.myhrcrmproject.domain.Vacancy;
import crm.myhrcrmproject.domain.enums.VacancyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {
    Optional<List<Vacancy>> findByStatus(VacancyStatus status);
    Optional<List<Vacancy>> findByEmployee(Employee employee);
}
