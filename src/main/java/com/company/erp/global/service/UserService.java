package com.company.erp.global.service;

import com.company.erp.global.entity.Role;
import com.company.erp.global.entity.User;
import com.company.erp.global.repository.RoleRepository;
import com.company.erp.global.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;
    private final com.company.erp.company.repository.CompanyRepository companyRepository;
    private final com.company.erp.logistics.repository.DriverRepository driverRepository;
    private final com.company.erp.mining.repository.MiningOperatorRepository miningOperatorRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
            org.springframework.security.crypto.password.PasswordEncoder passwordEncoder,
            com.company.erp.company.repository.CompanyRepository companyRepository,
            com.company.erp.logistics.repository.DriverRepository driverRepository,
            com.company.erp.mining.repository.MiningOperatorRepository miningOperatorRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.companyRepository = companyRepository;
        this.driverRepository = driverRepository;
        this.miningOperatorRepository = miningOperatorRepository;
    }

    @Transactional
    public User save(User user) {
        if (user.getId() == null) {
            // New user, hash password
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            // Existing user, check if password changed (simplified logic: if non-empty and
            // different)
            // For now, if password field is sent, re-hash it. If null/empty, keep existing?
            // Usually update DTO separates this. Here we are using Entity directly.
            // Let's assume if password is provided in update, we hash it.
            // But we need to be careful not to double hash if not changed.
            // For simple CRUD, let's assume update always sends a new password or we'll
            // handle partial update.
            // Better: updateUser method handles logic.
        }
        return userRepository.save(user);
    }

    @Transactional
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getCompany() != null && user.getCompany().getId() == null && user.getCompany().getName() != null) {
            com.company.erp.company.entity.Company existingCompany = companyRepository
                    .findByName(user.getCompany().getName())
                    .orElseThrow(
                            () -> new EntityNotFoundException("Company not found: " + user.getCompany().getName()));
            user.setCompany(existingCompany);
        }
        // Ensure roles are managed
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            java.util.Set<Role> managedRoles = new java.util.HashSet<>();
            for (Role r : user.getRoles()) {
                if (r.getId() != null) {
                    roleRepository.findById(r.getId()).ifPresent(managedRoles::add);
                } else if (r.getName() != null) {
                    roleRepository.findByName(r.getName()).ifPresent(managedRoles::add);
                }
            }
            user.setRoles(managedRoles);
        }
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long id, User userDetails) {
        User user = findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setUsername(userDetails.getUsername());

        if (userDetails.getCompany() != null) {
            if (userDetails.getCompany().getId() != null) {
                user.setCompany(companyRepository.findById(userDetails.getCompany().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Company not found")));
            } else if (userDetails.getCompany().getName() != null) {
                user.setCompany(companyRepository.findByName(userDetails.getCompany().getName())
                        .orElseThrow(() -> new EntityNotFoundException("Company not found")));
            }
        }

        user.setEnabled(userDetails.isEnabled());

        // Update roles
        if (userDetails.getRoles() != null) {
            java.util.Set<Role> managedRoles = new java.util.HashSet<>();
            for (Role r : userDetails.getRoles()) {
                if (r.getId() != null) {
                    roleRepository.findById(r.getId()).ifPresent(managedRoles::add);
                } else if (r.getName() != null) {
                    roleRepository.findByName(r.getName()).ifPresent(managedRoles::add);
                }
            }
            user.setRoles(managedRoles);
        }

        // Only update password if provided and not empty
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        // Cascade DELETE: Check and delete linked profiles
        driverRepository.findByUserId(id).ifPresent(driverRepository::delete);
        miningOperatorRepository.findByUserId(id).ifPresent(miningOperatorRepository::delete);

        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<User> findAllByCompany(com.company.erp.company.entity.Company company) {
        return userRepository.findByCompany(company);
    }

    @Transactional
    public void assignRoleToUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleId));
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> getUnlinkedDrivers() {
        return userRepository.findUsersWithRoleAndNoDriverProfile("DRIVER");
    }

    @Transactional(readOnly = true)
    public List<User> getUnlinkedUsersByRole(String roleName) {
        return userRepository.findUsersWithRoleAndNoDriverProfile(roleName);
    }

    @Transactional(readOnly = true)
    public List<User> getUnlinkedMiningOperators() {
        return userRepository.findUsersWithRoleAndNoMiningOperatorProfile("OPERATOR");
    }
}
