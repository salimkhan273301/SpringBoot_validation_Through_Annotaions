package com.servosys.service;

import com.servosys.dto.StudentDTO;
import java.util.List;

public interface StudentService {
    StudentDTO createStudent(StudentDTO studentDTO);
    StudentDTO getStudentById(Long id);
    List<StudentDTO> getAllStudents();
    StudentDTO updateStudent(Long id, StudentDTO studentDTO);
    void deleteStudent(Long id);
    List<StudentDTO> getStudentsByDepartment(String department);
    List<StudentDTO> searchStudentsByName(String name);
    StudentDTO deactivateStudent(Long id);
    StudentDTO activateStudent(Long id);
    List<StudentDTO> getActiveStudents();
    List<StudentDTO> getTopPerformingStudents(Integer year, Double minCgpa);
}