package com.servosys.repository;

import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.servosys.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);

    List<Student> findByActiveTrue();

    Page<Student> findByDepartment(String department, Pageable pageable);
}
