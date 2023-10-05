package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.Employee;
import crm.myhrcrmproject.domain.Interview;
import crm.myhrcrmproject.domain.enums.InterviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Integer> {
    List<Interview> findByStatus(InterviewStatus status);
    List<Interview> findByEmployee(Employee employee);
    List<Interview> findByDateAndEmployee(LocalDate date, Employee employee);
    List<Interview> findByDate(LocalDate date);
}
