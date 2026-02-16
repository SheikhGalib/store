package com.sheikhgalib.store.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Student entity
 * Tests getters, setters, and relationships
 */
class StudentTest {

    private Student student;
    private Department department;
    private User user;

    @BeforeEach
    void setUp() {
        student = new Student();
        department = new Department();
        department.setId(1L);
        department.setName("Computer Science");

        user = new User();
        user.setId(1L);
        user.setUsername("student1");
    }

    @Test
    @DisplayName("Should set and get all properties correctly")
    void testGettersAndSetters() {
        // Act
        student.setId(1L);
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("john.doe@test.com");
        student.setStudentId("STU001");
        student.setPhone("1234567890");
        student.setDepartment(department);
        student.setUser(user);

        // Assert
        assertEquals(1L, student.getId());
        assertEquals("John", student.getFirstName());
        assertEquals("Doe", student.getLastName());
        assertEquals("john.doe@test.com", student.getEmail());
        assertEquals("STU001", student.getStudentId());
        assertEquals("1234567890", student.getPhone());
        assertEquals(department, student.getDepartment());
        assertEquals(user, student.getUser());
    }

    @Test
    @DisplayName("Should handle null values")
    void testNullValues() {
        // Act
        student.setDepartment(null);
        student.setUser(null);
        student.setPhone(null);

        // Assert
        assertNull(student.getDepartment());
        assertNull(student.getUser());
        assertNull(student.getPhone());
    }

    @Test
    @DisplayName("Should initialize with empty courses list")
    void testCoursesInitialization() {
        // Assert
        assertNotNull(student.getCourses());
        assertTrue(student.getCourses().isEmpty());
    }
}
