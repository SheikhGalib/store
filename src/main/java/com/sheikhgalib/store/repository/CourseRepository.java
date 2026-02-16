package com.sheikhgalib.store.repository;

import com.sheikhgalib.store.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseCode(String courseCode);
    List<Course> findByDepartmentId(Long departmentId);
    List<Course> findByTeacherId(Long teacherId);
}
