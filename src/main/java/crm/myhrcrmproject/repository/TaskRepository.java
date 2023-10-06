package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.Candidate;
import crm.myhrcrmproject.domain.Employee;
import crm.myhrcrmproject.domain.Task;
import crm.myhrcrmproject.domain.Vacancy;
import crm.myhrcrmproject.domain.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByStatus(TaskStatus status);

    List<Task> findByEmployee(Employee employee);

    List<Task> findByCandidate(Candidate candidate);

    List<Task> findByVacancy(Vacancy candidate);
}
