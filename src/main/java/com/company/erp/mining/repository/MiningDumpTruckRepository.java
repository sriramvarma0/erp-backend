package com.company.erp.mining.repository;

import com.company.erp.mining.entity.MiningDumpTruck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MiningDumpTruckRepository extends JpaRepository<MiningDumpTruck, Long> {
    Optional<MiningDumpTruck> findByAssetId(UUID assetId);

    Optional<MiningDumpTruck> findByChassisNumber(String chassisNumber);

    Optional<MiningDumpTruck> findByEngineNumber(String engineNumber);

    Optional<MiningDumpTruck> findByRegistrationNumber(String registrationNumber);
}
