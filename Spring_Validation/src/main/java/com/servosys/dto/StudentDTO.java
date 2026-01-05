package com.servosys.dto;


import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {

    private Long id;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50)
    private String lastName;

    @NotBlank
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank
    @Pattern(
        regexp = "^[6-9]\\d{9}$",
        message = "Phone number must be valid Indian number"
    )
    private String phone;

    @NotNull
    @Past(message = "DOB must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank
    private String department;

    @NotNull
    @Min(1)
    @Max(4)
    private Integer academicYear;

    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "10.0")
    private Double cgpa;

    private Boolean active;
}
