package com.company.erp.global.util;

import com.company.erp.company.entity.Company;
import com.company.erp.company.repository.CompanyRepository;
import com.company.erp.global.entity.Role;
import com.company.erp.global.entity.User;
import com.company.erp.global.repository.RoleRepository;
import com.company.erp.global.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CompanyRepository companyRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(CompanyRepository companyRepository, RoleRepository roleRepository, UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.companyRepository = companyRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        seedCompanies();
        seedRoles();
        seedUsers();
    }

    private void seedCompanies() {
        createCompanyIfNotFound("SR Group", "Parent Company - Global Access");
        createCompanyIfNotFound("Logistics", "Subsidiary - Logistics Operations");
        createCompanyIfNotFound("Constructions", "Subsidiary - Construction Projects"); // Standardized to Plural
        createCompanyIfNotFound("Mining", "Subsidiary - Mining Operations");
    }

    private void createCompanyIfNotFound(String name, String description) {
        if (companyRepository.findByName(name).isEmpty()) {
            Company company = new Company(name, description);
            companyRepository.save(company);
        }
    }

    private void seedRoles() {
        createRoleIfNotFound("ADMIN", "Administrator with full access");
        createRoleIfNotFound("CHIEF", "Chief - Same access as Admin");
        createRoleIfNotFound("MANAGER", "Manager with restricted access");
        createRoleIfNotFound("SUPERVISOR", "Supervisor - Team Lead");
        createRoleIfNotFound("DRIVER", "Driver - Logistics only");
        createRoleIfNotFound("USER", "Standard user");
        createRoleIfNotFound("GLOBAL", "Global access role");
    }

    private void createRoleIfNotFound(String name, String description) {
        if (roleRepository.findByName(name).isEmpty()) {
            Role role = new Role(name, description);
            roleRepository.save(role);
        }
    }

    private void seedUsers() {
        // Global Admin (SR Group) - COMPLIANCE: Has GLOBAL role
        createAdmin("admin_sr", "password123", "SR Group", "GLOBAL", "ADMIN");

        // Subsidiary Admins - COMPLIANCE: NO Global role, restricted to Company
        createAdmin("admin_logistics", "password123", "Logistics", "ADMIN");
        createAdmin("admin_constructions", "password123", "Constructions", "ADMIN"); // Updated username and company
        createAdmin("admin_mining", "password123", "Mining", "ADMIN");

        // Chiefs (Same as Admin)
        createAdmin("chief_sr", "password123", "SR Group", "GLOBAL", "CHIEF");
        createAdmin("chief_logistics", "password123", "Logistics", "CHIEF");

        // Managers
        createAdmin("manager_sr", "password123", "SR Group", "GLOBAL", "MANAGER");
        createAdmin("manager_logistics", "password123", "Logistics", "MANAGER");

        // Supervisors
        createAdmin("supervisor_sr", "password123", "SR Group", "GLOBAL", "SUPERVISOR");
        createAdmin("supervisor_logistics", "password123", "Logistics", "SUPERVISOR");

        // Drivers
        createAdmin("driver_1", "password123", "Logistics", "DRIVER");
    }

    private void createAdmin(String username, String password, String companyName, String... roles) {
        if (userRepository.findByUsername(username).isEmpty()) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setEnabled(true);

            Company company = companyRepository.findByName(companyName)
                    .orElseThrow(() -> new RuntimeException("Company invalid: " + companyName));
            user.setCompany(company);

            java.util.Set<Role> userRoles = new java.util.HashSet<>();
            for (String roleName : roles) {
                roleRepository.findByName(roleName).ifPresent(userRoles::add);
            }
            user.setRoles(userRoles);

            userRepository.save(user);
        }
    }
}
