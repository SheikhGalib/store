package com.sheikhgalib.store.controller;

import com.sheikhgalib.store.entity.Teacher;
import com.sheikhgalib.store.service.DepartmentService;
import com.sheikhgalib.store.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/list")
    public String listTeachers(Model model) {
        model.addAttribute("teachers", teacherService.getAllTeachers());
        return "teacher/list";
    }

    @GetMapping("/view/{id}")
    public String viewTeacher(@PathVariable Long id, Model model) {
        Teacher teacher = teacherService.getTeacherById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        model.addAttribute("teacher", teacher);
        return "teacher/view";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public String createTeacherForm(Model model) {
        model.addAttribute("teacher", new Teacher());
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "teacher/form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String createTeacher(@ModelAttribute Teacher teacher) {
        teacherService.saveTeacher(teacher);
        return "redirect:/teacher/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String editTeacherForm(@PathVariable Long id, Model model) {
        Teacher teacher = teacherService.getTeacherById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        model.addAttribute("teacher", teacher);
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "teacher/form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit/{id}")
    public String editTeacher(@PathVariable Long id, @ModelAttribute Teacher teacher) {
        teacher.setId(id);
        teacherService.saveTeacher(teacher);
        return "redirect:/teacher/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return "redirect:/teacher/list";
    }
}
