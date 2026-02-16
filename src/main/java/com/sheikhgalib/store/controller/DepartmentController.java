package com.sheikhgalib.store.controller;

import com.sheikhgalib.store.entity.Department;
import com.sheikhgalib.store.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/department")
@PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/list")
    public String listDepartments(Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "department/list";
    }

    @GetMapping("/view/{id}")
    public String viewDepartment(@PathVariable Long id, Model model) {
        Department department = departmentService.getDepartmentById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        model.addAttribute("department", department);
        return "department/view";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public String createDepartmentForm(Model model) {
        model.addAttribute("department", new Department());
        return "department/form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String createDepartment(@ModelAttribute Department department) {
        departmentService.saveDepartment(department);
        return "redirect:/department/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String editDepartmentForm(@PathVariable Long id, Model model) {
        Department department = departmentService.getDepartmentById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        model.addAttribute("department", department);
        return "department/form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit/{id}")
    public String editDepartment(@PathVariable Long id, @ModelAttribute Department department) {
        department.setId(id);
        departmentService.saveDepartment(department);
        return "redirect:/department/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return "redirect:/department/list";
    }
}
