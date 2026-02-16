package com.sheikhgalib.store.controller;

import com.sheikhgalib.store.entity.Course;
import com.sheikhgalib.store.service.CourseService;
import com.sheikhgalib.store.service.DepartmentService;
import com.sheikhgalib.store.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/course")
@PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/list")
    public String listCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "course/list";
    }

    @GetMapping("/view/{id}")
    public String viewCourse(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        model.addAttribute("course", course);
        return "course/view";
    }

    @GetMapping("/create")
    public String createCourseForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("teachers", teacherService.getAllTeachers());
        return "course/form";
    }

    @PostMapping("/create")
    public String createCourse(@ModelAttribute Course course) {
        courseService.saveCourse(course);
        return "redirect:/course/list";
    }

    @GetMapping("/edit/{id}")
    public String editCourseForm(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        model.addAttribute("course", course);
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("teachers", teacherService.getAllTeachers());
        return "course/form";
    }

    @PostMapping("/edit/{id}")
    public String editCourse(@PathVariable Long id, @ModelAttribute Course course) {
        course.setId(id);
        courseService.saveCourse(course);
        return "redirect:/course/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return "redirect:/course/list";
    }
}
