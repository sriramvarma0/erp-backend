package com.company.erp.mining.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "mining_vehicles")
public class MiningVehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type; // e.g., Excavator, Dump Truck, Drill

    private String status; // e.g., Operational, Maintenance

    // Vehicle Specifications
    @Column(name = "number_of_tyres")
    private Integer numberOfTyres;
    private String power;
    private String gvw;
    private String mileage;
    @Column(name = "engine_capacity")
    private String engineCapacity;
    @Column(name = "fuel_tank_capacity")
    private String fuelTankCapacity;
    @Column(name = "payload_capacity")
    private String payloadCapacity;
    @Column(name = "chassis_type")
    private String chassisType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNumberOfTyres() {
        return numberOfTyres;
    }

    public void setNumberOfTyres(Integer numberOfTyres) {
        this.numberOfTyres = numberOfTyres;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getGvw() {
        return gvw;
    }

    public void setGvw(String gvw) {
        this.gvw = gvw;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(String engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public String getFuelTankCapacity() {
        return fuelTankCapacity;
    }

    public void setFuelTankCapacity(String fuelTankCapacity) {
        this.fuelTankCapacity = fuelTankCapacity;
    }

    public String getPayloadCapacity() {
        return payloadCapacity;
    }

    public void setPayloadCapacity(String payloadCapacity) {
        this.payloadCapacity = payloadCapacity;
    }

    public String getChassisType() {
        return chassisType;
    }

    public void setChassisType(String chassisType) {
        this.chassisType = chassisType;
    }
}
