package com.codecrafter.entity.dto;

import com.codecrafter.entity.Course;
import lombok.*;

@Setter
@Getter
public class StudentDto {

    private Long studentId;
    private String firstName;
    private String lastName;
    private String prnNumber;
    private String semester;
    private String gender;
    private Integer rollNumber;
    private Course course;
    private String mobileNumber;
    private String year;
}
