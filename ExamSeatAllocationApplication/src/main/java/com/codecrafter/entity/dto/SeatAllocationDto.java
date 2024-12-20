package com.codecrafter.entity.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SeatAllocationDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String prnNumber;
    private String courseName;
    private String semester;
    private String gender;
    private String blockNumber;
    private Long blockId;
    private String seatNumber;
    private Integer rollNumber;
    private String mobileNumber;
}
