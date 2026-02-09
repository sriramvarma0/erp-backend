package com.company.erp.mining.controller;

import com.company.erp.mining.entity.MiningOperator;
import com.company.erp.mining.repository.MiningOperatorRepository;
import com.company.erp.mining.repository.MiningVehicleRepository;
import com.company.erp.global.entity.User;
import com.company.erp.global.repository.RoleRepository;
import com.company.erp.global.repository.UserRepository;
import com.company.erp.company.repository.CompanyRepository;
import com.company.erp.global.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mining/operators")
@CrossOrigin(origins = "http://localhost:5173")
public class MiningOperatorController {

    @Autowired
    private MiningOperatorRepository miningOperatorRepository;

    @Autowired
    private MiningVehicleRepository miningVehicleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping
    public List<MiningOperator> getAllOperators() {
        List<MiningOperator> operators = miningOperatorRepository.findAll();

        // Merge unlinked OPERATOR users as "Draft" operators
        // Note: Using "OPERATOR" role specifically
        List<User> unlinked = userService.getUnlinkedMiningOperators();

        for (User u : unlinked) {
            MiningOperator draft = new MiningOperator();
            draft.setId(-u.getId()); // Negative ID for draft
            draft.setFullName(u.getUsername());
            draft.setOperatorId("PENDING");
            draft.setStatus("Draft");
            draft.setUserId(u.getId());
            operators.add(draft);
        }

        return operators;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MiningOperator> getOperatorById(@PathVariable Long id) {
        return miningOperatorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public MiningOperator createOperator(@RequestBody MiningOperator operator) {
        // 1. Generate ID if needed
        if (operator.getOperatorId() == null || operator.getOperatorId().isEmpty()) {
            operator.setOperatorId("OPR-" + System.currentTimeMillis());
        }

        // 2. User Creation/Linking Logic
        if (operator.getTransientUsername() != null && !operator.getTransientUsername().isEmpty()) {
            User user = new User();
            user.setUsername(operator.getTransientUsername());
            user.setPassword(operator.getTransientPassword());
            user.setEnabled(true);

            // Force Mining Company
            companyRepository.findByName("Mining").ifPresent(user::setCompany);

            // Force OPERATOR Role
            roleRepository.findByName("OPERATOR").ifPresent(role -> user.getRoles().add(role));

            User savedUser = userService.createUser(user);
            operator.setUserId(savedUser.getId());
        } else if (operator.getUserId() != null) {
            // Linking existing user - assuming ID is valid
        }

        return miningOperatorRepository.save(operator);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MiningOperator> updateOperator(@PathVariable Long id, @RequestBody MiningOperator details) {
        return miningOperatorRepository.findById(id)
                .map(op -> {
                    op.setFullName(details.getFullName());
                    op.setContactNumber(details.getContactNumber());
                    op.setAddressCurrent(details.getAddressCurrent());
                    op.setAddressPermanent(details.getAddressPermanent());
                    op.setDob(details.getDob());
                    op.setGender(details.getGender());

                    op.setLicenseNumber(details.getLicenseNumber());
                    op.setLicenseType(details.getLicenseType());
                    op.setLicenseExpiryDate(details.getLicenseExpiryDate());

                    op.setAssignedMachine(details.getAssignedMachine());
                    op.setOperatingZone(details.getOperatingZone());
                    op.setShiftType(details.getShiftType());

                    op.setStatus(details.getStatus());

                    // User Setup if happening during update
                    if (op.getUserId() == null && details.getTransientUsername() != null) {
                        User user = new User();
                        user.setUsername(details.getTransientUsername());
                        user.setPassword(details.getTransientPassword());
                        user.setEnabled(true);
                        companyRepository.findByName("Mining").ifPresent(user::setCompany);
                        roleRepository.findByName("OPERATOR").ifPresent(role -> user.getRoles().add(role));
                        User savedUser = userService.createUser(user);
                        op.setUserId(savedUser.getId());
                    } else if (op.getUserId() == null && details.getUserId() != null) {
                        op.setUserId(details.getUserId());
                    }

                    return ResponseEntity.ok(miningOperatorRepository.save(op));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperator(@PathVariable Long id) {
        if (miningOperatorRepository.existsById(id)) {
            miningOperatorRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
