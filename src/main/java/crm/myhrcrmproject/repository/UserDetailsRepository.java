package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.Employee;
import crm.myhrcrmproject.domain.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
    Optional<Employee> findAllByEmployee(Employee employee);
}
