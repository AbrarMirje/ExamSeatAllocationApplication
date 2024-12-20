package com.codecrafter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "student_table")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    private String firstName;
    private String lastName;

    @Column(unique = true,nullable = false)
    private String prnNumber;

    private String semester;
    private String gender;
    private boolean isSeatAllocated;
    private Integer rollNumber;

    private String mobileNumber;

    private String year;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<SeatAllocation> seatAllocations = new ArrayList<>();
}
