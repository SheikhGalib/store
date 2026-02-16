package com.sheikhgalib.store.service;

import com.sheikhgalib.store.entity.Department;
import com.sheikhgalib.store.entity.Teacher;
import com.sheikhgalib.store.repository.TeacherRepository;
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
 * Unit tests for TeacherService
 * Uses Mockito to mock the repository layer
 */
@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    private Teacher teacher1;
    private Teacher teacher2;
    private Department department;

    @BeforeEach
    void setUp() {
        // Create test department
        department = new Department();
        department.setId(1L);
        department.setName("Computer Science");
        department.setDescription("CS Department");

        // Create test teachers
        teacher1 = new Teacher();
        teacher1.setId(1L);
        teacher1.setFirstName("Dr. John");
        teacher1.setLastName("Smith");
        teacher1.setEmail("john.smith@test.com");
        teacher1.setEmployeeId("EMP001");
        teacher1.setPhone("1234567890");
        teacher1.setDepartment(department);

        teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setFirstName("Dr. Jane");
        teacher2.setLastName("Doe");
        teacher2.setEmail("jane.doe@test.com");
        teacher2.setEmployeeId("EMP002");
        teacher2.setPhone("0987654321");
        teacher2.setDepartment(department);
    }

    @Test
    @DisplayName("Should return all teachers")
    void getAllTeachers_ReturnsAllTeachers() {
        // Arrange
        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher1, teacher2));

        // Act
        List<Teacher> result = teacherService.getAllTeachers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Dr. John", result.get(0).getFirstName());
        assertEquals("Dr. Jane", result.get(1).getFirstName());
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no teachers exist")
    void getAllTeachers_ReturnsEmptyList() {
        // Arrange
        when(teacherRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<Teacher> result = teacherService.getAllTeachers();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return teacher by ID")
    void getTeacherById_ExistingId_ReturnsTeacher() {
        // Arrange
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher1));

        // Act
        Optional<Teacher> result = teacherService.getTeacherById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Dr. John", result.get().getFirstName());
        assertEquals("john.smith@test.com", result.get().getEmail());
        verify(teacherRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty optional for non-existing ID")
    void getTeacherById_NonExistingId_ReturnsEmpty() {
        // Arrange
        when(teacherRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Teacher> result = teacherService.getTeacherById(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(teacherRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should save teacher successfully")
    void saveTeacher_ValidTeacher_ReturnsSavedTeacher() {
        // Arrange
        Teacher newTeacher = new Teacher();
        newTeacher.setFirstName("Dr. Bob");
        newTeacher.setLastName("Wilson");
        newTeacher.setEmail("bob.wilson@test.com");
        newTeacher.setEmployeeId("EMP003");

        Teacher savedTeacher = new Teacher();
        savedTeacher.setId(3L);
        savedTeacher.setFirstName("Dr. Bob");
        savedTeacher.setLastName("Wilson");
        savedTeacher.setEmail("bob.wilson@test.com");
        savedTeacher.setEmployeeId("EMP003");

        when(teacherRepository.save(any(Teacher.class))).thenReturn(savedTeacher);

        // Act
        Teacher result = teacherService.saveTeacher(newTeacher);

        // Assert
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("Dr. Bob", result.getFirstName());
        verify(teacherRepository, times(1)).save(any(Teacher.class));
    }

    @Test
    @DisplayName("Should delete teacher successfully")
    void deleteTeacher_ExistingId_DeletesTeacher() {
        // Arrange
        doNothing().when(teacherRepository).deleteById(1L);

        // Act
        teacherService.deleteTeacher(1L);

        // Assert
        verify(teacherRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should return teachers by department ID")
    void getTeachersByDepartment_ExistingDepartment_ReturnsTeachers() {
        // Arrange
        when(teacherRepository.findByDepartmentId(1L)).thenReturn(Arrays.asList(teacher1, teacher2));

        // Act
        List<Teacher> result = teacherService.getTeachersByDepartment(1L);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(teacherRepository, times(1)).findByDepartmentId(1L);
    }

    @Test
    @DisplayName("Should return empty list for department with no teachers")
    void getTeachersByDepartment_NoTeachers_ReturnsEmptyList() {
        // Arrange
        when(teacherRepository.findByDepartmentId(999L)).thenReturn(Arrays.asList());

        // Act
        List<Teacher> result = teacherService.getTeachersByDepartment(999L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(teacherRepository, times(1)).findByDepartmentId(999L);
    }
}
