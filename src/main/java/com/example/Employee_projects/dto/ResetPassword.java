package com.example.Employee_projects.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResetPassword {
    private String employeeId;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
