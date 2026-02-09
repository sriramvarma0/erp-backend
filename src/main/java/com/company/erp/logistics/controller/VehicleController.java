package com.company.erp.logistics.controller;

import com.company.erp.logistics.entity.Vehicle;
import com.company.erp.logistics.repository.VehicleRepository;
import com.company.erp.global.entity.User;
import com.company.erp.global.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logistics/vehicles")
@CrossOrigin(origins = "http://localhost:5173")
public class VehicleController {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    private boolean isGlobalOrAdmin(User user) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("GLOBAL") || role.getName().equals("CHIEF"));
    }

    @GetMapping
    public List<Vehicle> getAllVehicles() {
        User currentUser = getCurrentUser();

        if (isGlobalOrAdmin(currentUser)) {
            return vehicleRepository.findAll();
        }

        // Strict filtering for subsidiary users
        if (currentUser.getCompany() == null) {
            throw new RuntimeException("User has no assigned company");
        }
        return vehicleRepository.findAllByCompany(currentUser.getCompany());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVehicleById(@PathVariable Long id) {
        User currentUser = getCurrentUser();

        java.util.Optional<Vehicle> vehicleOpt = vehicleRepository.findById(id);
        if (vehicleOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Vehicle vehicle = vehicleOpt.get();

        // Tenancy Check
        if (!isGlobalOrAdmin(currentUser)) {
            if (vehicle.getCompany() == null ||
                    !vehicle.getCompany().getId().equals(currentUser.getCompany().getId())) {
                return ResponseEntity.status(403).build();
            }
        }
        return ResponseEntity.ok(vehicle);
    }

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
        User currentUser = getCurrentUser();

        // If not global, force the company to match the user's company
        if (!isGlobalOrAdmin(currentUser)) {
            vehicle.setCompany(currentUser.getCompany());
        }
        return ResponseEntity.ok(vehicleRepository.save(vehicle));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVehicle(@PathVariable Long id, @RequestBody Vehicle vehicleDetails) {
        User currentUser = getCurrentUser();

        java.util.Optional<Vehicle> vehicleOpt = vehicleRepository.findById(id);
        if (vehicleOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Vehicle vehicle = vehicleOpt.get();

        // Tenancy Check
        if (!isGlobalOrAdmin(currentUser)) {
            if (vehicle.getCompany() == null ||
                    !vehicle.getCompany().getId().equals(currentUser.getCompany().getId())) {
                return ResponseEntity.status(403).build();
            }
        }

        // Identification
        vehicle.setFleetNumber(vehicleDetails.getFleetNumber());

        // Manufacturer
        vehicle.setMake(vehicleDetails.getMake());
        vehicle.setModel(vehicleDetails.getModel());
        vehicle.setManufacturingYear(vehicleDetails.getManufacturingYear());

        // Power
        vehicle.setPowertrainType(vehicleDetails.getPowertrainType());
        vehicle.setEnginePowerHp(vehicleDetails.getEnginePowerHp());
        vehicle.setEngineCapacityCc(vehicleDetails.getEngineCapacityCc());
        vehicle.setTransmissionType(vehicleDetails.getTransmissionType());
        vehicle.setEmissionNorm(vehicleDetails.getEmissionNorm());

        // Physical
        vehicle.setNumberOfWheels(vehicleDetails.getNumberOfWheels());
        vehicle.setAxleConfiguration(vehicleDetails.getAxleConfiguration());
        vehicle.setBodyType(vehicleDetails.getBodyType());
        vehicle.setTrailerAttached(vehicleDetails.getTrailerAttached());

        // Load
        vehicle.setPayloadCapacityTons(vehicleDetails.getPayloadCapacityTons());
        vehicle.setGrossVehicleWeight(vehicleDetails.getGrossVehicleWeight());
        vehicle.setLoadVolumeCbm(vehicleDetails.getLoadVolumeCbm());

        // Compliance
        vehicle.setInsuranceExpiry(vehicleDetails.getInsuranceExpiry());
        vehicle.setFitnessExpiry(vehicleDetails.getFitnessExpiry());
        vehicle.setPermitType(vehicleDetails.getPermitType());
        vehicle.setPucExpiry(vehicleDetails.getPucExpiry());

        // Tracking
        vehicle.setGpsDeviceId(vehicleDetails.getGpsDeviceId());
        vehicle.setOdometerReadingKm(vehicleDetails.getOdometerReadingKm());
        vehicle.setAverageMileage(vehicleDetails.getAverageMileage());

        // Ownership & Status
        vehicle.setOwnershipType(vehicleDetails.getOwnershipType());
        vehicle.setStatus(vehicleDetails.getStatus());
        vehicle.setAssignedDepot(vehicleDetails.getAssignedDepot());

        // Maintenance
        vehicle.setLastServiceDate(vehicleDetails.getLastServiceDate());
        vehicle.setServiceIntervalKm(vehicleDetails.getServiceIntervalKm());
        vehicle.setAnnualMaintenanceCost(vehicleDetails.getAnnualMaintenanceCost());
        vehicle.setFuelCostPerKm(vehicleDetails.getFuelCostPerKm());

        vehicle.setVehicleId(vehicleDetails.getVehicleId());
        vehicle.setRegistrationNumber(vehicleDetails.getRegistrationNumber());

        // Allow company update only if Admin
        if (isGlobalOrAdmin(currentUser)) {
            vehicle.setCompany(vehicleDetails.getCompany());
        }

        return ResponseEntity.ok(vehicleRepository.save(vehicle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        User currentUser = getCurrentUser();

        java.util.Optional<Vehicle> vehicleOpt = vehicleRepository.findById(id);
        if (vehicleOpt.isPresent()) {
            Vehicle vehicle = vehicleOpt.get();
            // Tenancy Check
            if (!isGlobalOrAdmin(currentUser)) {
                if (vehicle.getCompany() == null ||
                        !vehicle.getCompany().getId().equals(currentUser.getCompany().getId())) {
                    return ResponseEntity.status(403).build();
                }
            }

            vehicleRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
