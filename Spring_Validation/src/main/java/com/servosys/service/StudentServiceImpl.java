package com.servosys.service;

import com.servosys.dto.StudentDTO;
import com.servosys.exception.ResourceNotFoundException;
import com.servosys.exception.ValidationException;
import com.servosys.model.Student;
import com.servosys.repository.StudentRepository;
import com.servosys.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {
    
    private final StudentRepository studentRepository;
    
    @Override
    @Transactional
    public StudentDTO createStudent(StudentDTO studentDTO) {
        log.info("Creating new student with email: {}", studentDTO.getEmail());
        
        // Check if email already exists
        if (studentRepository.existsByEmail(studentDTO.getEmail())) {
            throw new ValidationException("Email already exists: " + studentDTO.getEmail());
        }
        
        // Create student using builder
        Student student = Student.builder()
                .firstName(studentDTO.getFirstName())
                .lastName(studentDTO.getLastName())
                .email(studentDTO.getEmail())
                .phoneNumber(studentDTO.getPhoneNumber())
                .dateOfBirth(studentDTO.getDateOfBirth())
                .department(studentDTO.getDepartment())
                .year(studentDTO.getYear())
                .cgpa(studentDTO.getCgpa())
                .isActive(studentDTO.getIsActive() != null ? studentDTO.getIsActive() : true)
                .build();
        
        Student savedStudent = studentRepository.save(student);
        log.info("Student created successfully with ID: {}", savedStudent.getId());
        
        return convertToDTO(savedStudent);
    }
    
    @Override
    public StudentDTO getStudentById(Long id) {
        log.info("Fetching student with ID: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
        return convertToDTO(student);
    }
    
    @Override
    public List<StudentDTO> getAllStudents() {
        log.info("Fetching all students");
        return studentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        log.info("Updating student with ID: {}", id);
        
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
        
        // Check if email is being changed and if it already exists for another student
        if (!existingStudent.getEmail().equals(studentDTO.getEmail()) && 
            studentRepository.existsByEmailAndIdNot(studentDTO.getEmail(), id)) {
            throw new ValidationException("Email already exists: " + studentDTO.getEmail());
        }
        
        // Update fields
        existingStudent.setFirstName(studentDTO.getFirstName());
        existingStudent.setLastName(studentDTO.getLastName());
        existingStudent.setEmail(studentDTO.getEmail());
        existingStudent.setPhoneNumber(studentDTO.getPhoneNumber());
        existingStudent.setDateOfBirth(studentDTO.getDateOfBirth());
        existingStudent.setDepartment(studentDTO.getDepartment());
        existingStudent.setYear(studentDTO.getYear());
        existingStudent.setCgpa(studentDTO.getCgpa());
        
        Student updatedStudent = studentRepository.save(existingStudent);
        log.info("Student updated successfully with ID: {}", id);
        
        return convertToDTO(updatedStudent);
    }
    
    @Override
    @Transactional
    public void deleteStudent(Long id) {
        log.info("Deleting student with ID: {}", id);
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student", "id", id);
        }
        studentRepository.deleteById(id);
        log.info("Student deleted successfully with ID: {}", id);
    }
    
    @Override
    public List<StudentDTO> getStudentsByDepartment(String department) {
        log.info("Fetching students by department: {}", department);
        return studentRepository.findByDepartment(department)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<StudentDTO> searchStudentsByName(String name) {
        log.info("Searching students by name: {}", name);
        return studentRepository.findByNameContaining(name)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public StudentDTO deactivateStudent(Long id) {
        log.info("Deactivating student with ID: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
        student.setIsActive(false);
        Student updatedStudent = studentRepository.save(student);
        return convertToDTO(updatedStudent);
    }
    
    @Override
    @Transactional
    public StudentDTO activateStudent(Long id) {
        log.info("Activating student with ID: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
        student.setIsActive(true);
        Student updatedStudent = studentRepository.save(student);
        return convertToDTO(updatedStudent);
    }
    
    @Override
    public List<StudentDTO> getActiveStudents() {
        log.info("Fetching all active students");
        return studentRepository.findByIsActive(true)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<StudentDTO> getTopPerformingStudents(Integer year, Double minCgpa) {
        log.info("Fetching top performing students for year: {} with CGPA >= {}", year, minCgpa);
        return studentRepository.findByYearAndCgpaGreaterThanEqual(year, minCgpa)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private StudentDTO convertToDTO(Student student) {
        return StudentDTO.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .phoneNumber(student.getPhoneNumber())
                .dateOfBirth(student.getDateOfBirth())
                .department(student.getDepartment())
                .year(student.getYear())
                .cgpa(student.getCgpa())
                .isActive(student.getIsActive())
                .build();
    }
}