package com.company.erp.mining.repository;

import com.company.erp.mining.entity.MiningOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MiningOperatorRepository extends JpaRepository<MiningOperator, Long> {
    Optional<MiningOperator> findByOperatorId(String operatorId);

    Optional<MiningOperator> findByUserId(Long userId);
}
