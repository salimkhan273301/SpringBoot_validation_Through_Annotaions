package com.servosys.service;



import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.servosys.dto.StudentDTO;
import com.servosys.exception.*;
import com.servosys.model.Student;
import com.servosys.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    @Override
    @Transactional
    public StudentDTO create(StudentDTO dto) {

        if (repository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        Student student = mapToEntity(dto);
        return mapToDTO(repository.save(student));
    }

    @Override
    public StudentDTO getById(Long id) {
        return mapToDTO(findStudent(id));
    }

    @Override
    public Page<StudentDTO> getByDepartment(
            String department, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("firstName"));

        return repository.findByDepartment(department, pageable)
                .map(this::mapToDTO);
    }

    @Override
    @Transactional
    public StudentDTO deactivate(Long id) {
        Student student = findStudent(id);
        student.setActive(false);
        return mapToDTO(student);
    }

    // ---------- Helpers ----------

    private Student findStudent(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found"));
    }

    private Student mapToEntity(StudentDTO dto) {
        return Student.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .dateOfBirth(dto.getDateOfBirth())
                .department(dto.getDepartment())
                .academicYear(dto.getAcademicYear())
                .cgpa(dto.getCgpa())
                .active(true)
                .build();
    }

    private StudentDTO mapToDTO(Student s) {
        return StudentDTO.builder()
                .id(s.getId())
                .firstName(s.getFirstName())
                .lastName(s.getLastName())
                .email(s.getEmail())
                .phone(s.getPhone())
                .dateOfBirth(s.getDateOfBirth())
                .department(s.getDepartment())
                .academicYear(s.getAcademicYear())
                .cgpa(s.getCgpa())
                .active(s.getActive())
                .build();
    }
}
