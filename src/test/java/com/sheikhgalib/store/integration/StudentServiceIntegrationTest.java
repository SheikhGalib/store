package com.sheikhgalib.store.integration;

import com.sheikhgalib.store.entity.Department;
import com.sheikhgalib.store.entity.Student;
import com.sheikhgalib.store.repository.DepartmentRepository;
import com.sheikhgalib.store.repository.StudentRepository;
import com.sheikhgalib.store.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for StudentService
 * Tests service layer with actual database operations
 */
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class StudentServiceIntegrationTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    private Department department;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
        departmentRepository.deleteAll();

        department = new Department();
        department.setName("Integration Test Department");
        department.setDescription("Test Department for Integration Tests");
        department = departmentRepository.save(department);
    }

    @Test
    @DisplayName("Should save and retrieve student from database")
    void saveAndRetrieveStudent() {
        // Create student
        Student student = new Student();
        student.setFirstName("Integration");
        student.setLastName("Test");
        student.setEmail("integration.test@test.com");
        student.setStudentId("INT001");
        student.setPhone("1234567890");
        student.setDepartment(department);

        // Save
        Student saved = studentService.saveStudent(student);
        assertNotNull(saved.getId());

        // Retrieve
        Optional<Student> retrieved = studentService.getStudentById(saved.getId());
        assertTrue(retrieved.isPresent());
        assertEquals("Integration", retrieved.get().getFirstName());
        assertEquals("integration.test@test.com", retrieved.get().getEmail());
    }

    @Test
    @DisplayName("Should get all students from database")
    void getAllStudents() {
        // Create multiple students
        Student student1 = new Student();
        student1.setFirstName("Student1");
        student1.setLastName("Test");
        student1.setEmail("student1@test.com");
        student1.setStudentId("STU001");
        student1.setDepartment(department);
        studentService.saveStudent(student1);

        Student student2 = new Student();
        student2.setFirstName("Student2");
        student2.setLastName("Test");
        student2.setEmail("student2@test.com");
        student2.setStudentId("STU002");
        student2.setDepartment(department);
        studentService.saveStudent(student2);

        // Get all
        List<Student> students = studentService.getAllStudents();
        assertEquals(2, students.size());
    }

    @Test
    @DisplayName("Should delete student from database")
    void deleteStudent() {
        // Create student
        Student student = new Student();
        student.setFirstName("ToDelete");
        student.setLastName("Test");
        student.setEmail("delete@test.com");
        student.setStudentId("DEL001");
        student.setDepartment(department);
        Student saved = studentService.saveStudent(student);

        // Delete
        studentService.deleteStudent(saved.getId());

        // Verify deletion
        Optional<Student> retrieved = studentService.getStudentById(saved.getId());
        assertFalse(retrieved.isPresent());
    }

    @Test
    @DisplayName("Should find students by department")
    void getStudentsByDepartment() {
        // Create another department
        Department anotherDept = new Department();
        anotherDept.setName("Another Department");
        anotherDept.setDescription("Another Test Department");
        anotherDept = departmentRepository.save(anotherDept);

        // Create students in different departments
        Student student1 = new Student();
        student1.setFirstName("Dept1Student");
        student1.setLastName("Test");
        student1.setEmail("dept1@test.com");
        student1.setStudentId("D1S001");
        student1.setDepartment(department);
        studentService.saveStudent(student1);

        Student student2 = new Student();
        student2.setFirstName("Dept2Student");
        student2.setLastName("Test");
        student2.setEmail("dept2@test.com");
        student2.setStudentId("D2S001");
        student2.setDepartment(anotherDept);
        studentService.saveStudent(student2);

        // Find by department
        List<Student> deptStudents = studentService.getStudentsByDepartment(department.getId());
        assertEquals(1, deptStudents.size());
        assertEquals("Dept1Student", deptStudents.get(0).getFirstName());
    }

    @Test
    @DisplayName("Should update student in database")
    void updateStudent() {
        // Create student
        Student student = new Student();
        student.setFirstName("Original");
        student.setLastName("Name");
        student.setEmail("original@test.com");
        student.setStudentId("ORG001");
        student.setDepartment(department);
        Student saved = studentService.saveStudent(student);

        // Update
        saved.setFirstName("Updated");
        saved.setEmail("updated@test.com");
        studentService.saveStudent(saved);

        // Verify update
        Optional<Student> retrieved = studentService.getStudentById(saved.getId());
        assertTrue(retrieved.isPresent());
        assertEquals("Updated", retrieved.get().getFirstName());
        assertEquals("updated@test.com", retrieved.get().getEmail());
    }
}
