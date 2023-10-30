package com.myhrcrmproject.service.auth;

import com.myhrcrmproject.domain.Employee;
import com.myhrcrmproject.domain.UserDetails;
import com.myhrcrmproject.repository.UserDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Optional<Employee> getCurrentAuthEmployeeId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = userDetailsRepository.findByUserName(authentication.getName()).orElse(null);

        if (userDetails != null) {
            return Optional.of(userDetails.getEmployee());
        }
        return Optional.empty();
    }

}
