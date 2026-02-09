package com.company.erp.mining.entity;

import com.company.erp.global.entity.User;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "mining_operators")
public class MiningOperator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "operator_id", nullable = false, unique = true)
    private String operatorId; // e.g., OPR-123

    @Column(name = "employee_code", unique = true)
    private String employeeCode;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    private LocalDate dob;
    private String gender;

    @Column(name = "contact_number", nullable = false)
    private String contactNumber;

    @Column(name = "emergency_contact_number")
    private String emergencyContactNumber;

    @Column(name = "address_permanent", columnDefinition = "TEXT")
    private String addressPermanent;

    @Column(name = "address_current", columnDefinition = "TEXT")
    private String addressCurrent;

    // --- License / Certification ---
    @Column(name = "license_number")
    private String licenseNumber; // Machine License Number

    @Column(name = "license_type")
    private String licenseType; // e.g., HMV, Excavator Cert

    @Column(name = "license_expiry_date")
    private LocalDate licenseExpiryDate;

    // --- Compliance ---
    @Column(name = "aadhaar_number")
    private String aadhaarNumber;

    @Column(name = "pan_number")
    private String panNumber;

    @Column(name = "medical_fitness_expiry")
    private LocalDate medicalFitnessExpiry;

    // --- Employment ---
    @Column(name = "employment_type")
    private String employmentType; // Permanent, Contract

    @Column(name = "date_of_joining")
    private LocalDate dateOfJoining;

    @Column(name = "shift_type")
    private String shiftType; // Morning, Night, Rotational

    @Column(name = "reporting_manager")
    private String reportingManager;

    // --- Machine Assignment ---
    @ManyToOne
    @JoinColumn(name = "assigned_machine_id")
    private MiningVehicle assignedMachine;

    @Column(name = "operating_zone")
    private String operatingZone; // e.g., Pit A, Dump Yard

    @Column(name = "experience_years")
    private Integer experienceYears;

    // --- Payroll ---
    @Column(name = "salary_type")
    private String salaryType;

    @Column(name = "base_salary")
    private BigDecimal baseSalary;

    private BigDecimal allowances;
    private BigDecimal incentives;

    // --- Status ---
    private String status; // Active, Suspended, Leave

    @Column(name = "last_active_date")
    private LocalDateTime lastActiveDate;

    @Column(name = "user_id", unique = true)
    private Long userId; // Link to Global User

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Transients for User Creation
    @Transient
    private String transientUsername;
    @Transient
    private String transientPassword;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmergencyContactNumber() {
        return emergencyContactNumber;
    }

    public void setEmergencyContactNumber(String emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
    }

    public String getAddressPermanent() {
        return addressPermanent;
    }

    public void setAddressPermanent(String addressPermanent) {
        this.addressPermanent = addressPermanent;
    }

    public String getAddressCurrent() {
        return addressCurrent;
    }

    public void setAddressCurrent(String addressCurrent) {
        this.addressCurrent = addressCurrent;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public LocalDate getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public void setLicenseExpiryDate(LocalDate licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public LocalDate getMedicalFitnessExpiry() {
        return medicalFitnessExpiry;
    }

    public void setMedicalFitnessExpiry(LocalDate medicalFitnessExpiry) {
        this.medicalFitnessExpiry = medicalFitnessExpiry;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(LocalDate dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getShiftType() {
        return shiftType;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }

    public String getReportingManager() {
        return reportingManager;
    }

    public void setReportingManager(String reportingManager) {
        this.reportingManager = reportingManager;
    }

    public MiningVehicle getAssignedMachine() {
        return assignedMachine;
    }

    public void setAssignedMachine(MiningVehicle assignedMachine) {
        this.assignedMachine = assignedMachine;
    }

    public String getOperatingZone() {
        return operatingZone;
    }

    public void setOperatingZone(String operatingZone) {
        this.operatingZone = operatingZone;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(String salaryType) {
        this.salaryType = salaryType;
    }

    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
    }

    public BigDecimal getAllowances() {
        return allowances;
    }

    public void setAllowances(BigDecimal allowances) {
        this.allowances = allowances;
    }

    public BigDecimal getIncentives() {
        return incentives;
    }

    public void setIncentives(BigDecimal incentives) {
        this.incentives = incentives;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getLastActiveDate() {
        return lastActiveDate;
    }

    public void setLastActiveDate(LocalDateTime lastActiveDate) {
        this.lastActiveDate = lastActiveDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getTransientUsername() {
        return transientUsername;
    }

    public void setTransientUsername(String transientUsername) {
        this.transientUsername = transientUsername;
    }

    public String getTransientPassword() {
        return transientPassword;
    }

    public void setTransientPassword(String transientPassword) {
        this.transientPassword = transientPassword;
    }
}
