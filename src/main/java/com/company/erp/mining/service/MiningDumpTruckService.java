package com.company.erp.mining.service;

import com.company.erp.company.repository.CompanyRepository;
import com.company.erp.mining.entity.MiningDumpTruck;
import com.company.erp.mining.repository.MiningDumpTruckRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class MiningDumpTruckService {

    private final MiningDumpTruckRepository repository;
    private final CompanyRepository companyRepository;

    public MiningDumpTruckService(MiningDumpTruckRepository repository, CompanyRepository companyRepository) {
        this.repository = repository;
        this.companyRepository = companyRepository;
    }

    @Transactional(readOnly = true)
    public List<MiningDumpTruck> getAllTrucks() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public MiningDumpTruck getTruckById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Truck not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public MiningDumpTruck getTruckByAssetId(UUID assetId) {
        return repository.findByAssetId(assetId)
                .orElseThrow(() -> new EntityNotFoundException("Truck not found with assetId: " + assetId));
    }

    @Transactional
    public MiningDumpTruck createTruck(MiningDumpTruck truck) {
        // Ensure Company is Mining
        if (truck.getCompany() == null) {
            companyRepository.findByName("Mining").ifPresent(truck::setCompany);
        }
        if (truck.getCompany() == null) {
            throw new RuntimeException("Mining company not configured in system");
        }

        return repository.save(truck);
    }

    @Transactional
    public MiningDumpTruck updateTruck(Long id, MiningDumpTruck details) {
        MiningDumpTruck truck = getTruckById(id);

        // Update fields - simplified (should use proper mapping in prod)
        truck.setManufacturer(details.getManufacturer());
        truck.setModel(details.getModel());
        truck.setVariant(details.getVariant());
        truck.setChassisNumber(details.getChassisNumber());
        truck.setEngineNumber(details.getEngineNumber());
        truck.setRegistrationNumber(details.getRegistrationNumber());
        truck.setOwnershipType(details.getOwnershipType());
        truck.setAssetStatus(details.getAssetStatus());
        truck.setEngineDisplacementCc(details.getEngineDisplacementCc());
        truck.setFuelType(details.getFuelType());
        truck.setEmissionNorm(details.getEmissionNorm());
        truck.setMaxPowerHp(details.getMaxPowerHp());
        truck.setMaxTorqueNm(details.getMaxTorqueNm());
        truck.setAxleConfiguration(details.getAxleConfiguration());
        truck.setTransmissionType(details.getTransmissionType());
        truck.setGvwKg(details.getGvwKg());
        truck.setKerbWeightKg(details.getKerbWeightKg());
        truck.setPayloadCapacityKg(details.getPayloadCapacityKg());
        truck.setBodyType(details.getBodyType());
        truck.setOverallLengthMm(details.getOverallLengthMm());
        truck.setWheelbaseMm(details.getWheelbaseMm());
        truck.setGroundClearanceMm(details.getGroundClearanceMm());
        truck.setTurningRadiusMm(details.getTurningRadiusMm());
        truck.setNumberOfTyres(details.getNumberOfTyres());
        truck.setFrontTyreSize(details.getFrontTyreSize());
        truck.setRearTyreSize(details.getRearTyreSize());
        truck.setFuelTankCapacityLtr(details.getFuelTankCapacityLtr());
        truck.setAverageMileageKmpl(details.getAverageMileageKmpl());
        truck.setPurchaseDate(details.getPurchaseDate());
        truck.setVendorName(details.getVendorName());
        truck.setInvoiceNumber(details.getInvoiceNumber());
        truck.setPurchasePrice(details.getPurchasePrice());
        truck.setDepreciationRate(details.getDepreciationRate());
        truck.setInsurancePolicyNumber(details.getInsurancePolicyNumber());
        truck.setInsuranceExpiryDate(details.getInsuranceExpiryDate());

        return repository.save(truck);
    }

    @Transactional
    public void deleteTruck(Long id) {
        repository.deleteById(id);
    }
}
