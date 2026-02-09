package com.company.erp.logistics.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Identification ---
    @Column(name = "vehicle_id", unique = true, nullable = false)
    private String vehicleId; // System generated or manually assigned unique ID

    @Column(name = "registration_number", unique = true, nullable = false)
    private String registrationNumber;

    @Column(name = "fleet_number")
    private String fleetNumber;

    // --- Manufacturer Details ---
    private String make;
    private String model;

    @Column(name = "manufacturing_year")
    private Integer manufacturingYear;

    // --- Power & Powertrain ---
    @Column(name = "powertrain_type")
    private String powertrainType; // Diesel, CNG, Electric, etc.

    @Column(name = "engine_power_hp")
    private Double enginePowerHp;

    @Column(name = "engine_capacity_cc")
    private Integer engineCapacityCc;

    @Column(name = "transmission_type")
    private String transmissionType; // Manual, Automatic

    @Column(name = "emission_norm")
    private String emissionNorm; // BS-IV, BS-VI

    // --- Physical Configuration ---
    @Column(name = "number_of_wheels")
    private Integer numberOfWheels;

    @Column(name = "axle_configuration")
    private String axleConfiguration; // 4x2, 6x4

    @Column(name = "body_type")
    private String bodyType; // Open, Container, Tipper

    @Column(name = "trailer_attached")
    private Boolean trailerAttached;

    // --- Load & Capacity ---
    @Column(name = "payload_capacity_tons")
    private Double payloadCapacityTons;

    @Column(name = "gross_vehicle_weight")
    private Double grossVehicleWeight;

    @Column(name = "load_volume_cbm")
    private Double loadVolumeCbm;

    // --- Operational & Compliance ---
    @Column(name = "insurance_expiry")
    private LocalDate insuranceExpiry;

    @Column(name = "fitness_expiry")
    private LocalDate fitnessExpiry;

    @Column(name = "permit_type")
    private String permitType; // State, National

    @Column(name = "puc_expiry")
    private LocalDate pucExpiry;

    // --- Tracking & Operations ---
    @Column(name = "gps_device_id")
    private String gpsDeviceId;

    @Column(name = "odometer_reading_km")
    private Double odometerReadingKm;

    @Column(name = "average_mileage")
    private Double averageMileage;

    // --- Ownership & Status ---
    @Column(name = "ownership_type")
    private String ownershipType; // Owned, Leased

    private String status; // Active, Maintenance, Retired

    @Column(name = "assigned_depot")
    private String assignedDepot;

    // --- Maintenance ---
    @Column(name = "last_service_date")
    private LocalDate lastServiceDate;

    @Column(name = "service_interval_km")
    private Integer serviceIntervalKm;

    @Column(name = "annual_maintenance_cost")
    private BigDecimal annualMaintenanceCost;

    @Column(name = "fuel_cost_per_km")
    private BigDecimal fuelCostPerKm;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getFleetNumber() {
        return fleetNumber;
    }

    public void setFleetNumber(String fleetNumber) {
        this.fleetNumber = fleetNumber;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getManufacturingYear() {
        return manufacturingYear;
    }

    public void setManufacturingYear(Integer manufacturingYear) {
        this.manufacturingYear = manufacturingYear;
    }

    public String getPowertrainType() {
        return powertrainType;
    }

    public void setPowertrainType(String powertrainType) {
        this.powertrainType = powertrainType;
    }

    public Double getEnginePowerHp() {
        return enginePowerHp;
    }

    public void setEnginePowerHp(Double enginePowerHp) {
        this.enginePowerHp = enginePowerHp;
    }

    public Integer getEngineCapacityCc() {
        return engineCapacityCc;
    }

    public void setEngineCapacityCc(Integer engineCapacityCc) {
        this.engineCapacityCc = engineCapacityCc;
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    public String getEmissionNorm() {
        return emissionNorm;
    }

    public void setEmissionNorm(String emissionNorm) {
        this.emissionNorm = emissionNorm;
    }

    public Integer getNumberOfWheels() {
        return numberOfWheels;
    }

    public void setNumberOfWheels(Integer numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
    }

    public String getAxleConfiguration() {
        return axleConfiguration;
    }

    public void setAxleConfiguration(String axleConfiguration) {
        this.axleConfiguration = axleConfiguration;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public Boolean getTrailerAttached() {
        return trailerAttached;
    }

    public void setTrailerAttached(Boolean trailerAttached) {
        this.trailerAttached = trailerAttached;
    }

    public Double getPayloadCapacityTons() {
        return payloadCapacityTons;
    }

    public void setPayloadCapacityTons(Double payloadCapacityTons) {
        this.payloadCapacityTons = payloadCapacityTons;
    }

    public Double getGrossVehicleWeight() {
        return grossVehicleWeight;
    }

    public void setGrossVehicleWeight(Double grossVehicleWeight) {
        this.grossVehicleWeight = grossVehicleWeight;
    }

    public Double getLoadVolumeCbm() {
        return loadVolumeCbm;
    }

    public void setLoadVolumeCbm(Double loadVolumeCbm) {
        this.loadVolumeCbm = loadVolumeCbm;
    }

    public LocalDate getInsuranceExpiry() {
        return insuranceExpiry;
    }

    public void setInsuranceExpiry(LocalDate insuranceExpiry) {
        this.insuranceExpiry = insuranceExpiry;
    }

    public LocalDate getFitnessExpiry() {
        return fitnessExpiry;
    }

    public void setFitnessExpiry(LocalDate fitnessExpiry) {
        this.fitnessExpiry = fitnessExpiry;
    }

    public String getPermitType() {
        return permitType;
    }

    public void setPermitType(String permitType) {
        this.permitType = permitType;
    }

    public LocalDate getPucExpiry() {
        return pucExpiry;
    }

    public void setPucExpiry(LocalDate pucExpiry) {
        this.pucExpiry = pucExpiry;
    }

    public String getGpsDeviceId() {
        return gpsDeviceId;
    }

    public void setGpsDeviceId(String gpsDeviceId) {
        this.gpsDeviceId = gpsDeviceId;
    }

    public Double getOdometerReadingKm() {
        return odometerReadingKm;
    }

    public void setOdometerReadingKm(Double odometerReadingKm) {
        this.odometerReadingKm = odometerReadingKm;
    }

    public Double getAverageMileage() {
        return averageMileage;
    }

    public void setAverageMileage(Double averageMileage) {
        this.averageMileage = averageMileage;
    }

    public String getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(String ownershipType) {
        this.ownershipType = ownershipType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedDepot() {
        return assignedDepot;
    }

    public void setAssignedDepot(String assignedDepot) {
        this.assignedDepot = assignedDepot;
    }

    public LocalDate getLastServiceDate() {
        return lastServiceDate;
    }

    public void setLastServiceDate(LocalDate lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }

    public Integer getServiceIntervalKm() {
        return serviceIntervalKm;
    }

    public void setServiceIntervalKm(Integer serviceIntervalKm) {
        this.serviceIntervalKm = serviceIntervalKm;
    }

    public BigDecimal getAnnualMaintenanceCost() {
        return annualMaintenanceCost;
    }

    public void setAnnualMaintenanceCost(BigDecimal annualMaintenanceCost) {
        this.annualMaintenanceCost = annualMaintenanceCost;
    }

    public BigDecimal getFuelCostPerKm() {
        return fuelCostPerKm;
    }

    public void setFuelCostPerKm(BigDecimal fuelCostPerKm) {
        this.fuelCostPerKm = fuelCostPerKm;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private com.company.erp.company.entity.Company company;

    public com.company.erp.company.entity.Company getCompany() {
        return company;
    }

    public void setCompany(com.company.erp.company.entity.Company company) {
        this.company = company;
    }
}
