package com.sheikhgalib.store;

import com.sheikhgalib.store.entity.*;
import com.sheikhgalib.store.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create users if they don't exist
        if (userRepository.count() == 0) {
            // Create admin user
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRoles(Set.of("ROLE_ADMIN"));
            userRepository.save(admin);

            // Create teacher user
            User teacherUser = new User();
            teacherUser.setUsername("teacher1");
            teacherUser.setEmail("teacher1@example.com");
            teacherUser.setPassword(passwordEncoder.encode("teacher123"));
            teacherUser.setRoles(Set.of("ROLE_TEACHER"));
            userRepository.save(teacherUser);

            // Create student user
            User studentUser = new User();
            studentUser.setUsername("student1");
            studentUser.setEmail("student1@example.com");
            studentUser.setPassword(passwordEncoder.encode("student123"));
            studentUser.setRoles(Set.of("ROLE_STUDENT"));
            userRepository.save(studentUser);

            System.out.println("===========================================");
            System.out.println("Sample users created:");
            System.out.println("Admin - Username: admin, Password: admin123");
            System.out.println("Teacher - Username: teacher1, Password: teacher123");
            System.out.println("Student - Username: student1, Password: student123");
            System.out.println("===========================================");
        }

        // Create sample departments
        if (departmentRepository.count() == 0) {
            Department csDept = new Department();
            csDept.setName("Computer Science");
            csDept.setDescription("Department of Computer Science and Engineering");
            departmentRepository.save(csDept);

            Department mathDept = new Department();
            mathDept.setName("Mathematics");
            mathDept.setDescription("Department of Mathematics");
            departmentRepository.save(mathDept);

            Department physicsDept = new Department();
            physicsDept.setName("Physics");
            physicsDept.setDescription("Department of Physics");
            departmentRepository.save(physicsDept);

            System.out.println("Sample departments created!");
        }

        // Create sample teachers
        if (teacherRepository.count() == 0) {
            Department csDept = departmentRepository.findByName("Computer Science").orElse(null);
            if (csDept != null) {
                Teacher teacher = new Teacher();
                teacher.setFirstName("John");
                teacher.setLastName("Smith");
                teacher.setEmail("john.smith@example.com");
                teacher.setEmployeeId("T001");
                teacher.setPhone("1234567890");
                teacher.setDepartment(csDept);
                teacherRepository.save(teacher);

                System.out.println("Sample teacher created!");
            }
        }

        // Create sample courses
        if (courseRepository.count() == 0) {
            Department csDept = departmentRepository.findByName("Computer Science").orElse(null);
            Teacher teacher = teacherRepository.findByEmployeeId("T001").orElse(null);

            if (csDept != null && teacher != null) {
                Course course1 = new Course();
                course1.setName("Data Structures");
                course1.setCourseCode("CS101");
                course1.setDescription("Introduction to Data Structures");
                course1.setCredits(3);
                course1.setDepartment(csDept);
                course1.setTeacher(teacher);
                courseRepository.save(course1);

                Course course2 = new Course();
                course2.setName("Algorithms");
                course2.setCourseCode("CS102");
                course2.setDescription("Algorithm Design and Analysis");
                course2.setCredits(4);
                course2.setDepartment(csDept);
                course2.setTeacher(teacher);
                courseRepository.save(course2);

                System.out.println("Sample courses created!");
            }
        }

        // Create sample students
        if (studentRepository.count() == 0) {
            Department csDept = departmentRepository.findByName("Computer Science").orElse(null);
            if (csDept != null) {
                Student student = new Student();
                student.setFirstName("Alice");
                student.setLastName("Johnson");
                student.setEmail("alice.johnson@example.com");
                student.setStudentId("S001");
                student.setPhone("9876543210");
                student.setDepartment(csDept);
                studentRepository.save(student);

                System.out.println("Sample student created!");
            }
        }
    }
}
