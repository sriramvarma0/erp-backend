package com.company.erp.global.controller;

import com.company.erp.global.util.JwtUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private final com.company.erp.global.repository.UserRepository userRepository;

    @PostMapping("/login")
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        System.out.println("DEBUG: Login attempt for: " + authRequest.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            System.out.println("DEBUG: Authentication successful for: " + authRequest.getUsername());

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtUtil.generateToken(userDetails);

            com.company.erp.global.entity.User user = userRepository.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found in DB but loaded by UserDetailsService"));

            return ResponseEntity.ok(new AuthResponse(
                    jwt,
                    userDetails.getUsername(),
                    user.getCompany() != null ? user.getCompany().getName() : null,
                    user.getRoles().stream().map(com.company.erp.global.entity.Role::getName)
                            .collect(java.util.stream.Collectors.toList())));
        } catch (org.springframework.security.core.AuthenticationException e) {
            System.out.println("DEBUG: Auth failed: " + e.getMessage());
            return ResponseEntity.status(401).body("Authentication Failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("DEBUG: Unknown error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Error: " + e.getMessage());
        }
    }

    @Data
    public static class AuthRequest {
        private String username;
        private String password;
    }

    @Data
    public static class AuthResponse {
        private String token;
        private String username;
        private String company;
        private java.util.List<String> roles;

        public AuthResponse(String token, String username, String company, java.util.List<String> roles) {
            this.token = token;
            this.username = username;
            this.company = company;
            this.roles = roles;
        }
    }
}
