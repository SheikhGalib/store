package com.sheikhgalib.store.repository;

import com.sheikhgalib.store.entity.Department;
import com.sheikhgalib.store.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Repository tests for StudentRepository
 * Tests JPA repository methods with H2 database
 */
@DataJpaTest
@ActiveProfiles("test")
class StudentRepositoryTest {

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
        department.setName("Test Department");
        department.setDescription("Test Description");
        department = departmentRepository.save(department);
    }

    @Test
    @DisplayName("Should find student by email")
    void findByEmail_ExistingEmail_ReturnsStudent() {
        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("john.doe@test.com");
        student.setStudentId("STU001");
        student.setDepartment(department);
        studentRepository.save(student);

        Optional<Student> found = studentRepository.findByEmail("john.doe@test.com");

        assertTrue(found.isPresent());
        assertEquals("John", found.get().getFirstName());
    }

    @Test
    @DisplayName("Should return empty for non-existing email")
    void findByEmail_NonExistingEmail_ReturnsEmpty() {
        Optional<Student> found = studentRepository.findByEmail("nonexistent@test.com");
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Should find student by student ID")
    void findByStudentId_ExistingId_ReturnsStudent() {
        Student student = new Student();
        student.setFirstName("Jane");
        student.setLastName("Smith");
        student.setEmail("jane.smith@test.com");
        student.setStudentId("STU002");
        student.setDepartment(department);
        studentRepository.save(student);

        Optional<Student> found = studentRepository.findByStudentId("STU002");

        assertTrue(found.isPresent());
        assertEquals("Jane", found.get().getFirstName());
    }

    @Test
    @DisplayName("Should find students by department ID")
    void findByDepartmentId_ExistingDepartment_ReturnsStudents() {
        Student student1 = new Student();
        student1.setFirstName("Student1");
        student1.setLastName("Test");
        student1.setEmail("student1@test.com");
        student1.setStudentId("STU001");
        student1.setDepartment(department);
        studentRepository.save(student1);

        Student student2 = new Student();
        student2.setFirstName("Student2");
        student2.setLastName("Test");
        student2.setEmail("student2@test.com");
        student2.setStudentId("STU002");
        student2.setDepartment(department);
        studentRepository.save(student2);

        List<Student> students = studentRepository.findByDepartmentId(department.getId());

        assertEquals(2, students.size());
    }

    @Test
    @DisplayName("Should return empty list for department with no students")
    void findByDepartmentId_EmptyDepartment_ReturnsEmptyList() {
        Department emptyDept = new Department();
        emptyDept.setName("Empty Department");
        emptyDept.setDescription("No students");
        emptyDept = departmentRepository.save(emptyDept);

        List<Student> students = studentRepository.findByDepartmentId(emptyDept.getId());

        assertTrue(students.isEmpty());
    }

    @Test
    @DisplayName("Should save and retrieve student correctly")
    void saveAndRetrieve_ValidStudent_Success() {
        Student student = new Student();
        student.setFirstName("Test");
        student.setLastName("Student");
        student.setEmail("test@test.com");
        student.setStudentId("TEST001");
        student.setPhone("1234567890");
        student.setDepartment(department);

        Student saved = studentRepository.save(student);

        assertNotNull(saved.getId());

        Optional<Student> retrieved = studentRepository.findById(saved.getId());
        assertTrue(retrieved.isPresent());
        assertEquals("Test", retrieved.get().getFirstName());
        assertEquals(department.getId(), retrieved.get().getDepartment().getId());
    }

    @Test
    @DisplayName("Should delete student correctly")
    void delete_ExistingStudent_RemovesFromDatabase() {
        Student student = new Student();
        student.setFirstName("ToDelete");
        student.setLastName("Student");
        student.setEmail("delete@test.com");
        student.setStudentId("DEL001");
        student.setDepartment(department);
        Student saved = studentRepository.save(student);

        studentRepository.deleteById(saved.getId());

        Optional<Student> deleted = studentRepository.findById(saved.getId());
        assertFalse(deleted.isPresent());
    }
}
