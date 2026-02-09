package com.company.erp.logistics.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "driver_id", nullable = false, unique = true)
    private String driverId;

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

    // --- License Details ---
    @Column(name = "license_number", nullable = false, unique = true)
    private String licenseNumber;

    @Column(name = "license_type")
    private String licenseType;

    @Column(name = "license_issuing_authority")
    private String licenseIssuingAuthority;

    @Column(name = "license_issue_date")
    private LocalDate licenseIssueDate;

    @Column(name = "license_expiry_date")
    private LocalDate licenseExpiryDate;

    // --- Compliance ---
    @Column(name = "aadhaar_number", unique = true)
    private String aadhaarNumber;

    @Column(name = "pan_number")
    private String panNumber;

    @Column(name = "police_verification_status")
    private String policeVerificationStatus;

    @Column(name = "medical_fitness_expiry")
    private LocalDate medicalFitnessExpiry;

    @Column(name = "alcohol_test_required")
    private Boolean alcoholTestRequired;

    // --- Employment ---
    @Column(name = "employment_type")
    private String employmentType;

    @Column(name = "date_of_joining")
    private LocalDate dateOfJoining;

    @Column(name = "shift_type")
    private String shiftType;

    @Column(name = "assigned_depot")
    private String assignedDepot;

    @Column(name = "reporting_manager")
    private String reportingManager;

    // --- Vehicle & Operations ---
    @ManyToOne
    @JoinColumn(name = "assigned_vehicle_id")
    private Vehicle assignedVehicle;

    @Column(name = "route_assigned")
    private String routeAssigned;

    @Column(name = "driving_experience_years")
    private Integer drivingExperienceYears;

    @Column(name = "trip_count")
    private Integer tripCount;

    // --- Performance & Safety ---
    @Column(name = "accident_history")
    private Boolean accidentHistory;

    @Column(name = "number_of_accidents")
    private Integer numberOfAccidents;

    @Column(name = "traffic_violations_count")
    private Integer trafficViolationsCount;

    @Column(name = "safety_training_completed")
    private Boolean safetyTrainingCompleted;

    @Column(name = "driver_rating")
    private BigDecimal driverRating;

    // --- Payroll ---
    @Column(name = "salary_type")
    private String salaryType;

    @Column(name = "base_salary")
    private BigDecimal baseSalary;

    private BigDecimal allowances;
    private BigDecimal incentives;
    private BigDecimal penalties;

    // --- Status ---
    private String status;

    @Column(name = "availability_status")
    private String availabilityStatus;

    @Column(name = "last_trip_date")
    private LocalDateTime lastTripDate;

    @Column(name = "user_id", unique = true)
    private Long userId; // Linked manually to User entity if needed

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

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

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
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

    public String getLicenseIssuingAuthority() {
        return licenseIssuingAuthority;
    }

    public void setLicenseIssuingAuthority(String licenseIssuingAuthority) {
        this.licenseIssuingAuthority = licenseIssuingAuthority;
    }

    public LocalDate getLicenseIssueDate() {
        return licenseIssueDate;
    }

    public void setLicenseIssueDate(LocalDate licenseIssueDate) {
        this.licenseIssueDate = licenseIssueDate;
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

    public String getPoliceVerificationStatus() {
        return policeVerificationStatus;
    }

    public void setPoliceVerificationStatus(String policeVerificationStatus) {
        this.policeVerificationStatus = policeVerificationStatus;
    }

    public LocalDate getMedicalFitnessExpiry() {
        return medicalFitnessExpiry;
    }

    public void setMedicalFitnessExpiry(LocalDate medicalFitnessExpiry) {
        this.medicalFitnessExpiry = medicalFitnessExpiry;
    }

    public Boolean getAlcoholTestRequired() {
        return alcoholTestRequired;
    }

    public void setAlcoholTestRequired(Boolean alcoholTestRequired) {
        this.alcoholTestRequired = alcoholTestRequired;
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

    public String getAssignedDepot() {
        return assignedDepot;
    }

    public void setAssignedDepot(String assignedDepot) {
        this.assignedDepot = assignedDepot;
    }

    public String getReportingManager() {
        return reportingManager;
    }

    public void setReportingManager(String reportingManager) {
        this.reportingManager = reportingManager;
    }

    public Vehicle getAssignedVehicle() {
        return assignedVehicle;
    }

    public void setAssignedVehicle(Vehicle assignedVehicle) {
        this.assignedVehicle = assignedVehicle;
    }

    public String getRouteAssigned() {
        return routeAssigned;
    }

    public void setRouteAssigned(String routeAssigned) {
        this.routeAssigned = routeAssigned;
    }

    public Integer getDrivingExperienceYears() {
        return drivingExperienceYears;
    }

    public void setDrivingExperienceYears(Integer drivingExperienceYears) {
        this.drivingExperienceYears = drivingExperienceYears;
    }

    public Integer getTripCount() {
        return tripCount;
    }

    public void setTripCount(Integer tripCount) {
        this.tripCount = tripCount;
    }

    public Boolean getAccidentHistory() {
        return accidentHistory;
    }

    public void setAccidentHistory(Boolean accidentHistory) {
        this.accidentHistory = accidentHistory;
    }

    public Integer getNumberOfAccidents() {
        return numberOfAccidents;
    }

    public void setNumberOfAccidents(Integer numberOfAccidents) {
        this.numberOfAccidents = numberOfAccidents;
    }

    public Integer getTrafficViolationsCount() {
        return trafficViolationsCount;
    }

    public void setTrafficViolationsCount(Integer trafficViolationsCount) {
        this.trafficViolationsCount = trafficViolationsCount;
    }

    public Boolean getSafetyTrainingCompleted() {
        return safetyTrainingCompleted;
    }

    public void setSafetyTrainingCompleted(Boolean safetyTrainingCompleted) {
        this.safetyTrainingCompleted = safetyTrainingCompleted;
    }

    public BigDecimal getDriverRating() {
        return driverRating;
    }

    public void setDriverRating(BigDecimal driverRating) {
        this.driverRating = driverRating;
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

    public BigDecimal getPenalties() {
        return penalties;
    }

    public void setPenalties(BigDecimal penalties) {
        this.penalties = penalties;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public LocalDateTime getLastTripDate() {
        return lastTripDate;
    }

    public void setLastTripDate(LocalDateTime lastTripDate) {
        this.lastTripDate = lastTripDate;
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

    @Column(name = "department")
    private String department; // e.g., "Logistics", "Mining"

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Transient
    private String transientUsername;

    @Transient
    private String transientPassword;

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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
