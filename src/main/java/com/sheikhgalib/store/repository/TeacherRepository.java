package com.sheikhgalib.store.repository;

import com.sheikhgalib.store.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByEmail(String email);
    Optional<Teacher> findByEmployeeId(String employeeId);
    List<Teacher> findByDepartmentId(Long departmentId);
}
