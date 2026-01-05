package com.servosys.service;

import org.springframework.data.domain.Page;

import com.servosys.dto.StudentDTO;

public interface StudentService {

    StudentDTO create(StudentDTO dto);

    StudentDTO getById(Long id);

    Page<StudentDTO> getByDepartment(
            String department, int page, int size);

    StudentDTO deactivate(Long id);
}
