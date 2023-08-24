package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role, Integer> {
}
