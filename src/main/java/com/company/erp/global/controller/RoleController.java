package com.company.erp.global.controller;

import com.company.erp.global.entity.Role;
import com.company.erp.global.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'GLOBAL', 'CHIEF')")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @PostMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasAnyRole('ADMIN', 'GLOBAL', 'CHIEF')")
    // Only Admin/Global/Chief should create new ROLES (types of roles). Managers
    // assign roles.
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role saved = roleService.save(role);
        return ResponseEntity.created(URI.create("/api/roles/" + saved.getId()))
                .body(saved);
    }

    @PostMapping("/{roleId}/permissions/{permissionId}")
    @org.springframework.security.access.prepost.PreAuthorize("hasAnyRole('ADMIN', 'GLOBAL')")
    public ResponseEntity<Void> assignPermissionToRole(@PathVariable Long roleId,
            @PathVariable Long permissionId) {
        roleService.assignPermissionToRole(roleId, permissionId);
        return ResponseEntity.ok().build();
    }
}
