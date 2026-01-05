package com.servosys.repository;

import com.servosys.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    
    Optional<Student> findByEmail(String email);
    
    List<Student> findByDepartment(String department);
    
    List<Student> findByIsActive(Boolean isActive);
    
    @Query("SELECT s FROM Student s WHERE s.year = :year AND s.cgpa >= :minCgpa")
    List<Student> findByYearAndCgpaGreaterThanEqual(@Param("year") Integer year, 
                                                   @Param("minCgpa") Double minCgpa);
    
    @Query("SELECT s FROM Student s WHERE LOWER(s.firstName) LIKE LOWER(CONCAT('%', :name, '%')) " +
           "OR LOWER(s.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Student> findByNameContaining(@Param("name") String name);
    
    boolean existsByEmail(String email);
    
    boolean existsByEmailAndIdNot(String email, Long id);
}