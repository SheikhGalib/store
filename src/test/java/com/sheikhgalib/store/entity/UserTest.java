package com.sheikhgalib.store.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for User entity
 * Tests authentication and authorization properties
 */
class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    @DisplayName("Should set and get all properties correctly")
    void testGettersAndSetters() {
        // Arrange
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_STUDENT");
        roles.add("ROLE_TEACHER");

        // Act
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("encodedPassword");
        user.setEmail("test@test.com");
        user.setRoles(roles);
        user.setEnabled(true);

        // Assert
        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("encodedPassword", user.getPassword());
        assertEquals("test@test.com", user.getEmail());
        assertEquals(roles, user.getRoles());
        assertTrue(user.isEnabled());
    }

    @Test
    @DisplayName("Should default enabled to true")
    void testDefaultEnabled() {
        // Assert
        assertTrue(user.isEnabled());
    }

    @Test
    @DisplayName("Should initialize with empty roles set")
    void testRolesInitialization() {
        // Assert
        assertNotNull(user.getRoles());
        assertTrue(user.getRoles().isEmpty());
    }

    @Test
    @DisplayName("Should handle disabled user")
    void testDisabledUser() {
        // Act
        user.setEnabled(false);

        // Assert
        assertFalse(user.isEnabled());
    }

    @Test
    @DisplayName("Should handle multiple roles")
    void testMultipleRoles() {
        // Arrange
        Set<String> roles = Set.of("ROLE_STUDENT", "ROLE_TEACHER", "ROLE_ADMIN");

        // Act
        user.setRoles(roles);

        // Assert
        assertEquals(3, user.getRoles().size());
        assertTrue(user.getRoles().contains("ROLE_STUDENT"));
        assertTrue(user.getRoles().contains("ROLE_TEACHER"));
        assertTrue(user.getRoles().contains("ROLE_ADMIN"));
    }
}
