package com.sheikhgalib.store.service;

import com.sheikhgalib.store.entity.Department;
import com.sheikhgalib.store.entity.Student;
import com.sheikhgalib.store.repository.StudentRepository;
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
 * Unit tests for StudentService
 * Uses Mockito to mock the repository layer
 */
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student1;
    private Student student2;
    private Department department;

    @BeforeEach
    void setUp() {
        // Create test department
        department = new Department();
        department.setId(1L);
        department.setName("Computer Science");
        department.setDescription("CS Department");

        // Create test students
        student1 = new Student();
        student1.setId(1L);
        student1.setFirstName("John");
        student1.setLastName("Doe");
        student1.setEmail("john.doe@test.com");
        student1.setStudentId("STU001");
        student1.setPhone("1234567890");
        student1.setDepartment(department);

        student2 = new Student();
        student2.setId(2L);
        student2.setFirstName("Jane");
        student2.setLastName("Smith");
        student2.setEmail("jane.smith@test.com");
        student2.setStudentId("STU002");
        student2.setPhone("0987654321");
        student2.setDepartment(department);
    }

    @Test
    @DisplayName("Should return all students")
    void getAllStudents_ReturnsAllStudents() {
        // Arrange
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));

        // Act
        List<Student> result = studentService.getAllStudents();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no students exist")
    void getAllStudents_ReturnsEmptyList() {
        // Arrange
        when(studentRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<Student> result = studentService.getAllStudents();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return student by ID")
    void getStudentById_ExistingId_ReturnsStudent() {
        // Arrange
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student1));

        // Act
        Optional<Student> result = studentService.getStudentById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
        assertEquals("john.doe@test.com", result.get().getEmail());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty optional for non-existing ID")
    void getStudentById_NonExistingId_ReturnsEmpty() {
        // Arrange
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Student> result = studentService.getStudentById(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(studentRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should save student successfully")
    void saveStudent_ValidStudent_ReturnsSavedStudent() {
        // Arrange
        Student newStudent = new Student();
        newStudent.setFirstName("Bob");
        newStudent.setLastName("Wilson");
        newStudent.setEmail("bob.wilson@test.com");
        newStudent.setStudentId("STU003");

        Student savedStudent = new Student();
        savedStudent.setId(3L);
        savedStudent.setFirstName("Bob");
        savedStudent.setLastName("Wilson");
        savedStudent.setEmail("bob.wilson@test.com");
        savedStudent.setStudentId("STU003");

        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        // Act
        Student result = studentService.saveStudent(newStudent);

        // Assert
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("Bob", result.getFirstName());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    @DisplayName("Should delete student successfully")
    void deleteStudent_ExistingId_DeletesStudent() {
        // Arrange
        doNothing().when(studentRepository).deleteById(1L);

        // Act
        studentService.deleteStudent(1L);

        // Assert
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should return students by department ID")
    void getStudentsByDepartment_ExistingDepartment_ReturnsStudents() {
        // Arrange
        when(studentRepository.findByDepartmentId(1L)).thenReturn(Arrays.asList(student1, student2));

        // Act
        List<Student> result = studentService.getStudentsByDepartment(1L);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(studentRepository, times(1)).findByDepartmentId(1L);
    }

    @Test
    @DisplayName("Should return empty list for department with no students")
    void getStudentsByDepartment_NoStudents_ReturnsEmptyList() {
        // Arrange
        when(studentRepository.findByDepartmentId(999L)).thenReturn(Arrays.asList());

        // Act
        List<Student> result = studentService.getStudentsByDepartment(999L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(studentRepository, times(1)).findByDepartmentId(999L);
    }
}
