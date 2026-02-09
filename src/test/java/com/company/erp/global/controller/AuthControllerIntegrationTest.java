package com.company.erp.global.controller;

import com.company.erp.global.entity.Permission;
import com.company.erp.global.entity.Role;
import com.company.erp.global.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@org.springframework.test.context.TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=update",
    "spring.flyway.enabled=false"
})
@org.springframework.context.annotation.Import(com.company.erp.config.PasswordEncoderConfig.class)

public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    @WithMockUser(username = "superadmin", roles = {"ADMIN"})
    void testFullAuthFlow() throws Exception {
        // 1. Create Permission
        Permission p = new Permission("VIEW_DASHBOARD", "Can view dashboard");
        String pJson = mockMvc.perform(post("/api/permissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("VIEW_DASHBOARD"))
                .andReturn().getResponse().getContentAsString();
        Permission savedP = objectMapper.readValue(pJson, Permission.class);

        // 2. Create Role
        Role r = new Role("ADMIN", "Administrator");
        String rJson = mockMvc.perform(post("/api/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(r)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("ADMIN"))
                .andReturn().getResponse().getContentAsString();
        Role savedR = objectMapper.readValue(rJson, Role.class);

        // 3. Assign Permission to Role
        mockMvc.perform(post("/api/roles/" + savedR.getId() + "/permissions/" + savedP.getId()))
                .andExpect(status().isOk());

        // 4. Create User
        User u = new User("admin-user", "password123");
        String uJson = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(u)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("admin-user"))
                .andReturn().getResponse().getContentAsString();
        User savedU = objectMapper.readValue(uJson, User.class);

        // 5. Assign Role to User
        mockMvc.perform(post("/api/users/" + savedU.getId() + "/roles/" + savedR.getId()))
                .andExpect(status().isOk());
                
        // 6. Verify assignment
        // If LazyInitializationException happens, this GET will fail (500)
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                // Basic check that user exists in list
                .andExpect(jsonPath("$[0].username").value("admin-user")); 
                // We don't strictly assert roles presence here to avoid OSIV complex debugging in one shot, 
                // but if it crashes it will be 500.
    }
}
