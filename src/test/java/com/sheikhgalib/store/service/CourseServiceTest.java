package com.sheikhgalib.store.service;

import com.sheikhgalib.store.entity.Course;
import com.sheikhgalib.store.entity.Department;
import com.sheikhgalib.store.entity.Teacher;
import com.sheikhgalib.store.repository.CourseRepository;
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
 * Unit tests for CourseService
 * Uses Mockito to mock the repository layer
 */
@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    private Course course1;
    private Course course2;
    private Department department;
    private Teacher teacher;

    @BeforeEach
    void setUp() {
        // Create test department
        department = new Department();
        department.setId(1L);
        department.setName("Computer Science");
        department.setDescription("CS Department");

        // Create test teacher
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("Dr. John");
        teacher.setLastName("Smith");
        teacher.setEmail("john.smith@test.com");
        teacher.setEmployeeId("EMP001");
        teacher.setDepartment(department);

        // Create test courses
        course1 = new Course();
        course1.setId(1L);
        course1.setName("Introduction to Programming");
        course1.setCourseCode("CS101");
        course1.setDescription("Basic programming concepts");
        course1.setCredits(3);
        course1.setDepartment(department);
        course1.setTeacher(teacher);

        course2 = new Course();
        course2.setId(2L);
        course2.setName("Data Structures");
        course2.setCourseCode("CS201");
        course2.setDescription("Data structures and algorithms");
        course2.setCredits(4);
        course2.setDepartment(department);
        course2.setTeacher(teacher);
    }

    @Test
    @DisplayName("Should return all courses")
    void getAllCourses_ReturnsAllCourses() {
        // Arrange
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course1, course2));

        // Act
        List<Course> result = courseService.getAllCourses();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Introduction to Programming", result.get(0).getName());
        assertEquals("Data Structures", result.get(1).getName());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no courses exist")
    void getAllCourses_ReturnsEmptyList() {
        // Arrange
        when(courseRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<Course> result = courseService.getAllCourses();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return course by ID")
    void getCourseById_ExistingId_ReturnsCourse() {
        // Arrange
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course1));

        // Act
        Optional<Course> result = courseService.getCourseById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Introduction to Programming", result.get().getName());
        assertEquals("CS101", result.get().getCourseCode());
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty optional for non-existing ID")
    void getCourseById_NonExistingId_ReturnsEmpty() {
        // Arrange
        when(courseRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Course> result = courseService.getCourseById(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(courseRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should save course successfully")
    void saveCourse_ValidCourse_ReturnsSavedCourse() {
        // Arrange
        Course newCourse = new Course();
        newCourse.setName("Database Systems");
        newCourse.setCourseCode("CS301");
        newCourse.setDescription("Introduction to databases");
        newCourse.setCredits(3);

        Course savedCourse = new Course();
        savedCourse.setId(3L);
        savedCourse.setName("Database Systems");
        savedCourse.setCourseCode("CS301");
        savedCourse.setDescription("Introduction to databases");
        savedCourse.setCredits(3);

        when(courseRepository.save(any(Course.class))).thenReturn(savedCourse);

        // Act
        Course result = courseService.saveCourse(newCourse);

        // Assert
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("Database Systems", result.getName());
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    @DisplayName("Should delete course successfully")
    void deleteCourse_ExistingId_DeletesCourse() {
        // Arrange
        doNothing().when(courseRepository).deleteById(1L);

        // Act
        courseService.deleteCourse(1L);

        // Assert
        verify(courseRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should return courses by department ID")
    void getCoursesByDepartment_ExistingDepartment_ReturnsCourses() {
        // Arrange
        when(courseRepository.findByDepartmentId(1L)).thenReturn(Arrays.asList(course1, course2));

        // Act
        List<Course> result = courseService.getCoursesByDepartment(1L);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(courseRepository, times(1)).findByDepartmentId(1L);
    }

    @Test
    @DisplayName("Should return courses by teacher ID")
    void getCoursesByTeacher_ExistingTeacher_ReturnsCourses() {
        // Arrange
        when(courseRepository.findByTeacherId(1L)).thenReturn(Arrays.asList(course1, course2));

        // Act
        List<Course> result = courseService.getCoursesByTeacher(1L);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(courseRepository, times(1)).findByTeacherId(1L);
    }

    @Test
    @DisplayName("Should return empty list for teacher with no courses")
    void getCoursesByTeacher_NoCourses_ReturnsEmptyList() {
        // Arrange
        when(courseRepository.findByTeacherId(999L)).thenReturn(Arrays.asList());

        // Act
        List<Course> result = courseService.getCoursesByTeacher(999L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(courseRepository, times(1)).findByTeacherId(999L);
    }
}
