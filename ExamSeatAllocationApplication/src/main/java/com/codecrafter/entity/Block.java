package com.codecrafter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "block_table")
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blockId;

    @Column(unique = true)
    private String blockNumber;

    private Integer capacity;
    private boolean isSupervisorAllocated;

    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL)
    private List<SeatAllocation> seatAllocations = new ArrayList<>();
}
