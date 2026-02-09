package com.company.erp.mining.controller;

import com.company.erp.mining.entity.MiningDumpTruck;
import com.company.erp.mining.service.MiningDumpTruckService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/mining/dump-trucks")
@CrossOrigin(origins = "http://localhost:5173")
public class MiningDumpTruckController {

    private final MiningDumpTruckService service;

    public MiningDumpTruckController(MiningDumpTruckService service) {
        this.service = service;
    }

    @GetMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasAnyRole('MANAGER', 'SUPERVISOR', 'GLOBAL', 'CHIEF', 'ADMIN')")
    public List<MiningDumpTruck> getAllTrucks() {
        return service.getAllTrucks();
    }

    @GetMapping("/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasAnyRole('MANAGER', 'SUPERVISOR', 'GLOBAL', 'CHIEF', 'ADMIN')")
    public ResponseEntity<MiningDumpTruck> getTruckById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getTruckById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/asset/{assetId}")
    @org.springframework.security.access.prepost.PreAuthorize("hasAnyRole('MANAGER', 'SUPERVISOR', 'GLOBAL', 'CHIEF', 'ADMIN')")
    public ResponseEntity<MiningDumpTruck> getTruckByAssetId(@PathVariable UUID assetId) {
        try {
            return ResponseEntity.ok(service.getTruckByAssetId(assetId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasAnyRole('MANAGER', 'GLOBAL', 'CHIEF', 'ADMIN')")
    public ResponseEntity<MiningDumpTruck> createTruck(@RequestBody MiningDumpTruck truck) {
        return ResponseEntity.ok(service.createTruck(truck));
    }

    @PutMapping("/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasAnyRole('MANAGER', 'GLOBAL', 'CHIEF', 'ADMIN')")
    public ResponseEntity<MiningDumpTruck> updateTruck(@PathVariable Long id, @RequestBody MiningDumpTruck details) {
        try {
            return ResponseEntity.ok(service.updateTruck(id, details));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasAnyRole('MANAGER', 'GLOBAL', 'CHIEF', 'ADMIN')")
    public ResponseEntity<Void> deleteTruck(@PathVariable Long id) {
        try {
            service.deleteTruck(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
