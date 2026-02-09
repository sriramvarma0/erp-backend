package com.company.erp.mining.repository;

import com.company.erp.mining.entity.MiningVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiningVehicleRepository extends JpaRepository<MiningVehicle, Long> {
}
