package com.servosys.controller;

import com.servosys.dto.ApiResponse;
import com.servosys.dto.StudentDTO;
import com.servosys.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {
    
    private final StudentService studentService;
    
    // Create Student
    @PostMapping
    public ResponseEntity<ApiResponse<StudentDTO>> createStudent(
            @Valid @RequestBody StudentDTO studentDTO) {
        log.info("Creating student with email: {}", studentDTO.getEmail());
        StudentDTO createdStudent = studentService.createStudent(studentDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created(createdStudent, "Student created successfully"));
    }
    
    // Get Student by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDTO>> getStudentById(@PathVariable Long id) {
        log.info("Getting student by ID: {}", id);
        StudentDTO student = studentService.getStudentById(id);
        return ResponseEntity.ok(ApiResponse.success(student, "Student retrieved successfully"));
    }
    
    // Get All Students
    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getAllStudents() {
        log.info("Getting all students");
        List<StudentDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(ApiResponse.success(students, "Students retrieved successfully"));
    }
    
    // Update Student
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDTO>> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentDTO studentDTO) {
        log.info("Updating student with ID: {}", id);
        StudentDTO updatedStudent = studentService.updateStudent(id, studentDTO);
        return ResponseEntity.ok(ApiResponse.success(updatedStudent, "Student updated successfully"));
    }
    
    // Delete Student
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        log.info("Deleting student with ID: {}", id);
        studentService.deleteStudent(id);
        return ResponseEntity.ok(ApiResponse.success("Student deleted successfully"));
    }
    
    // Get Students by Department
    @GetMapping("/department/{department}")
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getStudentsByDepartment(
            @PathVariable String department) {
        log.info("Getting students by department: {}", department);
        List<StudentDTO> students = studentService.getStudentsByDepartment(department);
        return ResponseEntity.ok(ApiResponse.success(students, "Students retrieved successfully"));
    }
    
    // Search Students by Name
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<StudentDTO>>> searchStudentsByName(
            @RequestParam String name) {
        log.info("Searching students by name: {}", name);
        List<StudentDTO> students = studentService.searchStudentsByName(name);
        return ResponseEntity.ok(ApiResponse.success(students, "Students retrieved successfully"));
    }
    
    // Deactivate Student
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<StudentDTO>> deactivateStudent(@PathVariable Long id) {
        log.info("Deactivating student with ID: {}", id);
        StudentDTO student = studentService.deactivateStudent(id);
        return ResponseEntity.ok(ApiResponse.success(student, "Student deactivated successfully"));
    }
    
    // Activate Student
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<StudentDTO>> activateStudent(@PathVariable Long id) {
        log.info("Activating student with ID: {}", id);
        StudentDTO student = studentService.activateStudent(id);
        return ResponseEntity.ok(ApiResponse.success(student, "Student activated successfully"));
    }
    
    // Get Active Students
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getActiveStudents() {
        log.info("Getting active students");
        List<StudentDTO> students = studentService.getActiveStudents();
        return ResponseEntity.ok(ApiResponse.success(students, "Active students retrieved successfully"));
    }
    
    // Get Top Performing Students
    @GetMapping("/top-performing")
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getTopPerformingStudents(
            @RequestParam Integer year,
            @RequestParam Double minCgpa) {
        log.info("Getting top performing students for year: {} with CGPA >= {}", year, minCgpa);
        List<StudentDTO> students = studentService.getTopPerformingStudents(year, minCgpa);
        return ResponseEntity.ok(ApiResponse.success(students, "Top performing students retrieved successfully"));
    }
}