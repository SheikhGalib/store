# Student Management System

[![CI/CD Pipeline](https://github.com/SheikhGalib/store/actions/workflows/ci.yml/badge.svg)](https://github.com/SheikhGalib/store/actions/workflows/ci.yml)

> **Version:** 1.0.1  
> **Last Updated:** February 17, 2026  
> **Branch Protection:** ✅ Enabled
> Security Valnarbility ci/cd job removed.

A comprehensive Student Management System built with Spring Boot, Spring Security, PostgreSQL, and Thymeleaf.

CI/CD added

## Features

### Authentication & Authorization
- User registration and login
- Role-based access control (Students, Teachers, Admin)
- Secure password encryption using BCrypt

### Role-Based Permissions

#### Students (ROLE_STUDENT)
- View student list
- View student details
- **Cannot** create, update, or delete students

#### Teachers (ROLE_TEACHER)
- All student permissions
- Create, update, and delete students
- View and manage courses
- View departments
- View teacher list

#### Admin (ROLE_ADMIN)
- All teacher permissions
- Create, update, and delete teachers
- Create, update, and delete departments
- Full system access

### Entities & Relationships

1. **User** - Authentication and authorization
2. **Student** - Student information and enrollment
3. **Teacher** - Teacher information and assignments
4. **Department** - Academic departments
5. **Course** - Course offerings

#### Entity Relationships:
- Department has many Students
- Department has many Teachers
- Department has many Courses
- Teacher has many Courses
- Student has many Courses (Many-to-Many)
- Course belongs to Department and Teacher

## Technology Stack

- **Backend**: Spring Boot 3.2.2
- **Security**: Spring Security 6
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA / Hibernate
- **Template Engine**: Thymeleaf
- **Build Tool**: Maven
- **Java Version**: 17

## Prerequisites

1. Java 17 or higher
2. PostgreSQL 12 or higher
3. Maven 3.6+

## Database Setup

1. Create PostgreSQL database:
```sql
CREATE DATABASE storeDB;
```

2. Update database credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/storeDB
spring.datasource.username=postgres
spring.datasource.password=12345678
```

## Running the Application

1. Clone the repository
2. Navigate to project directory
3. Run the application:
```bash
mvn spring-boot:run
```

4. Access the application at: `http://localhost:8081`

## Default Users

The application comes with pre-configured demo users:

| Role | Username | Password |
|------|----------|----------|
| Admin | admin | admin123 |
| Teacher | teacher1 | teacher123 |
| Student | student1 | student123 |

## Project Structure

```
src/main/java/com/sheikhgalib/store/
├── config/
│   └── SecurityConfig.java          # Spring Security configuration
├── controller/
│   ├── AuthController.java          # Login, Register, Dashboard
│   ├── StudentController.java       # Student CRUD operations
│   ├── TeacherController.java       # Teacher CRUD operations
│   ├── DepartmentController.java    # Department CRUD operations
│   └── CourseController.java        # Course CRUD operations
├── entity/
│   ├── User.java
│   ├── Student.java
│   ├── Teacher.java
│   ├── Department.java
│   └── Course.java
├── repository/
│   ├── UserRepository.java
│   ├── StudentRepository.java
│   ├── TeacherRepository.java
│   ├── DepartmentRepository.java
│   └── CourseRepository.java
├── security/
│   └── CustomUserDetailsService.java
├── service/
│   ├── StudentService.java
│   ├── TeacherService.java
│   ├── DepartmentService.java
│   └── CourseService.java
├── DataInitializer.java             # Sample data initialization
└── StoreApplication.java            # Main application class

src/main/resources/
├── templates/
│   ├── login.html
│   ├── register.html
│   ├── dashboard.html
│   ├── access-denied.html
│   └── student/
│       ├── list.html
│       ├── form.html
│       └── view.html
└── application.properties
```

## API Endpoints

### Authentication
- `GET /login` - Login page
- `POST /login` - Process login
- `GET /register` - Registration page
- `POST /register` - Process registration
- `GET /logout` - Logout
- `GET /dashboard` - Dashboard (authenticated users)

### Students
- `GET /student/list` - View all students
- `GET /student/view/{id}` - View student details
- `GET /student/create` - Create student form (Teachers/Admin only)
- `POST /student/create` - Save new student (Teachers/Admin only)
- `GET /student/edit/{id}` - Edit student form (Teachers/Admin only)
- `POST /student/edit/{id}` - Update student (Teachers/Admin only)
- `GET /student/delete/{id}` - Delete student (Teachers/Admin only)

### Teachers
- `GET /teacher/list` - View all teachers
- `GET /teacher/view/{id}` - View teacher details
- `GET /teacher/create` - Create teacher form (Admin only)
- `POST /teacher/create` - Save new teacher (Admin only)
- `GET /teacher/edit/{id}` - Edit teacher form (Admin only)
- `POST /teacher/edit/{id}` - Update teacher (Admin only)
- `GET /teacher/delete/{id}` - Delete teacher (Admin only)

### Departments
- `GET /department/list` - View all departments
- `GET /department/view/{id}` - View department details
- `GET /department/create` - Create department form (Admin only)
- `POST /department/create` - Save new department (Admin only)
- `GET /department/edit/{id}` - Edit department form (Admin only)
- `POST /department/edit/{id}` - Update department (Admin only)
- `GET /department/delete/{id}` - Delete department (Admin only)

### Courses
- `GET /course/list` - View all courses
- `GET /course/view/{id}` - View course details
- `GET /course/create` - Create course form (Teachers/Admin)
- `POST /course/create` - Save new course (Teachers/Admin)
- `GET /course/edit/{id}` - Edit course form (Teachers/Admin)
- `POST /course/edit/{id}` - Update course (Teachers/Admin)
- `GET /course/delete/{id}` - Delete course (Teachers/Admin)

## Security Features

1. **Password Encryption**: All passwords are encrypted using BCrypt
2. **Method-Level Security**: Uses `@PreAuthorize` annotations
3. **CSRF Protection**: Enabled by default
4. **Session Management**: Secure session handling
5. **Access Denied Handling**: Custom access denied page

## Usage Guide

### For Students:
1. Login with student credentials
2. View student list
3. View student details
4. Access limited to read-only operations

### For Teachers:
1. Login with teacher credentials
2. View and manage students (Create, Read, Update, Delete)
3. View and manage courses
4. View departments and other teachers

### For Admin:
1. Login with admin credentials
2. Full access to all resources
3. Manage teachers and departments
4. Complete system administration

## Development

To add more features:

1. **Add new entities**: Create entity classes in `entity` package
2. **Add repositories**: Create repository interfaces extending `JpaRepository`
3. **Add services**: Create service classes with business logic
4. **Add controllers**: Create controller classes with endpoints
5. **Add templates**: Create Thymeleaf templates in `templates` directory

## Testing

This project includes comprehensive unit tests and integration tests.

### Running Tests

```bash
# Run all tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=StudentServiceTest

# Run only unit tests
./mvnw test -Dtest="*ServiceTest,*ControllerTest"

# Run only integration tests
./mvnw test -Dtest="*IntegrationTest"

# Skip tests during build
./mvnw package -DskipTests
```

### Test Categories

| Category | Description | Example |
|----------|-------------|---------|
| **Unit Tests** | Test individual components in isolation using Mockito | `StudentServiceTest` |
| **Integration Tests** | Test full Spring context with H2 database | `StudentControllerIntegrationTest` |
| **Repository Tests** | Test JPA repository methods | `StudentRepositoryTest` |
| **Security Tests** | Test role-based access control | Tests with `@WithMockUser` |

### Test Coverage

- **Service Layer**: 100% coverage with 32 unit tests
- **Controller Layer**: Full endpoint coverage with security tests
- **Repository Layer**: Custom query method testing
- **Entity Layer**: Getter/setter and relationship tests

### Test Configuration

Tests use H2 in-memory database configured in `src/test/resources/application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=create-drop
```

### Manual Testing

Access the application and test with the default users:

| Role | Username | Password |
|------|----------|----------|
| Admin | admin | admin123 |
| Teacher | teacher1 | teacher123 |
| Student | student1 | student123 |

## CI/CD Pipeline

This project includes a GitHub Actions CI/CD pipeline that:

- ✅ Builds and compiles the project
- ✅ Runs all 99 unit and integration tests
- ✅ Performs code quality checks
- ✅ Scans for security vulnerabilities
- ✅ Creates JAR artifacts (on main branch)
- ✅ Builds Docker images (on main branch)

### Pipeline Status

The CI pipeline runs automatically on:
- Every push to `main`, `master`, or `develop` branches
- Every pull request to these branches

### Branch Protection

For production deployments, configure branch protection rules. See `docs/GITHUB_PROTECTION_RULES.md` for a complete guide.

## Documentation

For detailed project documentation, see:

- `docs/PROJECT_REPORT.md` - Comprehensive project report with MVC workflow
- `docs/RUN_INSTRUCTIONS.md` - Detailed setup instructions
- `docs/GITHUB_PROTECTION_RULES.md` - GitHub branch protection setup guide
- `QUICKSTART.md` - Quick start guide

## License

This project is created for educational purposes.
