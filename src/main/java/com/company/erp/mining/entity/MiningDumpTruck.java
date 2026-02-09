package com.company.erp.mining.entity;

import com.company.erp.company.entity.Company;
import com.company.erp.mining.enums.AssetStatus;
import com.company.erp.mining.enums.OwnershipType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "mining_dump_trucks")
public class MiningDumpTruck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID assetId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    // Master Data
    private String manufacturer;
    private String model;
    private String variant;
    @Column(unique = true)
    private String chassisNumber;
    @Column(unique = true)
    private String engineNumber;
    @Column(unique = true)
    private String registrationNumber;

    // Media
    private String photoUrl;

    @Enumerated(EnumType.STRING)
    private OwnershipType ownershipType;

    @Enumerated(EnumType.STRING)
    private AssetStatus assetStatus;

    // Technical Specs
    private Integer engineDisplacementCc;
    private String fuelType;
    private String emissionNorm;
    private Integer maxPowerHp;
    private Integer maxTorqueNm;
    private String axleConfiguration;
    private String transmissionType;

    // Capacity & Load
    private Double gvwKg;
    private Double kerbWeightKg;
    private Double payloadCapacityKg;
    private String bodyType;

    // Dimensions
    private Double overallLengthMm;
    private Double wheelbaseMm;
    private Double groundClearanceMm;
    private Double turningRadiusMm;

    // Tyres
    private Integer numberOfTyres;
    private String frontTyreSize;
    private String rearTyreSize;

    // Fuel
    private Double fuelTankCapacityLtr;
    private Double averageMileageKmpl;

    // Lifecycle & Finance
    private LocalDate purchaseDate;
    private String vendorName;
    private String invoiceNumber;
    private BigDecimal purchasePrice;
    private Double depreciationRate;
    private String insurancePolicyNumber;
    private LocalDate insuranceExpiryDate;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getAssetId() {
        return assetId;
    }

    public void setAssetId(UUID assetId) {
        this.assetId = assetId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public OwnershipType getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(OwnershipType ownershipType) {
        this.ownershipType = ownershipType;
    }

    public AssetStatus getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(AssetStatus assetStatus) {
        this.assetStatus = assetStatus;
    }

    public Integer getEngineDisplacementCc() {
        return engineDisplacementCc;
    }

    public void setEngineDisplacementCc(Integer engineDisplacementCc) {
        this.engineDisplacementCc = engineDisplacementCc;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getEmissionNorm() {
        return emissionNorm;
    }

    public void setEmissionNorm(String emissionNorm) {
        this.emissionNorm = emissionNorm;
    }

    public Integer getMaxPowerHp() {
        return maxPowerHp;
    }

    public void setMaxPowerHp(Integer maxPowerHp) {
        this.maxPowerHp = maxPowerHp;
    }

    public Integer getMaxTorqueNm() {
        return maxTorqueNm;
    }

    public void setMaxTorqueNm(Integer maxTorqueNm) {
        this.maxTorqueNm = maxTorqueNm;
    }

    public String getAxleConfiguration() {
        return axleConfiguration;
    }

    public void setAxleConfiguration(String axleConfiguration) {
        this.axleConfiguration = axleConfiguration;
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    public Double getGvwKg() {
        return gvwKg;
    }

    public void setGvwKg(Double gvwKg) {
        this.gvwKg = gvwKg;
    }

    public Double getKerbWeightKg() {
        return kerbWeightKg;
    }

    public void setKerbWeightKg(Double kerbWeightKg) {
        this.kerbWeightKg = kerbWeightKg;
    }

    public Double getPayloadCapacityKg() {
        return payloadCapacityKg;
    }

    public void setPayloadCapacityKg(Double payloadCapacityKg) {
        this.payloadCapacityKg = payloadCapacityKg;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public Double getOverallLengthMm() {
        return overallLengthMm;
    }

    public void setOverallLengthMm(Double overallLengthMm) {
        this.overallLengthMm = overallLengthMm;
    }

    public Double getWheelbaseMm() {
        return wheelbaseMm;
    }

    public void setWheelbaseMm(Double wheelbaseMm) {
        this.wheelbaseMm = wheelbaseMm;
    }

    public Double getGroundClearanceMm() {
        return groundClearanceMm;
    }

    public void setGroundClearanceMm(Double groundClearanceMm) {
        this.groundClearanceMm = groundClearanceMm;
    }

    public Double getTurningRadiusMm() {
        return turningRadiusMm;
    }

    public void setTurningRadiusMm(Double turningRadiusMm) {
        this.turningRadiusMm = turningRadiusMm;
    }

    public Integer getNumberOfTyres() {
        return numberOfTyres;
    }

    public void setNumberOfTyres(Integer numberOfTyres) {
        this.numberOfTyres = numberOfTyres;
    }

    public String getFrontTyreSize() {
        return frontTyreSize;
    }

    public void setFrontTyreSize(String frontTyreSize) {
        this.frontTyreSize = frontTyreSize;
    }

    public String getRearTyreSize() {
        return rearTyreSize;
    }

    public void setRearTyreSize(String rearTyreSize) {
        this.rearTyreSize = rearTyreSize;
    }

    public Double getFuelTankCapacityLtr() {
        return fuelTankCapacityLtr;
    }

    public void setFuelTankCapacityLtr(Double fuelTankCapacityLtr) {
        this.fuelTankCapacityLtr = fuelTankCapacityLtr;
    }

    public Double getAverageMileageKmpl() {
        return averageMileageKmpl;
    }

    public void setAverageMileageKmpl(Double averageMileageKmpl) {
        this.averageMileageKmpl = averageMileageKmpl;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Double getDepreciationRate() {
        return depreciationRate;
    }

    public void setDepreciationRate(Double depreciationRate) {
        this.depreciationRate = depreciationRate;
    }

    public String getInsurancePolicyNumber() {
        return insurancePolicyNumber;
    }

    public void setInsurancePolicyNumber(String insurancePolicyNumber) {
        this.insurancePolicyNumber = insurancePolicyNumber;
    }

    public LocalDate getInsuranceExpiryDate() {
        return insuranceExpiryDate;
    }

    public void setInsuranceExpiryDate(LocalDate insuranceExpiryDate) {
        this.insuranceExpiryDate = insuranceExpiryDate;
    }

    @PrePersist
    public void generateAssetId() {
        if (assetId == null) {
            assetId = UUID.randomUUID();
        }
    }
}
