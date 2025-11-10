package com.ruiz.Beauty.Salon.Management.System.service;

import com.ruiz.Beauty.Salon.Management.System.model.Employee;
import com.ruiz.Beauty.Salon.Management.System.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Servicio para cargar detalles de empleados durante la autenticación.
 */
@Service
@Transactional
@Slf4j
public class EmployeeDetailsService implements UserDetailsService {
    private final EmployeeRepository employeeRepository;

    public EmployeeDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Employee not found with email: {}", email);
                    return new UsernameNotFoundException("Employee not found with email: " + email);
                });

        if (!employee.isActive()) {
            log.warn("Attempted login for inactive employee: {}", email);
            throw new DisabledException("Employee account is disabled");
        }

        log.debug("Employee found: {} with role: {}", email, employee.getRol());

        return new EmployeeDetails(employee);
    }
}

/**
 * Implementación de UserDetails para empleados.
 */
class EmployeeDetails implements UserDetails {

    private final Employee employee;

    public EmployeeDetails(Employee employee) {
        this.employee = employee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + employee.getRol()));
    }

    @Override
    public String getPassword() {
        return employee.getPassword();
    }

    @Override
    public String getUsername() {
        return employee.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return employee.isActive();
    }

    public Employee getEmployee() {
        return employee;
    }

}
