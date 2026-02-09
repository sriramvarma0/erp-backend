package com.company.erp.logistics.repository;

import com.company.erp.logistics.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByDriverId(String driverId);

    Optional<Driver> findByLicenseNumber(String licenseNumber);

    Optional<Driver> findByContactNumber(String contactNumber);

    java.util.List<Driver> findByDepartment(String department);

    Optional<Driver> findByUserId(Long userId);
}
