package com.company.erp.global.service;

import com.company.erp.global.entity.User;
import com.company.erp.global.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

        private final UserRepository userRepository;

        @Override
        @Transactional
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                System.out.println("DEBUG: Attempting to load user: " + username);
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> {
                                        System.out.println("DEBUG: User not found: " + username);
                                        return new UsernameNotFoundException("User not found: " + username);
                                });
                System.out.println("DEBUG: Found user: " + user.getUsername() + ", Password: " + user.getPassword()
                                + ", Roles: " + user.getRoles());

                java.util.Set<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                                .flatMap(role -> role.getPermissions().stream())
                                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                                .collect(Collectors.toSet());

                user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));

                return new org.springframework.security.core.userdetails.User(
                                user.getUsername(),
                                user.getPassword(),
                                authorities);
        }
}
