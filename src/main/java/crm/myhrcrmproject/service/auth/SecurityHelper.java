package crm.myhrcrmproject.service.auth;

import crm.myhrcrmproject.domain.Employee;
import crm.myhrcrmproject.domain.UserDetails;
import crm.myhrcrmproject.repository.UserDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityHelper {
    private final UserDetailsRepository userDetailsRepository;

    public boolean isAuthUserEqualsEmployee(Employee employee) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authUserName = authentication.getName();
        UserDetails userDetails = userDetailsRepository.findByUserName(authUserName).orElse(null);

        if (userDetails != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

            boolean isManager = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_MANAGER"));

            return isAdmin || isManager || (userDetails.getEmployee() != null && userDetails.getEmployee().equals(employee));
        }
        return false;
    }

}
