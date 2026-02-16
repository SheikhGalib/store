package com.sheikhgalib.store.service;

import com.sheikhgalib.store.entity.Course;
import com.sheikhgalib.store.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public List<Course> getCoursesByDepartment(Long departmentId) {
        return courseRepository.findByDepartmentId(departmentId);
    }

    public List<Course> getCoursesByTeacher(Long teacherId) {
        return courseRepository.findByTeacherId(teacherId);
    }
}
