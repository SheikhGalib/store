package com.sheikhgalib.store.integration;

import com.sheikhgalib.store.entity.User;
import com.sheikhgalib.store.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for AuthController
 * Tests authentication, registration, and authorization flows
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        // Clear users for clean tests
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Should display login page")
    void login_ShowsLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    @DisplayName("Should display login page with error message on invalid credentials")
    void login_InvalidCredentials_ShowsError() throws Exception {
        mockMvc.perform(get("/login?error=true"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(content().string(containsString("Invalid username or password")));
    }

    @Test
    @DisplayName("Should display login page with logout message")
    void login_AfterLogout_ShowsMessage() throws Exception {
        mockMvc.perform(get("/login?logout=true"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(content().string(containsString("logged out")));
    }

    @Test
    @DisplayName("Should display registration form")
    void register_ShowsRegistrationForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    @DisplayName("Should register new user successfully")
    void register_ValidData_CreatesUser() throws Exception {
        mockMvc.perform(post("/register")
                        .with(csrf())
                        .param("username", "newuser")
                        .param("email", "newuser@test.com")
                        .param("password", "password123")
                        .param("role", "STUDENT"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?registered=true"));
    }

    @Test
    @DisplayName("Should fail registration with existing username")
    void register_ExistingUsername_ShowsError() throws Exception {
        // Create existing user
        User existingUser = new User();
        existingUser.setUsername("existinguser");
        existingUser.setEmail("existing@test.com");
        existingUser.setPassword(passwordEncoder.encode("password"));
        existingUser.setRoles(Set.of("ROLE_STUDENT"));
        userRepository.save(existingUser);

        mockMvc.perform(post("/register")
                        .with(csrf())
                        .param("username", "existinguser")
                        .param("email", "new@test.com")
                        .param("password", "password123")
                        .param("role", "STUDENT"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(content().string(containsString("Username already exists")));
    }

    @Test
    @DisplayName("Should fail registration with existing email")
    void register_ExistingEmail_ShowsError() throws Exception {
        // Create existing user
        User existingUser = new User();
        existingUser.setUsername("existinguser");
        existingUser.setEmail("existing@test.com");
        existingUser.setPassword(passwordEncoder.encode("password"));
        existingUser.setRoles(Set.of("ROLE_STUDENT"));
        userRepository.save(existingUser);

        mockMvc.perform(post("/register")
                        .with(csrf())
                        .param("username", "newuser")
                        .param("email", "existing@test.com")
                        .param("password", "password123")
                        .param("role", "STUDENT"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(content().string(containsString("Email already exists")));
    }

    @Test
    @DisplayName("Should display dashboard for authenticated user")
    @WithMockUser(username = "testuser")
    void dashboard_Authenticated_ShowsDashboard() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(content().string(containsString("testuser")));
    }

    @Test
    @DisplayName("Should redirect to login when accessing dashboard without authentication")
    void dashboard_Unauthenticated_RedirectsToLogin() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @DisplayName("Should display access denied page")
    @WithMockUser
    void accessDenied_ShowsAccessDeniedPage() throws Exception {
        mockMvc.perform(get("/access-denied"))
                .andExpect(status().isOk())
                .andExpect(view().name("access-denied"));
    }

    @Test
    @DisplayName("Should handle logout correctly")
    @WithMockUser
    void logout_Authenticated_RedirectsToLogin() throws Exception {
        mockMvc.perform(post("/logout").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?logout=true"));
    }
}
