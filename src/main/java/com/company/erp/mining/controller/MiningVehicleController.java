package com.company.erp.mining.controller;

import com.company.erp.mining.entity.MiningVehicle;
import com.company.erp.mining.repository.MiningVehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mining/vehicles")
@CrossOrigin(origins = "http://localhost:5173")
public class MiningVehicleController {

    @Autowired
    private MiningVehicleRepository miningVehicleRepository;

    @GetMapping
    public List<MiningVehicle> getAllVehicles() {
        return miningVehicleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MiningVehicle> getVehicleById(@PathVariable Long id) {
        return miningVehicleRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public MiningVehicle createVehicle(@RequestBody MiningVehicle vehicle) {
        return miningVehicleRepository.save(vehicle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MiningVehicle> updateVehicle(@PathVariable Long id, @RequestBody MiningVehicle vehicleDetails) {
        return miningVehicleRepository.findById(id)
                .map(vehicle -> {
                    vehicle.setName(vehicleDetails.getName());
                    vehicle.setType(vehicleDetails.getType());
                    vehicle.setStatus(vehicleDetails.getStatus());
                    return ResponseEntity.ok(miningVehicleRepository.save(vehicle));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        if (miningVehicleRepository.existsById(id)) {
            miningVehicleRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
