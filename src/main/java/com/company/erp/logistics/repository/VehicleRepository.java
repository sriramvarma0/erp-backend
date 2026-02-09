package com.company.erp.logistics.repository;

import com.company.erp.logistics.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByVehicleId(String vehicleId);

    Optional<Vehicle> findByRegistrationNumber(String registrationNumber);

    java.util.List<Vehicle> findAllByCompany(com.company.erp.company.entity.Company company);
}
