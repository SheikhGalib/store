package com.sheikhgalib.store.controller;

import com.sheikhgalib.store.entity.User;
import com.sheikhgalib.store.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AuthController
 * Tests login, registration, and dashboard functionality
 */
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Model model;

    @InjectMocks
    private AuthController authController;

    // Simple Principal implementation for testing
    private Principal testPrincipal;

    @BeforeEach
    void setUp() {
        testPrincipal = () -> "testuser";
    }

    @Test
    @DisplayName("Should display login page")
    void login_NoParams_ReturnsLoginView() {
        // Act
        String viewName = authController.login(null, null, model);

        // Assert
        assertEquals("login", viewName);
        verify(model, never()).addAttribute(eq("error"), any());
        verify(model, never()).addAttribute(eq("message"), any());
    }

    @Test
    @DisplayName("Should display login page with error message")
    void login_WithError_ReturnsLoginViewWithError() {
        // Act
        String viewName = authController.login("true", null, model);

        // Assert
        assertEquals("login", viewName);
        verify(model).addAttribute("error", "Invalid username or password");
    }

    @Test
    @DisplayName("Should display login page with logout message")
    void login_WithLogout_ReturnsLoginViewWithMessage() {
        // Act
        String viewName = authController.login(null, "true", model);

        // Assert
        assertEquals("login", viewName);
        verify(model).addAttribute("message", "You have been logged out successfully");
    }

    @Test
    @DisplayName("Should display registration form")
    void registerForm_ReturnsRegisterView() {
        // Act
        String viewName = authController.registerForm();

        // Assert
        assertEquals("register", viewName);
    }

    @Test
    @DisplayName("Should register new user successfully")
    void register_ValidUser_RedirectsToLogin() {
        // Arrange
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("newuser@test.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(new User());

        // Act
        String viewName = authController.register("newuser", "newuser@test.com",
                "password123", "STUDENT", model);

        // Assert
        assertEquals("redirect:/login?registered=true", viewName);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should fail registration with existing username")
    void register_ExistingUsername_ReturnsRegisterWithError() {
        // Arrange
        when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        // Act
        String viewName = authController.register("existinguser", "new@test.com",
                "password123", "STUDENT", model);

        // Assert
        assertEquals("register", viewName);
        verify(model).addAttribute("error", "Username already exists");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should fail registration with existing email")
    void register_ExistingEmail_ReturnsRegisterWithError() {
        // Arrange
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("existing@test.com")).thenReturn(true);

        // Act
        String viewName = authController.register("newuser", "existing@test.com",
                "password123", "STUDENT", model);

        // Assert
        assertEquals("register", viewName);
        verify(model).addAttribute("error", "Email already exists");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should display dashboard for authenticated user")
    void dashboard_AuthenticatedUser_ReturnsDashboardView() {
        // Act
        String viewName = authController.dashboard(testPrincipal, model);

        // Assert
        assertEquals("dashboard", viewName);
        verify(model).addAttribute("username", "testuser");
    }

    @Test
    @DisplayName("Should display access denied page")
    void accessDenied_ReturnsAccessDeniedView() {
        // Act
        String viewName = authController.accessDenied();

        // Assert
        assertEquals("access-denied", viewName);
    }
}
