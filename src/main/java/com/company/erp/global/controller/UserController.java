package com.company.erp.global.controller;

import com.company.erp.global.entity.User;
import com.company.erp.global.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final com.company.erp.company.repository.CompanyRepository companyRepository;
    private final com.company.erp.global.repository.UserRepository userRepository;

    public UserController(UserService userService,
            com.company.erp.company.repository.CompanyRepository companyRepository,
            com.company.erp.global.repository.UserRepository userRepository) {
        this.userService = userService;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'GLOBAL', 'CHIEF')")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String company) {
        String username = org.springframework.security.core.context.SecurityContextHolder.getContext()
                .getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean isGlobal = currentUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals("GLOBAL") || role.getName().equals("CHIEF"));

        if (isGlobal) {
            if (company != null && !company.isEmpty()) {
                com.company.erp.company.entity.Company companyEntity = companyRepository.findByName(company)
                        .orElse(null);
                if (companyEntity == null) {
                    return ResponseEntity.ok(java.util.Collections.emptyList());
                }
                return ResponseEntity.ok(userService.findAllByCompany(companyEntity));
            }
            return ResponseEntity.ok(userService.findAll());
        } else {
            // Non-Global users can ONLY see users from their own company
            if (currentUser.getCompany() == null) {
                return ResponseEntity.ok(java.util.Collections.emptyList());
            }
            return ResponseEntity.ok(userService.findAllByCompany(currentUser.getCompany()));
        }
    }

    @PostMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'GLOBAL', 'CHIEF')")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            String username = org.springframework.security.core.context.SecurityContextHolder.getContext()
                    .getAuthentication().getName();
            User currentUser = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Current user not found"));

            // Check for duplicate username
            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().body("Username '" + user.getUsername() + "' already exists.");
            }

            boolean isGlobal = currentUser.getRoles().stream()
                    .anyMatch(role -> role.getName().equals("GLOBAL") || role.getName().equals("CHIEF"));

            // 1. Tenancy Enforcement for Creation
            if (!isGlobal) {
                // Force the company to be the creator's company
                if (currentUser.getCompany() == null) {
                    return ResponseEntity.status(403)
                            .body("Subsidiary admin must belong to a company to create users.");
                }
                user.setCompany(currentUser.getCompany());
            }

            // 2. Role Restriction
            if (!isGlobal && user.getRoles() != null) {
                boolean tryingToAssignElegatedRole = user.getRoles().stream()
                        .anyMatch(r -> r.getName().equals("GLOBAL") || r.getName().equals("CHIEF"));
                if (tryingToAssignElegatedRole) {
                    return ResponseEntity.status(403).body("Insufficient privileges to assign GLOBAL/CHIEF roles.");
                }
            }

            User saved = userService.createUser(user);
            return ResponseEntity.created(URI.create("/api/users/" + saved.getId()))
                    .body(saved);
        } catch (Exception e) {
            e.printStackTrace(); // Log it to server console too
            return ResponseEntity.status(500).body("Error creating user: " + e.getMessage());
        }
    }

    @PostMapping("/{userId}/roles/{roleId}")
    @org.springframework.security.access.prepost.PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'GLOBAL', 'CHIEF')")
    public ResponseEntity<Void> assignRoleToUser(@PathVariable Long userId,
            @PathVariable Long roleId) {
        // Simple endpoint, but we should probably secure it too.
        // For now relying on main update flow or assuming this is less used.
        userService.assignRoleToUser(userId, roleId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'GLOBAL', 'CHIEF')")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        String username = org.springframework.security.core.context.SecurityContextHolder.getContext()
                .getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean isGlobal = currentUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals("GLOBAL") || role.getName().equals("CHIEF"));

        User existingUser = userService.findById(id).orElse(null);
        if (existingUser == null)
            return ResponseEntity.notFound().build();

        // 1. Tenancy Enforcement
        if (!isGlobal) {
            // Cannot update user from another company
            if (existingUser.getCompany() == null
                    || !existingUser.getCompany().getId().equals(currentUser.getCompany().getId())) {
                return ResponseEntity.status(403).build();
            }
            // Cannot change company
            userDetails.setCompany(currentUser.getCompany());
        }

        // 2. Role Restriction
        if (!isGlobal && userDetails.getRoles() != null) {
            boolean tryingToAssignElegatedRole = userDetails.getRoles().stream()
                    .anyMatch(r -> r.getName().equals("GLOBAL") || r.getName().equals("CHIEF"));
            if (tryingToAssignElegatedRole) {
                return ResponseEntity.status(403).body("Insufficient privileges to assign GLOBAL/CHIEF roles.");
            }
        }

        return ResponseEntity.ok(userService.updateUser(id, userDetails));
    }

    @DeleteMapping("/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'GLOBAL', 'CHIEF')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        String username = org.springframework.security.core.context.SecurityContextHolder.getContext()
                .getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean isGlobal = currentUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals("GLOBAL") || role.getName().equals("CHIEF"));

        User existingUser = userService.findById(id).orElse(null);
        if (existingUser != null) {
            if (!isGlobal) {
                // Cannot delete user from another company
                if (existingUser.getCompany() == null
                        || !existingUser.getCompany().getId().equals(currentUser.getCompany().getId())) {
                    return ResponseEntity.status(403).build();
                }
            }
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
