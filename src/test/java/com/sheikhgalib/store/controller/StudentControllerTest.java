package com.sheikhgalib.store.controller;

import com.sheikhgalib.store.entity.Department;
import com.sheikhgalib.store.entity.Student;
import com.sheikhgalib.store.service.DepartmentService;
import com.sheikhgalib.store.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit tests for StudentController
 * Uses Mockito to mock the service layer
 */
@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @Mock
    private DepartmentService departmentService;

    @Mock
    private Model model;

    @InjectMocks
    private StudentController studentController;

    private Student student;
    private Department department;
    private List<Student> studentList;
    private List<Department> departmentList;

    @BeforeEach
    void setUp() {
        department = new Department();
        department.setId(1L);
        department.setName("Computer Science");
        department.setDescription("CS Department");

        student = new Student();
        student.setId(1L);
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("john.doe@test.com");
        student.setStudentId("STU001");
        student.setPhone("1234567890");
        student.setDepartment(department);

        Student student2 = new Student();
        student2.setId(2L);
        student2.setFirstName("Jane");
        student2.setLastName("Smith");
        student2.setEmail("jane.smith@test.com");
        student2.setStudentId("STU002");

        studentList = Arrays.asList(student, student2);
        departmentList = Arrays.asList(department);
    }

    @Test
    @DisplayName("Should list all students")
    void listStudents_ReturnsStudentListView() {
        // Arrange
        when(studentService.getAllStudents()).thenReturn(studentList);

        // Act
        String viewName = studentController.listStudents(model);

        // Assert
        assertEquals("student/list", viewName);
        verify(model).addAttribute(eq("students"), any(List.class));
        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    @DisplayName("Should view student details")
    void viewStudent_ExistingId_ReturnsStudentView() {
        // Arrange
        when(studentService.getStudentById(1L)).thenReturn(Optional.of(student));

        // Act
        String viewName = studentController.viewStudent(1L, model);

        // Assert
        assertEquals("student/view", viewName);
        verify(model).addAttribute(eq("student"), any(Student.class));
        verify(studentService, times(1)).getStudentById(1L);
    }

    @Test
    @DisplayName("Should throw exception when viewing non-existing student")
    void viewStudent_NonExistingId_ThrowsException() {
        // Arrange
        when(studentService.getStudentById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            studentController.viewStudent(999L, model);
        });
    }

    @Test
    @DisplayName("Should show create student form")
    void createStudentForm_ReturnsFormView() {
        // Arrange
        when(departmentService.getAllDepartments()).thenReturn(departmentList);

        // Act
        String viewName = studentController.createStudentForm(model);

        // Assert
        assertEquals("student/form", viewName);
        verify(model).addAttribute(eq("student"), any(Student.class));
        verify(model).addAttribute(eq("departments"), any(List.class));
    }

    @Test
    @DisplayName("Should create student and redirect")
    void createStudent_ValidStudent_RedirectsToList() {
        // Arrange
        when(studentService.saveStudent(any(Student.class))).thenReturn(student);

        // Act
        String viewName = studentController.createStudent(student);

        // Assert
        assertEquals("redirect:/student/list", viewName);
        verify(studentService, times(1)).saveStudent(any(Student.class));
    }

    @Test
    @DisplayName("Should show edit student form")
    void editStudentForm_ExistingId_ReturnsFormView() {
        // Arrange
        when(studentService.getStudentById(1L)).thenReturn(Optional.of(student));
        when(departmentService.getAllDepartments()).thenReturn(departmentList);

        // Act
        String viewName = studentController.editStudentForm(1L, model);

        // Assert
        assertEquals("student/form", viewName);
        verify(model).addAttribute(eq("student"), any(Student.class));
        verify(model).addAttribute(eq("departments"), any(List.class));
    }

    @Test
    @DisplayName("Should throw exception when editing non-existing student")
    void editStudentForm_NonExistingId_ThrowsException() {
        // Arrange
        when(studentService.getStudentById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            studentController.editStudentForm(999L, model);
        });
    }

    @Test
    @DisplayName("Should update student and redirect")
    void editStudent_ValidStudent_RedirectsToList() {
        // Arrange
        when(studentService.saveStudent(any(Student.class))).thenReturn(student);

        // Act
        String viewName = studentController.editStudent(1L, student);

        // Assert
        assertEquals("redirect:/student/list", viewName);
        assertEquals(1L, student.getId());
        verify(studentService, times(1)).saveStudent(any(Student.class));
    }

    @Test
    @DisplayName("Should delete student and redirect")
    void deleteStudent_ExistingId_RedirectsToList() {
        // Arrange
        doNothing().when(studentService).deleteStudent(1L);

        // Act
        String viewName = studentController.deleteStudent(1L);

        // Assert
        assertEquals("redirect:/student/list", viewName);
        verify(studentService, times(1)).deleteStudent(1L);
    }
}