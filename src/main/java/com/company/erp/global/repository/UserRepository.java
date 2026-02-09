package com.company.erp.global.repository;

import com.company.erp.global.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    java.util.List<User> findByCompany(com.company.erp.company.entity.Company company);

    @org.springframework.data.jpa.repository.Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName AND u.id NOT IN (SELECT d.userId FROM Driver d WHERE d.userId IS NOT NULL)")
    java.util.List<User> findUsersWithRoleAndNoDriverProfile(
            @org.springframework.web.bind.annotation.RequestParam("roleName") String roleName);

    @org.springframework.data.jpa.repository.Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName AND u.id NOT IN (SELECT m.userId FROM MiningOperator m WHERE m.userId IS NOT NULL)")
    java.util.List<User> findUsersWithRoleAndNoMiningOperatorProfile(
            @org.springframework.web.bind.annotation.RequestParam("roleName") String roleName);
}
