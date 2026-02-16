# 📚 Student Management System - Complete Project Report

## 📋 Table of Contents
1. [Project Overview](#project-overview)
2. [Project Structure](#project-structure)
3. [Database Schema & Relationships](#database-schema--relationships)
4. [Technology Stack](#technology-stack)
5. [Spring Security Implementation](#spring-security-implementation)
6. [MVC Architecture Workflow](#mvc-architecture-workflow)
7. [Step-by-Step Workflow](#step-by-step-workflow)
8. [Building the Project from Scratch](#building-the-project-from-scratch)
9. [Running the Application](#running-the-application)

---

## 📋 Project Overview

**Project Name:** Student Management System  
**Type:** Web Application  
**Framework:** Spring Boot 3.2.0  
**Purpose:** Role-based student management with authentication and CRUD operations

### Key Features:
- ✅ User Authentication (Login/Register)
- ✅ Role-Based Access Control (Student, Teacher, Admin)
- ✅ CRUD Operations for Students, Teachers, Departments, and Courses
- ✅ Secure Session Management
- ✅ PostgreSQL Database Integration

---

## 🌳 Project Structure

```
D:\spring-boot-learning\store\
│
├── pom.xml                                    # Maven dependencies
├── mvnw, mvnw.cmd                             # Maven wrapper scripts
│
├── src/
│   ├── main/
│   │   ├── java/com/sheikhgalib/store/
│   │   │   │
│   │   │   ├── StoreApplication.java         # Main Spring Boot Application
│   │   │   ├── HomeController.java           # Root URL handler
│   │   │   ├── DataInitializer.java          # Sample data loader
│   │   │   │
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java       # Spring Security Configuration
│   │   │   │
│   │   │   ├── entity/                       # Database Models (JPA Entities)
│   │   │   │   ├── User.java                 # User authentication entity
│   │   │   │   ├── Student.java              # Student entity
│   │   │   │   ├── Teacher.java              # Teacher entity
│   │   │   │   ├── Department.java           # Department entity
│   │   │   │   └── Course.java               # Course entity
│   │   │   │
│   │   │   ├── repository/                   # Data Access Layer (JPA Repositories)
│   │   │   │   ├── UserRepository.java       # User data access
│   │   │   │   ├── StudentRepository.java    # Student data access
│   │   │   │   ├── TeacherRepository.java    # Teacher data access
│   │   │   │   ├── DepartmentRepository.java # Department data access
│   │   │   │   └── CourseRepository.java     # Course data access
│   │   │   │
│   │   │   ├── service/                      # Business Logic Layer
│   │   │   │   ├── StudentService.java       # Student business logic
│   │   │   │   ├── TeacherService.java       # Teacher business logic
│   │   │   │   ├── DepartmentService.java    # Department business logic
│   │   │   │   └── CourseService.java        # Course business logic
│   │   │   │
│   │   │   ├── security/
│   │   │   │   └── CustomUserDetailsService.java  # User authentication service
│   │   │   │
│   │   │   └── controller/                   # Presentation Layer (Request Handlers)
│   │   │       ├── AuthController.java       # Login/Register/Dashboard
│   │   │       ├── StudentController.java    # Student CRUD endpoints
│   │   │       ├── TeacherController.java    # Teacher CRUD endpoints
│   │   │       ├── DepartmentController.java # Department CRUD endpoints
│   │   │       └── CourseController.java     # Course CRUD endpoints
│   │   │
│   │   └── resources/
│   │       ├── application.properties        # Application configuration
│   │       │
│   │       ├── static/
│   │       │   └── index.html                # Static welcome page
│   │       │
│   │       └── templates/                    # Thymeleaf HTML Templates
│   │           ├── login.html                # Login page
│   │           ├── register.html             # Registration page
│   │           ├── dashboard.html            # Main dashboard
│   │           ├── access-denied.html        # Access denied page
│   │           │
│   │           ├── student/                  # Student management views
│   │           │   ├── list.html             # List all students
│   │           │   ├── form.html             # Create/Edit student
│   │           │   └── view.html             # View student details
│   │           │
│   │           ├── teacher/                  # Teacher management views
│   │           │   ├── list.html             # List all teachers
│   │           │   ├── form.html             # Create/Edit teacher
│   │           │   └── view.html             # View teacher details
│   │           │
│   │           ├── department/               # Department management views
│   │           │   ├── list.html             # List all departments
│   │           │   ├── form.html             # Create/Edit department
│   │           │   └── view.html             # View department details
│   │           │
│   │           └── course/                   # Course management views
│   │               ├── list.html             # List all courses
│   │               ├── form.html             # Create/Edit course
│   │               └── view.html             # View course details
│   │
│   └── test/
│       └── java/com/sheikhgalib/store/
│           └── StoreApplicationTests.java    # Unit tests
│
└── target/                                    # Compiled classes (generated)
```

---

## 🗄️ Database Schema & Relationships

### Database: `storeDB` (PostgreSQL)

### Tables Created:

#### 1. **users** (Authentication)
```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,        -- BCrypt encrypted
    email VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT true
);
```

#### 2. **user_roles** (User Roles)
```sql
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role VARCHAR(255),                     -- ROLE_STUDENT, ROLE_TEACHER, ROLE_ADMIN
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

#### 3. **departments** (Academic Departments)
```sql
CREATE TABLE departments (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    description VARCHAR(255)
);
```

#### 4. **students** (Student Information)
```sql
CREATE TABLE students (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    student_id VARCHAR(255) UNIQUE,
    phone VARCHAR(255),
    department_id BIGINT,
    user_id BIGINT UNIQUE,
    FOREIGN KEY (department_id) REFERENCES departments(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

#### 5. **teachers** (Teacher Information)
```sql
CREATE TABLE teachers (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    employee_id VARCHAR(255) UNIQUE,
    phone VARCHAR(255),
    department_id BIGINT,
    user_id BIGINT UNIQUE,
    FOREIGN KEY (department_id) REFERENCES departments(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

#### 6. **courses** (Course Information)
```sql
CREATE TABLE courses (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    course_code VARCHAR(255) UNIQUE NOT NULL,
    description VARCHAR(255),
    credits INTEGER,
    department_id BIGINT,
    teacher_id BIGINT,
    FOREIGN KEY (department_id) REFERENCES departments(id),
    FOREIGN KEY (teacher_id) REFERENCES teachers(id)
);
```

#### 7. **student_courses** (Many-to-Many Junction Table)
```sql
CREATE TABLE student_courses (
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);
```

### Entity Relationships Diagram:

```
┌─────────────┐
│   users     │
│  (id, ...)  │
└──────┬──────┘
       │ 1
       │
       ├──────────────────┬──────────────────┐
       │ 1:1              │ 1:1              │ 1:Many
       ▼                  ▼                  ▼
┌────────────┐    ┌────────────┐    ┌────────────┐
│  students  │    │  teachers  │    │ user_roles │
│  (id, ...) │    │  (id, ...) │    │ (role)     │
└─────┬──────┘    └─────┬──────┘    └────────────┘
      │                 │
      │ Many:1          │ Many:1
      │                 │
      │    ┌────────────┴─────────┐
      │    │                      │
      │    ▼ 1                    ▼ 1
      │  ┌──────────────┐    ┌──────────┐
      │  │ departments  │◄───┤ courses  │
      │  │  (id, ...)   │    │(id, ...) │
      │  └──────────────┘    └─────┬────┘
      │                             │
      │ Many                        │ 1:Many
      │                             │
      └──────────┬──────────────────┘
                 │ Many:Many
                 ▼
        ┌─────────────────┐
        │ student_courses │
        │  (junction)     │
        └─────────────────┘
```

### Relationship Summary:

| Relationship | Type | Description |
|-------------|------|-------------|
| User → Student | One-to-One | Each student has one user account |
| User → Teacher | One-to-One | Each teacher has one user account |
| User → Roles | One-to-Many | A user can have multiple roles |
| Department → Students | One-to-Many | A department has many students |
| Department → Teachers | One-to-Many | A department has many teachers |
| Department → Courses | One-to-Many | A department offers many courses |
| Teacher → Courses | One-to-Many | A teacher teaches many courses |
| Student ↔ Course | Many-to-Many | Students enroll in multiple courses |

---

## 🛠️ Technology Stack

### Backend:
- **Spring Boot 3.2.0** - Main framework
- **Spring Web** - RESTful web services
- **Spring Data JPA** - Database access layer
- **Spring Security 6** - Authentication & authorization
- **Hibernate** - ORM framework
- **PostgreSQL** - Relational database

### Frontend:
- **Thymeleaf** - Server-side template engine
- **HTML5 & CSS3** - Web pages and styling
- **Thymeleaf Spring Security Integration** - Security tags in templates

### Build Tool:
- **Maven** - Dependency management and build automation

### Development Tools:
- **Spring Boot DevTools** - Hot reload during development
- **Java 17** - Programming language

---

## 🔐 Spring Security Implementation

### Security Flow Diagram:

```
User Request
     ▼
┌────────────────────────────────┐
│  Spring Security Filter Chain  │
└────────────────────────────────┘
     │
     ├─── Is user authenticated? ──► No ──► Redirect to /login
     │                                │
     ▼ Yes                            ▼
Check Authorization              Login Page
     │                          (username, password)
     │                                │
     ├─── Has required role? ──► No ──┼──► Access Denied
     │                                │
     ▼ Yes                            ▼
Allow Access                   CustomUserDetailsService
     │                          (loads user from DB)
     ▼                                │
Controller Method                    ▼
     │                    DaoAuthenticationProvider
     ▼                      (validates credentials)
Service Layer                        │
     │                               ▼
     ▼                    Password Encoder (BCrypt)
Repository                           │
     │                               ▼
     ▼                    Authentication Success/Failure
Database                             │
                                     ▼
                              Redirect to Dashboard
```

### Key Security Files:

#### 1. **SecurityConfig.java** (Security Configuration)

```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    
    // Password encryption
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    // Authentication provider
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    // Security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        // URL-based authorization
        // Form login configuration
        // Logout configuration
        // Exception handling
    }
}
```

**What it does:**
1. **Password Encoding**: Uses BCrypt to hash passwords
2. **Authentication Provider**: Links UserDetailsService with password encoder
3. **Security Filter Chain**: Defines which URLs require authentication and authorization

**Security Rules:**
```java
.authorizeHttpRequests(auth -> auth
    .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()  // Public resources
    .requestMatchers("/register", "/login").permitAll()              // Public pages
    .requestMatchers("/admin/**").hasRole("ADMIN")                   // Admin only
    .requestMatchers("/teacher/**").hasAnyRole("TEACHER", "ADMIN")   // Teacher or Admin
    .requestMatchers("/student/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")  // Authenticated users
    .anyRequest().authenticated()                                    // Everything else requires login
)
```

#### 2. **CustomUserDetailsService.java** (User Authentication)

```java
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) {
        // 1. Load user from database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        // 2. Convert roles to authorities
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        
        // 3. Return Spring Security UserDetails object
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
```

**What it does:**
1. Implements Spring Security's `UserDetailsService`
2. Loads user from database by username
3. Converts user roles to Spring Security authorities
4. Returns a `UserDetails` object that Spring Security understands

**Flow:**
```
Login Form (username, password)
        ▼
Spring Security Authentication Filter
        ▼
CustomUserDetailsService.loadUserByUsername(username)
        ▼
UserRepository.findByUsername(username)
        ▼
Database Query
        ▼
User Entity Retrieved
        ▼
Convert to UserDetails
        ▼
Password Verification (BCrypt)
        ▼
Authentication Success/Failure
```

---

## 🏗️ MVC Architecture Workflow

### Model-View-Controller Pattern:

```
┌─────────────────────────────────────────────────────────────┐
│                      MVC ARCHITECTURE                        │
└─────────────────────────────────────────────────────────────┘

Browser Request (HTTP)
       │
       ▼
┌──────────────────────┐
│    CONTROLLER        │  ◄─── Request Mapping (@GetMapping, @PostMapping)
│  (Presentation)      │
│                      │
│ AuthController       │
│ StudentController    │
│ TeacherController    │
│ etc.                 │
└──────┬───────────────┘
       │
       ▼
┌──────────────────────┐
│     SERVICE          │  ◄─── Business Logic (@Service)
│  (Business Logic)    │
│                      │
│ StudentService       │
│ TeacherService       │
│ etc.                 │
└──────┬───────────────┘
       │
       ▼
┌──────────────────────┐
│    REPOSITORY        │  ◄─── Data Access (@Repository)
│  (Data Access)       │
│                      │
│ StudentRepository    │
│ TeacherRepository    │
│ etc.                 │
└──────┬───────────────┘
       │
       ▼
┌──────────────────────┐
│      MODEL           │  ◄─── Database Entities (@Entity)
│   (Data Model)       │
│                      │
│ Student.java         │
│ Teacher.java         │
│ etc.                 │
└──────┬───────────────┘
       │
       ▼
┌──────────────────────┐
│     DATABASE         │
│   (PostgreSQL)       │
│                      │
│  storeDB             │
└──────────────────────┘
       │
       ▼
    Response
       │
       ▼
┌──────────────────────┐
│       VIEW           │  ◄─── HTML Templates (Thymeleaf)
│   (Presentation)     │
│                      │
│ list.html            │
│ form.html            │
│ view.html            │
└──────────────────────┘
       │
       ▼
   Browser Display
```

### Layer Responsibilities:

#### 1. **Model Layer** (Entity Package)
- **Files**: `Student.java`, `Teacher.java`, `Department.java`, `Course.java`, `User.java`
- **Purpose**: Represent database tables as Java objects
- **Annotations**: `@Entity`, `@Table`, `@Id`, `@Column`, `@ManyToOne`, etc.

**Example (Student.java):**
```java
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String firstName;
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    
    // Getters and setters
}
```

#### 2. **Repository Layer** (Repository Package)
- **Files**: `StudentRepository.java`, `TeacherRepository.java`, etc.
- **Purpose**: Database operations (CRUD)
- **Extends**: `JpaRepository<Entity, ID>`

**Example (StudentRepository.java):**
```java
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
    List<Student> findByDepartmentId(Long departmentId);
}
```

**Generated SQL:**
- `findAll()` → `SELECT * FROM students`
- `findById(id)` → `SELECT * FROM students WHERE id = ?`
- `save(student)` → `INSERT INTO students ...` or `UPDATE students ...`
- `deleteById(id)` → `DELETE FROM students WHERE id = ?`

#### 3. **Service Layer** (Service Package)
- **Files**: `StudentService.java`, `TeacherService.java`, etc.
- **Purpose**: Business logic, transaction management
- **Annotations**: `@Service`, `@Transactional`

**Example (StudentService.java):**
```java
@Service
@Transactional
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }
    
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
```

#### 4. **Controller Layer** (Controller Package)
- **Files**: `StudentController.java`, `TeacherController.java`, etc.
- **Purpose**: Handle HTTP requests, return views
- **Annotations**: `@Controller`, `@GetMapping`, `@PostMapping`

**Example (StudentController.java):**
```java
@Controller
@RequestMapping("/student")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    @GetMapping("/list")
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "student/list";  // Returns student/list.html
    }
    
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @PostMapping("/create")
    public String createStudent(@ModelAttribute Student student) {
        studentService.saveStudent(student);
        return "redirect:/student/list";
    }
}
```

#### 5. **View Layer** (Templates Package)
- **Files**: All HTML files in `templates/` folder
- **Purpose**: Display data to user
- **Engine**: Thymeleaf

**Example (student/list.html):**
```html
<table>
    <tr th:each="student : ${students}">
        <td th:text="${student.id}">1</td>
        <td th:text="${student.firstName}">John</td>
    </tr>
</table>
```

---

## 🔄 Step-by-Step Workflow

### 1. Application Startup Flow

```
1. JVM starts
   ▼
2. StoreApplication.main() executes
   ▼
3. SpringApplication.run() initializes Spring context
   ▼
4. Auto-configuration runs
   │
   ├─► DataSource configured (PostgreSQL connection)
   ├─► JPA/Hibernate initialized
   ├─► Spring Security filters created
   ├─► Component scanning (@Controller, @Service, @Repository)
   └─► Thymeleaf template engine configured
   ▼
5. Database schema created/updated (ddl-auto=update)
   ▼
6. DataInitializer.run() executes
   │
   ├─► Checks if users exist
   ├─► Creates sample users (admin, teacher1, student1)
   ├─► Creates sample departments
   ├─► Creates sample teachers
   ├─► Creates sample courses
   └─► Creates sample students
   ▼
7. Tomcat server starts on port 8081
   ▼
8. Application ready to accept requests
```

**Files Involved:**
1. `StoreApplication.java` - Entry point
2. `application.properties` - Configuration
3. `SecurityConfig.java` - Security setup
4. `DataInitializer.java` - Sample data
5. All `@Entity` classes - Table creation
6. All `@Repository` interfaces - Data access setup

---

### 2. Login/Register Workflow

#### **Registration Flow:**

```
User fills registration form (register.html)
        ▼
POST /register
        ▼
AuthController.register()
        │
        ├─► Check if username exists (UserRepository.existsByUsername)
        ├─► Check if email exists (UserRepository.existsByEmail)
        │
        ▼ Validation passed
        │
        ├─► Create new User object
        ├─► Encrypt password (BCryptPasswordEncoder)
        ├─► Set roles (ROLE_STUDENT or ROLE_TEACHER)
        ├─► Save to database (UserRepository.save)
        │
        ▼
Redirect to /login?registered=true
        ▼
Show success message
```

**Files Involved:**
1. `register.html` - Registration form
2. `AuthController.java` - Handle registration
3. `User.java` - User entity
4. `UserRepository.java` - Save user to DB
5. `SecurityConfig.java` - Password encryption

**Code Flow in AuthController.java:**
```java
@PostMapping("/register")
public String register(
    @RequestParam String username,
    @RequestParam String email,
    @RequestParam String password,
    @RequestParam String role,
    Model model
) {
    // 1. Validation
    if (userRepository.existsByUsername(username)) {
        model.addAttribute("error", "Username already exists");
        return "register";
    }
    
    // 2. Create user
    User user = new User();
    user.setUsername(username);
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(password));  // Encrypt password
    user.setRoles(Set.of("ROLE_" + role.toUpperCase()));
    
    // 3. Save to database
    userRepository.save(user);
    
    // 4. Redirect to login
    return "redirect:/login?registered=true";
}
```

#### **Login Flow:**

```
User visits http://localhost:8081
        ▼
HomeController.index() → Redirect to /dashboard
        ▼
SecurityConfig intercepts (user not authenticated)
        ▼
Redirect to /login
        ▼
User fills login form (login.html)
        ▼
POST /login (Spring Security handles this automatically)
        ▼
UsernamePasswordAuthenticationFilter intercepts
        ▼
CustomUserDetailsService.loadUserByUsername(username)
        │
        ├─► UserRepository.findByUsername(username)
        ├─► Load user from database
        ├─► Load user roles
        │
        ▼
DaoAuthenticationProvider validates
        │
        ├─► Compare passwords using BCrypt
        │
        ▼ Password matches
        │
Authentication Success
        │
        ├─► Create SecurityContext
        ├─► Store in session
        │
        ▼
Redirect to /dashboard (defaultSuccessUrl)
        ▼
AuthController.dashboard()
        │
        ├─► Get authenticated username (Principal)
        ├─► Add to model
        │
        ▼
Render dashboard.html
        ▼
Show role-based menu (Thymeleaf sec:authorize)
```

**Files Involved:**
1. `login.html` - Login form
2. `SecurityConfig.java` - Form login configuration
3. `CustomUserDetailsService.java` - Load user from DB
4. `UserRepository.java` - Find user by username
5. `AuthController.java` - Dashboard display

**Security Filter Chain:**
```
HTTP Request
    ▼
DisableEncodeUrlFilter
    ▼
WebAsyncManagerIntegrationFilter
    ▼
SecurityContextHolderFilter
    ▼
HeaderWriterFilter
    ▼
CorsFilter
    ▼
CsrfFilter
    ▼
LogoutFilter
    ▼
UsernamePasswordAuthenticationFilter  ◄─── Login happens here
    ▼
RequestCacheAwareFilter
    ▼
SecurityContextHolderAwareRequestFilter
    ▼
AnonymousAuthenticationFilter
    ▼
ExceptionTranslationFilter
    ▼
AuthorizationFilter  ◄─── Role checking happens here
    ▼
DispatcherServlet (Controller)
```

---

### 3. Student Panel Workflow

#### **View Students List (Any authenticated user):**

```
User clicks "View Students" on dashboard
        ▼
GET /student/list
        ▼
Spring Security checks authorization
        │
        ├─► Has role STUDENT, TEACHER, or ADMIN?
        │
        ▼ Yes (allowed)
        │
StudentController.listStudents()
        │
        ├─► StudentService.getAllStudents()
        │       │
        │       ├─► StudentRepository.findAll()
        │       │       │
        │       │       └─► SELECT * FROM students
        │       │
        │       └─► Return List<Student>
        │
        ├─► Add students to Model
        │
        ▼
Return "student/list"
        ▼
Thymeleaf renders student/list.html
        │
        ├─► Loop through students (th:each)
        ├─► Display each student in table row
        ├─► Show action buttons based on role (sec:authorize)
        │
        ▼
HTML sent to browser
```

**Files Involved:**
1. `dashboard.html` - Link to student list
2. `StudentController.java` - Handle request
3. `StudentService.java` - Business logic
4. `StudentRepository.java` - Database query
5. `student/list.html` - Display students

**StudentController.java - List Method:**
```java
@GetMapping("/list")
public String listStudents(Model model) {
    // 1. Get all students from service
    model.addAttribute("students", studentService.getAllStudents());
    
    // 2. Return view name
    return "student/list";  // Resolves to templates/student/list.html
}
```

**student/list.html - Display Logic:**
```html
<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <!-- Loop through each student -->
        <tr th:each="student : ${students}">
            <td th:text="${student.id}">1</td>
            <td th:text="${student.firstName + ' ' + student.lastName}">John Doe</td>
            <td>
                <a th:href="@{/student/view/{id}(id=${student.id})}">View</a>
                
                <!-- Only show Edit/Delete for TEACHER or ADMIN -->
                <a th:href="@{/student/edit/{id}(id=${student.id})}" 
                   sec:authorize="hasAnyRole('TEACHER', 'ADMIN')">Edit</a>
                <a th:href="@{/student/delete/{id}(id=${student.id})}" 
                   sec:authorize="hasAnyRole('TEACHER', 'ADMIN')">Delete</a>
            </td>
        </tr>
    </tbody>
</table>
```

#### **Create Student (Teacher/Admin only):**

```
User clicks "Add New Student"
        ▼
GET /student/create
        ▼
@PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')") checks
        │
        ├─► User has TEACHER or ADMIN role?
        │
        ▼ Yes
        │
StudentController.createStudentForm()
        │
        ├─► DepartmentService.getAllDepartments() (for dropdown)
        ├─► Create new Student object
        ├─► Add to model
        │
        ▼
Return "student/form"
        ▼
Render student/form.html (Create mode)
        ▼
User fills form and submits
        ▼
POST /student/create
        ▼
StudentController.createStudent(@ModelAttribute Student student)
        │
        ├─► StudentService.saveStudent(student)
        │       │
        │       ├─► StudentRepository.save(student)
        │       │       │
        │       │       └─► INSERT INTO students VALUES (...)
        │       │
        │       └─► Return saved Student
        │
        ▼
Redirect to /student/list
        ▼
Show updated student list
```

**Files Involved:**
1. `StudentController.java` - Handle create requests
2. `StudentService.java` - Save logic
3. `StudentRepository.java` - Database insert
4. `DepartmentService.java` - Get departments for dropdown
5. `student/form.html` - Create/Edit form

**StudentController.java - Create Methods:**
```java
@PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
@GetMapping("/create")
public String createStudentForm(Model model) {
    // 1. Create new empty student
    model.addAttribute("student", new Student());
    
    // 2. Get departments for dropdown
    model.addAttribute("departments", departmentService.getAllDepartments());
    
    // 3. Return form view
    return "student/form";
}

@PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
@PostMapping("/create")
public String createStudent(@ModelAttribute Student student) {
    // 1. Save student to database
    studentService.saveStudent(student);
    
    // 2. Redirect to list page
    return "redirect:/student/list";
}
```

**student/form.html - Dynamic Form:**
```html
<h2 th:text="${student.id != null ? 'Edit Student' : 'Add New Student'}">Form</h2>

<form th:action="${student.id != null ? '/student/edit/' + student.id : '/student/create'}" 
      th:object="${student}" method="post">
    
    <input type="text" th:field="*{firstName}" required />
    <input type="text" th:field="*{lastName}" required />
    
    <select th:field="*{department}">
        <option th:each="dept : ${departments}" 
                th:value="${dept.id}" 
                th:text="${dept.name}">Department</option>
    </select>
    
    <button type="submit">Save</button>
</form>
```

#### **Edit Student (Teacher/Admin only):**

```
User clicks "Edit" on student row
        ▼
GET /student/edit/{id}
        ▼
@PreAuthorize checks authorization
        ▼
StudentController.editStudentForm(id)
        │
        ├─► StudentService.getStudentById(id)
        │       │
        │       └─► StudentRepository.findById(id)
        │               │
        │               └─► SELECT * FROM students WHERE id = ?
        │
        ├─► DepartmentService.getAllDepartments()
        ├─► Add student and departments to model
        │
        ▼
Return "student/form"
        ▼
Render form with existing data pre-filled
        ▼
User modifies and submits
        ▼
POST /student/edit/{id}
        ▼
StudentController.editStudent(id, student)
        │
        ├─► student.setId(id)  // Ensure correct ID
        ├─► StudentService.saveStudent(student)
        │       │
        │       └─► StudentRepository.save(student)
        │               │
        │               └─► UPDATE students SET ... WHERE id = ?
        │
        ▼
Redirect to /student/list
```

#### **Delete Student (Teacher/Admin only):**

```
User clicks "Delete" on student row
        ▼
JavaScript confirms: "Are you sure?"
        ▼ User confirms
        ▼
GET /student/delete/{id}
        ▼
@PreAuthorize checks authorization
        ▼
StudentController.deleteStudent(id)
        │
        ├─► StudentService.deleteStudent(id)
        │       │
        │       └─► StudentRepository.deleteById(id)
        │               │
        │               └─► DELETE FROM students WHERE id = ?
        │
        ▼
Redirect to /student/list
```

---

### 4. Teacher Panel Workflow

#### **Similar to Student Panel, but with different authorization:**

**View Teachers:**
- **Who can access**: TEACHER, ADMIN (not STUDENT)
- **URL**: `/teacher/list`
- **Controller**: `TeacherController.listTeachers()`

**Create/Edit/Delete Teachers:**
- **Who can access**: ADMIN only
- **Annotation**: `@PreAuthorize("hasRole('ADMIN')")`
- **URLs**: `/teacher/create`, `/teacher/edit/{id}`, `/teacher/delete/{id}`

**Flow is identical to Student Panel:**
```
TeacherController → TeacherService → TeacherRepository → Database
                                                             ↓
                    ← Model ← View (teacher/list.html) ←──┘
```

---

### 5. Department Panel Workflow

**Access Control:**
- **View departments**: TEACHER, ADMIN
- **Create/Edit/Delete**: ADMIN only

**Controller Level Authorization:**
```java
@Controller
@RequestMapping("/department")
@PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")  // Class-level
public class DepartmentController {
    
    @GetMapping("/list")  // Inherits class-level authorization
    public String listDepartments(Model model) { ... }
    
    @PreAuthorize("hasRole('ADMIN')")  // Additional method-level restriction
    @PostMapping("/create")
    public String createDepartment(@ModelAttribute Department department) { ... }
}
```

---

### 6. Course Panel Workflow

**Access Control:**
- **View courses**: TEACHER, ADMIN
- **Create/Edit/Delete**: TEACHER, ADMIN

**Unique Feature:**
- Courses have relationships with both Department and Teacher
- Form includes dropdowns for both

**CourseController.java - Create Form:**
```java
@GetMapping("/create")
public String createCourseForm(Model model) {
    model.addAttribute("course", new Course());
    model.addAttribute("departments", departmentService.getAllDepartments());
    model.addAttribute("teachers", teacherService.getAllTeachers());  // For dropdown
    return "course/form";
}
```

**course/form.html - Multiple Relationships:**
```html
<select th:field="*{department}">
    <option th:each="dept : ${departments}" 
            th:value="${dept.id}" 
            th:text="${dept.name}">Department</option>
</select>

<select th:field="*{teacher}">
    <option th:each="t : ${teachers}" 
            th:value="${t.id}" 
            th:text="${t.firstName + ' ' + t.lastName}">Teacher</option>
</select>
```

---

### 7. Logout Workflow

```
User clicks "Logout" button
        ▼
POST /logout (form submission with CSRF token)
        ▼
LogoutFilter intercepts
        │
        ├─► Invalidate HTTP session
        ├─► Clear SecurityContext
        ├─► Clear authentication
        ├─► Delete cookies (if any)
        │
        ▼
Redirect to /login?logout=true
        ▼
login.html displays success message
```

**Why POST instead of GET:**
- **CSRF Protection**: Prevents malicious sites from logging you out
- **Security Best Practice**: State-changing operations should use POST

**Logout Button (dashboard.html):**
```html
<form th:action="@{/logout}" method="post" class="logout-form">
    <button type="submit" class="logout-btn">Logout</button>
</form>
```

**Thymeleaf automatically adds CSRF token:**
```html
<form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="[generated-token]"/>
    <button type="submit">Logout</button>
</form>
```

---

## 🏗️ Building the Project from Scratch

### Step 1: Create Spring Boot Project

#### Option A: Using Spring Initializr (https://start.spring.io)

1. **Project**: Maven
2. **Language**: Java
3. **Spring Boot**: 3.2.0
4. **Group**: com.sheikhgalib
5. **Artifact**: store
6. **Java**: 17
7. **Dependencies**:
   - Spring Web
   - Spring Data JPA
   - Spring Security
   - PostgreSQL Driver
   - Thymeleaf
   - Thymeleaf Extras Spring Security 6
   - Spring Boot DevTools
   - Validation

8. Generate and extract the project

#### Option B: Manual Setup

Create `pom.xml`:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" 
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
    </parent>
    
    <groupId>com.sheikhgalib</groupId>
    <artifactId>store</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>store</name>
    
    <properties>
        <java.version>17</java.version>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-springsecurity6</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

---

### Step 2: Configure Database

Create `src/main/resources/application.properties`:
```properties
# Application Name
spring.application.name=store

# Server Port
server.port=8081

# PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/storeDB
spring.datasource.username=postgres
spring.datasource.password=12345678

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true
```

**Create PostgreSQL Database:**
```sql
CREATE DATABASE storeDB;
```

---

### Step 3: Create Entity Classes

**1. Create `User.java` (in entity package):**
```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String email;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();
    
    private boolean enabled = true;
    
    // Getters and setters
}
```

**2. Create `Department.java`:**
```java
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    private String description;
    
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<>();
    
    // Getters and setters
}
```

**3. Create `Student.java`, `Teacher.java`, `Course.java`** (similar pattern)

---

### Step 4: Create Repository Interfaces

```java
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
    List<Student> findByDepartmentId(Long departmentId);
}
```

Repeat for User, Teacher, Department, Course

---

### Step 5: Create Service Classes

```java
@Service
@Transactional
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
    
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }
    
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
```

Repeat for Teacher, Department, Course

---

### Step 6: Configure Spring Security

**1. Create `CustomUserDetailsService.java`:**
```java
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) 
            throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
```

**2. Create `SecurityConfig.java`:**
```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    
    private final CustomUserDetailsService userDetailsService;
    
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/register", "/login").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/teacher/**").hasAnyRole("TEACHER", "ADMIN")
                .requestMatchers("/student/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            );
        
        return http.build();
    }
}
```

---

### Step 7: Create Controllers

**1. AuthController.java:**
```java
@Controller
public class AuthController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }
    
    @PostMapping("/register")
    public String register(@RequestParam String username,
                          @RequestParam String email,
                          @RequestParam String password,
                          @RequestParam String role) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Set.of("ROLE_" + role.toUpperCase()));
        
        userRepository.save(user);
        return "redirect:/login?registered=true";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "dashboard";
    }
}
```

**2. StudentController.java:**
```java
@Controller
@RequestMapping("/student")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private DepartmentService departmentService;
    
    @GetMapping("/list")
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "student/list";
    }
    
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "student/form";
    }
    
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @PostMapping("/create")
    public String create(@ModelAttribute Student student) {
        studentService.saveStudent(student);
        return "redirect:/student/list";
    }
    
    // Edit and Delete methods...
}
```

Repeat similar controllers for Teacher, Department, Course

---

### Step 8: Create Thymeleaf Templates

**Directory structure:**
```
src/main/resources/templates/
├── login.html
├── register.html
├── dashboard.html
├── access-denied.html
├── student/
│   ├── list.html
│   ├── form.html
│   └── view.html
├── teacher/
│   ├── list.html
│   ├── form.html
│   └── view.html
├── department/
│   ├── list.html
│   ├── form.html
│   └── view.html
└── course/
    ├── list.html
    ├── form.html
    └── view.html
```

**Example: login.html**
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Login</title>
    <style>
        /* Add CSS styling */
    </style>
</head>
<body>
    <div class="container">
        <h2>Login</h2>
        
        <div th:if="${param.error}" class="alert-error">
            Invalid username or password
        </div>
        
        <form th:action="@{/login}" method="post">
            <input type="text" name="username" placeholder="Username" required />
            <input type="password" name="password" placeholder="Password" required />
            <button type="submit">Login</button>
        </form>
        
        <a th:href="@{/register}">Register here</a>
    </div>
</body>
</html>
```

**Example: student/list.html**
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head>
    <title>Student List</title>
</head>
<body>
    <h1>Students</h1>
    
    <a th:href="@{/student/create}" 
       sec:authorize="hasAnyRole('TEACHER', 'ADMIN')">Add Student</a>
    
    <table>
        <tr th:each="student : ${students}">
            <td th:text="${student.firstName}">John</td>
            <td th:text="${student.lastName}">Doe</td>
            <td>
                <a th:href="@{/student/view/{id}(id=${student.id})}">View</a>
                <a th:href="@{/student/edit/{id}(id=${student.id})}"
                   sec:authorize="hasAnyRole('TEACHER', 'ADMIN')">Edit</a>
                <a th:href="@{/student/delete/{id}(id=${student.id})}"
                   sec:authorize="hasAnyRole('TEACHER', 'ADMIN')">Delete</a>
            </td>
        </tr>
    </table>
</body>
</html>
```

---

### Step 9: Create Data Initializer (Optional but Recommended)

**DataInitializer.java:**
```java
@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Create sample users
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRoles(Set.of("ROLE_ADMIN"));
            userRepository.save(admin);
            
            // Create teacher and student users...
        }
        
        // Create sample departments
        if (departmentRepository.count() == 0) {
            Department cs = new Department();
            cs.setName("Computer Science");
            cs.setDescription("Department of CS");
            departmentRepository.save(cs);
            
            // Create more departments...
        }
    }
}
```

---

### Step 10: Create Main Application Class

**StoreApplication.java:**
```java
@SpringBootApplication
public class StoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }
}
```

---

## 🚀 Running the Application

### Prerequisites:
1. **Java 17** installed
2. **PostgreSQL** installed and running
3. **Maven** installed (or use included mvnw)

### Steps:

#### 1. Create Database
```sql
-- Open PostgreSQL command line or pgAdmin
CREATE DATABASE storeDB;
```

#### 2. Configure Database Connection
Edit `application.properties` with your PostgreSQL credentials:
```properties
spring.datasource.username=postgres
spring.datasource.password=your_password
```

#### 3. Build the Project
```bash
# Windows
.\mvnw.cmd clean install

# Linux/Mac
./mvnw clean install
```

#### 4. Run the Application
```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

#### 5. Access the Application
Open browser: **http://localhost:8081**

### Expected Startup Logs:
```
Started StoreApplication in X.XXX seconds
===========================================
Sample users created:
Admin - Username: admin, Password: admin123
Teacher - Username: teacher1, Password: teacher123
Student - Username: student1, Password: student123
===========================================
```

---

## 🧪 Testing the Application

### Test Accounts:

| Username | Password | Role | Capabilities |
|----------|----------|------|--------------|
| admin | admin123 | ADMIN | Full access (all CRUD) |
| teacher1 | teacher123 | TEACHER | Manage students & courses |
| student1 | student123 | STUDENT | View only |

### Test Cases:

#### 1. **Registration Flow:**
```
1. Go to http://localhost:8081
2. Click "Register here"
3. Fill form:
   - Username: testuser
   - Email: test@example.com
   - Password: password123
   - Role: STUDENT
4. Click Register
5. Should redirect to login with success message
6. Login with new credentials
```

#### 2. **Student Role Test:**
```
1. Login as student1/student123
2. Dashboard shows only "Students" option
3. Click "View Students"
4. Can see student list
5. NO "Add New Student" button visible
6. NO Edit/Delete buttons on rows
7. Try to access /student/create directly
8. Should see "Access Denied" page
```

#### 3. **Teacher Role Test:**
```
1. Login as teacher1/teacher123
2. Dashboard shows Students, Teachers, Departments, Courses
3. Click "Students" → "Add New Student"
4. Fill form and save
5. Can edit and delete students
6. Try to add a new teacher
7. Should NOT see "Add Teacher" button (Admin only)
```

#### 4. **Admin Role Test:**
```
1. Login as admin/admin123
2. Can access all sections
3. Can perform all CRUD operations
4. Can add teachers, departments
5. Full system access
```

#### 5. **Logout Test:**
```
1. Login with any account
2. Click "Logout" button
3. Should redirect to login page
4. Try to access /dashboard
5. Should redirect back to login
6. Session cleared successfully
```

---

## 📊 Database Query Examples

### Check Created Tables:
```sql
-- List all tables
\dt

-- View table structure
\d users
\d students
\d teachers
\d departments
\d courses
```

### Sample Queries:
```sql
-- View all users
SELECT id, username, email FROM users;

-- View user roles
SELECT u.username, ur.role 
FROM users u 
JOIN user_roles ur ON u.id = ur.user_id;

-- View students with departments
SELECT s.first_name, s.last_name, d.name as department
FROM students s
LEFT JOIN departments d ON s.department_id = d.id;

-- View courses with teachers
SELECT c.name as course_name, c.course_code, 
       t.first_name || ' ' || t.last_name as teacher_name
FROM courses c
LEFT JOIN teachers t ON c.teacher_id = t.id;
```

---

## 🎓 Key Learning Points

### 1. **Spring Boot Auto-Configuration:**
- Automatically configures DataSource, JPA, Security
- No XML configuration needed
- Convention over configuration

### 2. **Dependency Injection:**
- `@Autowired` injects dependencies
- Constructor injection is preferred
- Spring manages bean lifecycle

### 3. **JPA/Hibernate:**
- Entities represent database tables
- Repositories provide CRUD operations
- No SQL code needed for basic operations
- `@ManyToOne`, `@OneToMany`, `@ManyToMany` define relationships

### 4. **Spring Security:**
- Filter-based security
- Method-level security with `@PreAuthorize`
- BCrypt password hashing
- CSRF protection built-in

### 5. **MVC Pattern:**
- **Model**: Entities (data)
- **View**: Thymeleaf templates (presentation)
- **Controller**: Controllers (logic)
- Clear separation of concerns

### 6. **Thymeleaf:**
- Server-side template engine
- Natural templates (valid HTML)
- Spring Security integration
- Expression language for dynamic content

---

## 🔍 Troubleshooting

### Common Issues:

#### 1. **Port 8081 already in use:**
```
Solution: Change port in application.properties
server.port=8082
```

#### 2. **Database connection failed:**
```
Solution: Check PostgreSQL is running
- Verify credentials in application.properties
- Ensure database storeDB exists
```

#### 3. **Logout returns 404:**
```
Solution: Use POST form, not GET link
<form th:action="@{/logout}" method="post">
    <button type="submit">Logout</button>
</form>
```

#### 4. **Access denied errors:**
```
Solution: Check user roles
- Students can only VIEW
- Teachers can MANAGE students
- Admin can do EVERYTHING
```

#### 5. **Circular dependency error:**
```
Solution: Use constructor injection instead of @Autowired fields
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
```

---

## 📝 Summary

This Student Management System demonstrates:

✅ **Complete MVC Architecture**  
✅ **Spring Boot Best Practices**  
✅ **Spring Security Implementation**  
✅ **Role-Based Access Control**  
✅ **JPA Entity Relationships**  
✅ **RESTful URL Design**  
✅ **Thymeleaf Template Engine**  
✅ **PostgreSQL Integration**  
✅ **CRUD Operations**  
✅ **Session Management**  

### Project Highlights:
- **7 Database Tables** with proper relationships
- **5 Controllers** handling all operations
- **5 Services** containing business logic
- **5 Repositories** for data access
- **5 Entities** mapped to tables
- **13 HTML Templates** for user interface
- **Complete Security** with authentication & authorization

---

## 🎯 Next Steps for Enhancement

1. **Add Email Notifications** - Send emails on registration
2. **Add File Upload** - Profile pictures for students/teachers
3. **Add Pagination** - For large datasets
4. **Add Search & Filter** - Find students/teachers easily
5. **Add Reports** - Generate PDF reports
6. **Add API Endpoints** - RESTful API for mobile apps
7. **Add Attendance System** - Track student attendance
8. **Add Grading System** - Manage student grades
9. **Add Course Enrollment** - Students can enroll in courses
10. **Add Dashboard Analytics** - Charts and statistics

---

**End of Project Report**

*This comprehensive guide covers everything you need to understand, build, and explain the Student Management System to your teacher or use as a template for similar projects.*

---

**Created by:** Sheikh Galib  
**Date:** February 2, 2026  
**Framework:** Spring Boot 3.2.0  
**Database:** PostgreSQL  
**Language:** Java 17
