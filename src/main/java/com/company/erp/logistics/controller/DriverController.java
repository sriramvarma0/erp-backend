package com.company.erp.logistics.controller;

import com.company.erp.logistics.entity.Driver;
import com.company.erp.logistics.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logistics/drivers")
@CrossOrigin(origins = "http://localhost:5173")
public class DriverController {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private com.company.erp.global.service.UserService userService;

    @Autowired
    private com.company.erp.global.repository.RoleRepository roleRepository;

    @Autowired
    private com.company.erp.company.repository.CompanyRepository companyRepository;

    @GetMapping
    public List<Driver> getAllDrivers(@RequestParam(required = false) String department) {
        List<Driver> drivers;
        if (department != null && !department.isEmpty()) {
            drivers = driverRepository.findByDepartment(department);
        } else {
            drivers = driverRepository.findAll();
        }

        // Merge unlinked users as "Draft" drivers/operators
        String roleToFetch = "DRIVER";
        if (department != null && department.equalsIgnoreCase("Mining")) {
            roleToFetch = "OPERATOR";
        }

        List<com.company.erp.global.entity.User> unlinked = userService.getUnlinkedUsersByRole(roleToFetch);

        for (com.company.erp.global.entity.User u : unlinked) {
            Driver draft = new Driver();
            draft.setId(-u.getId()); // Negative ID to indicate transient/draft state
            draft.setFullName(u.getUsername()); // Use username as placeholder name
            draft.setDriverId("PENDING");
            draft.setStatus("Draft");
            draft.setUserId(u.getId());
            drivers.add(draft);
        }

        return drivers;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable Long id) {
        return driverRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/available-users")
    public List<com.company.erp.global.entity.User> getAvailableUsersForLinking() {
        return userService.getUnlinkedDrivers();
    }

    @PostMapping
    public Driver createDriver(@RequestBody Driver driver) {
        // 1. Create the Driver Logic
        if (driver.getDriverId() == null || driver.getDriverId().isEmpty()) {
            driver.setDriverId("DRV-" + System.currentTimeMillis());
        }

        // 2. Check if we need to create a User
        // We look for special transient fields in the Driver object or just assume we
        // need to create one if provided?
        // Use the Driver's EmployeeCode as Username if not provided?
        // Let's expect the frontend to ANYHOW send 'username' and 'password' in the
        // body.
        // But Driver entity doesn't have them. We need to add transient fields to
        // Driver entity or use a DTO.
        // For simplicity, we can use a Map or modify Driver entity to have @Transient
        // fields.

        // BETTER APPROACH: The frontend sends the user details inside the Driver object
        // if we add @Transient fields to Driver.java.
        // Let's assume we will add transient 'username' and 'password' to Driver.java
        // next.

        if (driver.getTransientUsername() != null && !driver.getTransientUsername().isEmpty()) {
            com.company.erp.global.entity.User user = new com.company.erp.global.entity.User();
            user.setUsername(driver.getTransientUsername());
            user.setPassword(driver.getTransientPassword()); // Will be encoded by UserService or here
            user.setEnabled(true);

            // Assign Company based on Department
            String targetCompany = "Logistics"; // Default
            if (driver.getDepartment() != null) {
                if (driver.getDepartment().equalsIgnoreCase("Mining")) {
                    targetCompany = "Mining";
                } else if (driver.getDepartment().equalsIgnoreCase("Construction")) {
                    targetCompany = "Construction";
                }
            }
            companyRepository.findByName(targetCompany).ifPresent(user::setCompany);

            // Assign Role: DRIVER
            roleRepository.findByName("DRIVER").ifPresent(role -> user.getRoles().add(role));

            // Create User
            com.company.erp.global.entity.User savedUser = userService.createUser(user);

            // Link to Driver
            driver.setUserId(savedUser.getId());
        } else if (driver.getUserId() != null) {
            // Linking existing user
            // Verify user exists and has ROLE_DRIVER? Optional but good practice.
            // For now, assume frontend sends valid ID.
        }

        return driverRepository.save(driver);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Driver> updateDriver(@PathVariable Long id, @RequestBody Driver driverDetails) {
        return driverRepository.findById(id)
                .map(driver -> {
                    // Personal Info
                    driver.setFullName(driverDetails.getFullName());
                    driver.setDob(driverDetails.getDob());
                    driver.setGender(driverDetails.getGender());
                    driver.setContactNumber(driverDetails.getContactNumber());
                    driver.setEmergencyContactNumber(driverDetails.getEmergencyContactNumber());
                    driver.setAddressPermanent(driverDetails.getAddressPermanent());
                    driver.setAddressCurrent(driverDetails.getAddressCurrent());

                    // License
                    driver.setLicenseNumber(driverDetails.getLicenseNumber());
                    driver.setLicenseType(driverDetails.getLicenseType());
                    driver.setLicenseIssuingAuthority(driverDetails.getLicenseIssuingAuthority());
                    driver.setLicenseIssueDate(driverDetails.getLicenseIssueDate());
                    driver.setLicenseExpiryDate(driverDetails.getLicenseExpiryDate());

                    // Compliance
                    driver.setAadhaarNumber(driverDetails.getAadhaarNumber());
                    driver.setPanNumber(driverDetails.getPanNumber());
                    driver.setPoliceVerificationStatus(driverDetails.getPoliceVerificationStatus());
                    driver.setMedicalFitnessExpiry(driverDetails.getMedicalFitnessExpiry());
                    driver.setAlcoholTestRequired(driverDetails.getAlcoholTestRequired());

                    // Employment & Operations
                    driver.setEmploymentType(driverDetails.getEmploymentType());
                    driver.setDateOfJoining(driverDetails.getDateOfJoining());
                    driver.setShiftType(driverDetails.getShiftType());
                    driver.setAssignedDepot(driverDetails.getAssignedDepot());
                    driver.setReportingManager(driverDetails.getReportingManager());
                    // Handle both generic Vehicle and MiningVehicle
                    if (driverDetails.getAssignedVehicle() != null) {
                        driver.setAssignedVehicle(driverDetails.getAssignedVehicle());
                    }
                    driver.setRouteAssigned(driverDetails.getRouteAssigned());
                    driver.setDrivingExperienceYears(driverDetails.getDrivingExperienceYears());

                    // Status & Performance
                    driver.setStatus(driverDetails.getStatus());
                    driver.setAvailabilityStatus(driverDetails.getAvailabilityStatus());
                    driver.setDriverRating(driverDetails.getDriverRating());
                    driver.setDepartment(driverDetails.getDepartment());

                    // Payroll
                    driver.setSalaryType(driverDetails.getSalaryType());
                    driver.setBaseSalary(driverDetails.getBaseSalary());
                    driver.setAllowances(driverDetails.getAllowances());
                    driver.setIncentives(driverDetails.getIncentives());
                    driver.setPenalties(driverDetails.getPenalties());

                    // Handle User Linking/Creation during Update
                    if (driver.getUserId() == null) {
                        if (driverDetails.getTransientUsername() != null
                                && !driverDetails.getTransientUsername().isEmpty()) {
                            com.company.erp.global.entity.User user = new com.company.erp.global.entity.User();
                            user.setUsername(driverDetails.getTransientUsername());
                            user.setPassword(driverDetails.getTransientPassword());
                            user.setEnabled(true);

                            companyRepository.findByName("Logistics").ifPresent(user::setCompany);

                            roleRepository.findByName("DRIVER").ifPresent(role -> user.getRoles().add(role));

                            com.company.erp.global.entity.User savedUser = userService.createUser(user);
                            driver.setUserId(savedUser.getId());
                        } else if (driverDetails.getUserId() != null) {
                            driver.setUserId(driverDetails.getUserId());
                        }
                    }

                    return ResponseEntity.ok(driverRepository.save(driver));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDriver(@PathVariable Long id) {
        return driverRepository.findById(id)
                .map(driver -> {
                    driverRepository.delete(driver);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
