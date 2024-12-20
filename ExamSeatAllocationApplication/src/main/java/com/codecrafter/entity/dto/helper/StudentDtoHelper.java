package com.codecrafter.entity.dto.helper;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentDtoHelper {
    private Long studentId;
    private String firstName;
    private String lastName;
    private String prnNumber;
    private String semester;
    private String gender;
    private Integer rollNumber;
    private String seatNumber;
    private String courseName;
    private String mobileNumber;
    private String blockNumber;
    private boolean isSeatAllocated;
    private String year;
}
