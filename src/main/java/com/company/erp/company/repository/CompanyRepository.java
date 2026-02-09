package com.company.erp.company.repository;

import com.company.erp.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    java.util.Optional<Company> findByName(String name);
}
