package com.example.Employee_projects.Document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "projects")
public class Project {
    @Id
    private String id;
    private String title;
    private String description;
}
