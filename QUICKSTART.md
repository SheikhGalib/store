# Quick Start Guide - Student Management System

## 🚀 Getting Started

### Step 1: Database Setup
1. Open PostgreSQL and create the database:
   ```sql
   CREATE DATABASE storeDB;
   ```

2. The database tables will be created automatically when you run the application.

### Step 2: Run the Application
```bash
cd D:\spring-boot-learning\store
mvnw spring-boot:run
```

Or in PowerShell:
```powershell
cd D:\spring-boot-learning\store
.\mvnw.cmd spring-boot:run
```

### Step 3: Access the Application
Open your browser and go to: **http://localhost:8081**

## 🔐 Login Credentials

### Admin Account
- **Username**: `admin`
- **Password**: `admin123`
- **Access**: Full system access

### Teacher Account  
- **Username**: `teacher1`
- **Password**: `teacher123`
- **Access**: Can manage students and courses

### Student Account
- **Username**: `student1`
- **Password**: `student123`
- **Access**: View-only access

## 📝 Testing the System

### Test as a Student:
1. Login with: `student1` / `student123`
2. Navigate to "Students" section
3. Try to view student list ✅
4. Try to create a new student ❌ (Access Denied)

### Test as a Teacher:
1. Login with: `teacher1` / `teacher123`
2. Navigate to "Students" section
3. Click "Add New Student" ✅
4. Fill in student details and save ✅
5. Edit and delete students ✅
6. Navigate to "Teachers" section
7. Try to add a new teacher ❌ (Access Denied - Admin only)

### Test as an Admin:
1. Login with: `admin` / `admin123`
2. You have full access to:
   - Students (CRUD)
   - Teachers (CRUD)
   - Departments (CRUD)
   - Courses (CRUD)

## 🎯 Key Features to Test

### 1. Role-Based Access Control
- Students can only VIEW
- Teachers can VIEW and MANAGE students/courses
- Admin can MANAGE everything

### 2. CRUD Operations
- **Create**: Add new records
- **Read**: View lists and details
- **Update**: Edit existing records
- **Delete**: Remove records (with confirmation)

### 3. Entity Relationships
- Assign students to departments
- Assign courses to teachers
- Link students with courses

## 📊 Sample Data

The application comes pre-loaded with:
- 3 Departments: Computer Science, Mathematics, Physics
- 1 Teacher: John Smith (CS Department)
- 2 Courses: Data Structures (CS101), Algorithms (CS102)
- 1 Student: Alice Johnson (CS Department)

## 🔧 Troubleshooting

### Database Connection Issues
If you get database errors:
1. Check PostgreSQL is running
2. Verify database name is `storeDB`
3. Check username/password in `application.properties`

### Port Already in Use
If port 8081 is busy:
1. Change port in `application.properties`:
   ```properties
   server.port=8082
   ```
2. Restart the application

### Login Issues
- Make sure you're using the correct credentials
- Passwords are case-sensitive
- Try the default accounts listed above

## 🎨 Navigation Guide

```
Dashboard
├── Students
│   ├── View Students → List all students
│   ├── Add Student → Create new (Teachers/Admin)
│   ├── Edit Student → Modify existing (Teachers/Admin)
│   └── Delete Student → Remove (Teachers/Admin)
│
├── Teachers
│   ├── View Teachers → List all teachers
│   ├── Add Teacher → Create new (Admin only)
│   ├── Edit Teacher → Modify existing (Admin only)
│   └── Delete Teacher → Remove (Admin only)
│
├── Departments
│   ├── View Departments → List all departments
│   ├── Add Department → Create new (Admin only)
│   └── Manage Department (Admin only)
│
└── Courses
    ├── View Courses → List all courses
    ├── Add Course → Create new (Teachers/Admin)
    └── Manage Courses (Teachers/Admin)
```

## 📱 Register New Users

1. Click "Register here" on login page
2. Fill in:
   - Username
   - Email
   - Password
   - Role (Student or Teacher)
3. Click "Register"
4. Login with new credentials

## ✨ Next Steps

1. **Customize**: Modify templates in `src/main/resources/templates/`
2. **Add Features**: Extend controllers and services
3. **Enhance UI**: Update CSS styles
4. **Add Validation**: Include form validation rules
5. **Add Reports**: Generate student/teacher reports

## 🆘 Need Help?

Check the main README.md for:
- Complete API documentation
- Project structure details
- Development guide
- Security features

---
**Happy Learning! 🎓**
