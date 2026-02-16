package com.sheikhgalib.store.integration;

import com.sheikhgalib.store.entity.Department;
import com.sheikhgalib.store.entity.Student;
import com.sheikhgalib.store.repository.DepartmentRepository;
import com.sheikhgalib.store.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for StudentController
 * Tests the full Spring MVC stack with Spring Security
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class StudentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    private Department department;
    private Student student;

    @BeforeEach
    void setUp() {
        // Clear existing data
        studentRepository.deleteAll();
        departmentRepository.deleteAll();

        // Create test department
        department = new Department();
        department.setName("Test Department");
        department.setDescription("Test Description");
        department = departmentRepository.save(department);

        // Create test student
        student = new Student();
        student.setFirstName("Test");
        student.setLastName("Student");
        student.setEmail("test.student@test.com");
        student.setStudentId("TEST001");
        student.setPhone("1234567890");
        student.setDepartment(department);
        student = studentRepository.save(student);
    }

    @Test
    @DisplayName("Should redirect to login when accessing student list without authentication")
    void listStudents_Unauthenticated_RedirectsToLogin() throws Exception {
        mockMvc.perform(get("/student/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @DisplayName("Should display student list for authenticated student")
    @WithMockUser(roles = "STUDENT")
    void listStudents_AuthenticatedStudent_ReturnsStudentList() throws Exception {
        mockMvc.perform(get("/student/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/list"))
                .andExpect(model().attributeExists("students"));
    }

    @Test
    @DisplayName("Should display student list for authenticated teacher")
    @WithMockUser(roles = "TEACHER")
    void listStudents_AuthenticatedTeacher_ReturnsStudentList() throws Exception {
        mockMvc.perform(get("/student/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/list"));
    }

    @Test
    @DisplayName("Should view student details when authenticated")
    @WithMockUser(roles = "STUDENT")
    void viewStudent_AuthenticatedUser_ReturnsStudentDetails() throws Exception {
        mockMvc.perform(get("/student/view/" + student.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("student/view"))
                .andExpect(model().attributeExists("student"))
                .andExpect(model().attribute("student", hasProperty("firstName", is("Test"))));
    }

    @Test
    @DisplayName("Should show create form for teacher")
    @WithMockUser(roles = "TEACHER")
    void createStudentForm_Teacher_ShowsForm() throws Exception {
        mockMvc.perform(get("/student/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/form"))
                .andExpect(model().attributeExists("student"))
                .andExpect(model().attributeExists("departments"));
    }

    @Test
    @DisplayName("Should deny create form access for student role")
    @WithMockUser(roles = "STUDENT")
    void createStudentForm_Student_DeniesAccess() throws Exception {
        mockMvc.perform(get("/student/create"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should create student when teacher submits valid data")
    @WithMockUser(roles = "TEACHER")
    void createStudent_ValidData_CreatesStudent() throws Exception {
        mockMvc.perform(post("/student/create")
                        .with(csrf())
                        .param("firstName", "New")
                        .param("lastName", "Student")
                        .param("email", "new.student@test.com")
                        .param("studentId", "NEW001")
                        .param("phone", "9876543210"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/student/list"));
    }

    @Test
    @DisplayName("Should show edit form for teacher")
    @WithMockUser(roles = "TEACHER")
    void editStudentForm_Teacher_ShowsForm() throws Exception {
        mockMvc.perform(get("/student/edit/" + student.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("student/form"))
                .andExpect(model().attribute("student", hasProperty("firstName", is("Test"))));
    }

    @Test
    @DisplayName("Should deny edit form access for student role")
    @WithMockUser(roles = "STUDENT")
    void editStudentForm_Student_DeniesAccess() throws Exception {
        mockMvc.perform(get("/student/edit/" + student.getId()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should update student when teacher submits valid data")
    @WithMockUser(roles = "TEACHER")
    void editStudent_ValidData_UpdatesStudent() throws Exception {
        mockMvc.perform(post("/student/edit/" + student.getId())
                        .with(csrf())
                        .param("firstName", "Updated")
                        .param("lastName", "Student")
                        .param("email", "updated.student@test.com")
                        .param("studentId", "UPD001"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/student/list"));
    }

    @Test
    @DisplayName("Should delete student when teacher requests deletion")
    @WithMockUser(roles = "TEACHER")
    void deleteStudent_Teacher_DeletesStudent() throws Exception {
        mockMvc.perform(get("/student/delete/" + student.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/student/list"));
    }

    @Test
    @DisplayName("Should deny delete access for student role")
    @WithMockUser(roles = "STUDENT")
    void deleteStudent_Student_DeniesAccess() throws Exception {
        mockMvc.perform(get("/student/delete/" + student.getId()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Admin should have full access to student operations")
    @WithMockUser(roles = "ADMIN")
    void studentOperations_Admin_HasFullAccess() throws Exception {
        // Create
        mockMvc.perform(get("/student/create"))
                .andExpect(status().isOk());

        // Edit
        mockMvc.perform(get("/student/edit/" + student.getId()))
                .andExpect(status().isOk());

        // Delete
        mockMvc.perform(get("/student/delete/" + student.getId()))
                .andExpect(status().is3xxRedirection());
    }
}
