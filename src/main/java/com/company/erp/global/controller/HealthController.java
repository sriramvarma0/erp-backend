package com.company.erp.global.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class HealthController {

    private final DataSource dataSource;
    private final com.company.erp.global.repository.UserRepository userRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public HealthController(DataSource dataSource, com.company.erp.global.repository.UserRepository userRepository,
            org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");

        try (Connection conn = dataSource.getConnection()) {
            if (conn.isValid(1)) {
                status.put("database", "CONNECTED");
            } else {
                status.put("database", "UNREACHABLE");
            }

            boolean userExists = userRepository.findByUsername("admin-user").isPresent();
            status.put("admin_user_exists", String.valueOf(userExists));

        } catch (SQLException e) {
            status.put("database", "ERROR: " + e.getMessage());
        }

        return ResponseEntity.ok(status);
    }

    @org.springframework.web.bind.annotation.PostMapping("/reset-demo")
    public ResponseEntity<String> resetDemoUser() {
        var user = userRepository.findByUsername("admin-user");
        if (user.isPresent()) {
            var u = user.get();
            u.setPassword(passwordEncoder.encode("password123"));
            userRepository.save(u);
            return ResponseEntity.ok("Password reset to 'password123'");
        }
        return ResponseEntity.badRequest().body("User not found");
    }
}
