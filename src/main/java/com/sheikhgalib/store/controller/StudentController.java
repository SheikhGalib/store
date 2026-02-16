package com.sheikhgalib.store.controller;

import com.sheikhgalib.store.entity.Student;
import com.sheikhgalib.store.service.DepartmentService;
import com.sheikhgalib.store.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/view/{id}")
    public String viewStudent(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        model.addAttribute("student", student);
        return "student/view";
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @GetMapping("/create")
    public String createStudentForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "student/form";
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @PostMapping("/create")
    public String createStudent(@ModelAttribute Student student) {
        studentService.saveStudent(student);
        return "redirect:/student/list";
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @GetMapping("/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        model.addAttribute("student", student);
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "student/form";
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @PostMapping("/edit/{id}")
    public String editStudent(@PathVariable Long id, @ModelAttribute Student student) {
        student.setId(id);
        studentService.saveStudent(student);
        return "redirect:/student/list";
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/student/list";
    }
}
