package com.sheikhgalib.store.service;

import com.sheikhgalib.store.entity.Department;
import com.sheikhgalib.store.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for DepartmentService
 * Uses Mockito to mock the repository layer
 */
@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department1;
    private Department department2;

    @BeforeEach
    void setUp() {
        department1 = new Department();
        department1.setId(1L);
        department1.setName("Computer Science");
        department1.setDescription("Department of Computer Science and Engineering");

        department2 = new Department();
        department2.setId(2L);
        department2.setName("Mathematics");
        department2.setDescription("Department of Mathematics");
    }

    @Test
    @DisplayName("Should return all departments")
    void getAllDepartments_ReturnsAllDepartments() {
        // Arrange
        when(departmentRepository.findAll()).thenReturn(Arrays.asList(department1, department2));

        // Act
        List<Department> result = departmentService.getAllDepartments();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Computer Science", result.get(0).getName());
        assertEquals("Mathematics", result.get(1).getName());
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no departments exist")
    void getAllDepartments_ReturnsEmptyList() {
        // Arrange
        when(departmentRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<Department> result = departmentService.getAllDepartments();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return department by ID")
    void getDepartmentById_ExistingId_ReturnsDepartment() {
        // Arrange
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department1));

        // Act
        Optional<Department> result = departmentService.getDepartmentById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Computer Science", result.get().getName());
        verify(departmentRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty optional for non-existing ID")
    void getDepartmentById_NonExistingId_ReturnsEmpty() {
        // Arrange
        when(departmentRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Department> result = departmentService.getDepartmentById(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(departmentRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should save department successfully")
    void saveDepartment_ValidDepartment_ReturnsSavedDepartment() {
        // Arrange
        Department newDepartment = new Department();
        newDepartment.setName("Physics");
        newDepartment.setDescription("Department of Physics");

        Department savedDepartment = new Department();
        savedDepartment.setId(3L);
        savedDepartment.setName("Physics");
        savedDepartment.setDescription("Department of Physics");

        when(departmentRepository.save(any(Department.class))).thenReturn(savedDepartment);

        // Act
        Department result = departmentService.saveDepartment(newDepartment);

        // Assert
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("Physics", result.getName());
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    @DisplayName("Should delete department successfully")
    void deleteDepartment_ExistingId_DeletesDepartment() {
        // Arrange
        doNothing().when(departmentRepository).deleteById(1L);

        // Act
        departmentService.deleteDepartment(1L);

        // Assert
        verify(departmentRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should update department successfully")
    void saveDepartment_ExistingDepartment_UpdatesDepartment() {
        // Arrange
        department1.setDescription("Updated Description");
        when(departmentRepository.save(any(Department.class))).thenReturn(department1);

        // Act
        Department result = departmentService.saveDepartment(department1);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Description", result.getDescription());
        verify(departmentRepository, times(1)).save(department1);
    }
}
