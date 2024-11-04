package com.example.Employee_projects.dto;



import com.example.Employee_projects.Document.Employee;
import lombok.Data;

@Data
public class SignInResponseDto {

    private String id;

    private String mobile;

    private String name;

    private String token;


    public SignInResponseDto(Employee user) {
        this.id = user.getId();
        this.name = user.getName();
        this.mobile = user.getPhone();

    }

    public SignInResponseDto() {
        super();
    }

}
